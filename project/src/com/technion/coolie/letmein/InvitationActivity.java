package com.technion.coolie.letmein;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.technion.coolie.R;
import com.technion.coolie.letmein.model.ContactInfo;
import com.technion.coolie.letmein.model.ContactsUtils;
import com.technion.coolie.letmein.model.Contract;
import com.technion.coolie.letmein.model.Invitation;
import com.technion.coolie.letmein.model.Invitation.Status;
import com.technion.coolie.letmein.model.adapters.ContactsAdapter;

public class InvitationActivity extends DatabaseActivity implements
		CalendarSupplier {

	private static final int DROP_DOWN_LAYOUT_INDEX = android.R.layout.simple_dropdown_item_1line;

	private final MyCalendar cal = new MyCalendar();
	private boolean isDoneItemEnabled = false;

	private AutoCompleteTextView friendNameEdit;
	private EditText friendCellphoneEdit;
	private EditText friendCarNumberEdit;
	private Spinner friendCarCompanySpinner;
	private AutoCompleteTextView friendCarColorEdit;
	private TextView datePicker;
	private TextView timePicker;
	private ImageView friendImage;

	private long currentSelectedId = -1;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.lmi_menu, menu);

		menu.findItem(R.id.lmi_search).setVisible(false);
		menu.findItem(R.id.lmi_add_invitation).setVisible(false);
		menu.findItem(R.id.lmi_done).setEnabled(isDoneItemEnabled);

		// TODO: discuss about it:
		// return super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.lmi_done:
			executeInvitation();
			return true;
		case R.id.lmi_discard:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmi_activity_invitation);

		TextWatcher updateEnabilityWatcher = new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				updateEnabilityOfDoneItem();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};

		friendNameEdit = (AutoCompleteTextView) findViewById(R.id.lmi_friend_name_edit);
		friendNameEdit.addTextChangedListener(updateEnabilityWatcher);
		friendNameEdit.requestFocus();

		friendCellphoneEdit = (EditText) findViewById(R.id.lmi_friend_cellphone_edit);
		friendCellphoneEdit.addTextChangedListener(updateEnabilityWatcher);
		/*
		 * friendNameEdit.setOnEditorActionListener(new OnEditorActionListener()
		 * {
		 * 
		 * @Override public boolean onEditorAction(final TextView v, final int
		 * actionId, final KeyEvent event) { if (actionId ==
		 * EditorInfo.IME_ACTION_NEXT)
		 * friendCellphoneEdit.setText(getFriendCellPhoneNumber());
		 * 
		 * return false; } });
		 */

		friendImage = (ImageView) InvitationActivity.this
				.findViewById(R.id.lmi_friend_image);

		friendCarNumberEdit = (EditText) findViewById(R.id.lmi_friend_car_number_edit);
		friendCarNumberEdit.addTextChangedListener(updateEnabilityWatcher);

		friendCarCompanySpinner = (Spinner) findViewById(R.id.lmi_friend_car_company_edit);

		datePicker = (TextView) findViewById(R.id.lmi_date_picker);
		datePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerFragment().show(getSupportFragmentManager(),
						DatePickerFragment.class.getSimpleName());
			}
		});
		datePicker.setText(cal.parseDate());

		timePicker = (TextView) findViewById(R.id.lmi_time_picker);
		timePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new TimePickerFragment().show(getSupportFragmentManager(),
						TimePickerFragment.class.getSimpleName());
			}
		});
		timePicker.setText(cal.parseTime());

		friendCarColorEdit = (AutoCompleteTextView) findViewById(R.id.lmi_friend_car_color_edit);
		friendCarColorEdit.addTextChangedListener(updateEnabilityWatcher);
		friendCarColorEdit.setAdapter(new ArrayAdapter<String>(
				InvitationActivity.this, DROP_DOWN_LAYOUT_INDEX, getResources()
						.getStringArray(R.array.lmi_car_colors)));
		friendCarColorEdit
				.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(final TextView v,
							final int actionId, final KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_NEXT) {
							final InputMethodManager inputMethodManager = (InputMethodManager) InvitationActivity.this
									.getSystemService(Activity.INPUT_METHOD_SERVICE);
							inputMethodManager.hideSoftInputFromWindow(
									InvitationActivity.this.getCurrentFocus()
											.getWindowToken(), 0);
						}

						return false;
					}
				});
	}

	private void updateEnabilityOfDoneItem() {
		isDoneItemEnabled = !"".equals(friendNameEdit.getText().toString())
				&& !"".equals(friendCellphoneEdit.getText().toString())
				&& !"".equals(friendCarNumberEdit.getText().toString())
				&& !"".equals(friendCarColorEdit.getText().toString());

		supportInvalidateOptionsMenu();
	}

	@Override
	protected void onStart() {
		super.onStart();
		new AutoCompletionGatheringTask().execute();
	}

	private class AutoCompletionGatheringTask extends
			AsyncTask<Void, Void, ContactsAdapter> {

		@Override
		protected ContactsAdapter doInBackground(final Void... params) {

			String packName = InvitationActivity.this.getPackageName();
			Uri defaultPicPath = Uri.parse("android.resource://" + packName
					+ "/" + R.drawable.lmi_google_man);

			List<ContactInfo> l = ContactsUtils
					.getAllContacts(InvitationActivity.this
							.getContentResolver());
			for (ContactInfo ci : l) {
				if (ci.imageUri == null) {
					ci.imageUri = defaultPicPath.toString();
				}
			}

			return new ContactsAdapter(InvitationActivity.this, l);
		}

		@Override
		protected void onPostExecute(final ContactsAdapter adapter) {
			friendNameEdit.setAdapter(adapter);

			OnItemClickListener itemClickListener = new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					ContactInfo hm = (ContactInfo) arg0.getAdapter().getItem(
							position);
					friendImage.setImageURI(Uri.parse(hm.imageUri));
					friendCellphoneEdit.setText(hm.phoneNumber);
					InvitationActivity.this.currentSelectedId = hm.id;

					(new AsyncTask<Long, Void, String>() {
						@Override
						protected String doInBackground(Long... params) {
							List<Invitation> l;
							try {
								l = getHelper()
										.getDataDao()
										.queryBuilder()
										.limit(1L)
										.orderBy(Contract.Invitation.DATE,
												false).query();

								if (l.size() > 0) {
									return l.get(0).getCarNumber();
								} else {
									return "";
								}
							} catch (SQLException e) {
								return "";
							}
						}

						@Override
						protected void onPostExecute(String carNumber) {
							friendCarNumberEdit.setText(carNumber);
						}

					}).execute(hm.id);

				}

			};
			friendNameEdit.setOnItemClickListener(itemClickListener);
		}

	}

	@Override
	public void onSaveInstanceState(final Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putCharSequence(
				String.valueOf(R.id.lmi_friend_name_edit),
				friendNameEdit.getText());
		savedInstanceState.putCharSequence(
				String.valueOf(R.id.lmi_friend_cellphone_edit),
				friendCellphoneEdit.getText());
		savedInstanceState.putCharSequence(
				String.valueOf(R.id.lmi_friend_car_number_edit),
				friendCarNumberEdit.getText());
		savedInstanceState.putInt(
				String.valueOf(R.id.lmi_friend_car_company_edit),
				friendCarCompanySpinner.getSelectedItemPosition());
		savedInstanceState.putCharSequence(
				String.valueOf(R.id.lmi_friend_car_color_edit),
				friendCarColorEdit.getText());
		savedInstanceState.putIntArray(Consts.CALENDAR_INFO, cal.backup());
	}

	@Override
	public void onRestoreInstanceState(final Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		friendNameEdit.setText(savedInstanceState.getCharSequence(String
				.valueOf(R.id.lmi_friend_name_edit)));
		friendCellphoneEdit.setText(savedInstanceState.getCharSequence(String
				.valueOf(R.id.lmi_friend_cellphone_edit)));
		friendCarNumberEdit.setText(savedInstanceState.getCharSequence(String
				.valueOf(R.id.lmi_friend_car_number_edit)));
		friendCarCompanySpinner.setSelection(savedInstanceState.getInt(String
				.valueOf(R.id.lmi_friend_car_company_edit)));
		friendCarColorEdit.setText(savedInstanceState.getCharSequence(String
				.valueOf(R.id.lmi_friend_car_color_edit)));

		cal.restore(savedInstanceState.getIntArray(Consts.CALENDAR_INFO));
		datePicker.setText(cal.parseDate());
		timePicker.setText(cal.parseTime());
	}

	public void executeInvitation() {
		final String friendName = friendNameEdit.getText().toString();
		final String friendCellphone = friendCellphoneEdit.getText().toString();
		final String carNumber = friendCarNumberEdit.getText().toString();
		final CarManufacturer carCompany = CarManufacturer.values()[friendCarCompanySpinner
				.getSelectedItemPosition()];
		final String carColor = friendCarColorEdit.getText().toString();

		if (isUserChooseDateInThePast()) {
			Toast.makeText(getApplicationContext(),
					R.string.lmi_user_picked_time_in_the_past_message, Toast.LENGTH_SHORT).show();
			return;
		}

		final Invitation i = Invitation.builder()
				.contactId(this.currentSelectedId).date(cal.getTime())
				.status(Status.CREATED).contactName(friendName)
				.contactPhoneNumber(friendCellphone).carNumber(carNumber)
				.carManufacturer(carCompany).carColor(carColor).build();

		getHelper().getDataDao().create(i);

		finish();
	}

	private boolean isUserChooseDateInThePast() {
		MyCalendar now = new MyCalendar();

		return now.getTime().after(cal.getTime()) || now.equals(cal);
	}

	@Override
	public void setDate(int year, int month, int day) {
		cal.setDate(year, month, day);
		datePicker.setText(cal.parseDate());
	}

	@Override
	public void setTime(int hour, int minute) {
		cal.setTime(hour, minute);
		timePicker.setText(cal.parseTime());
	}

	@Override
	public MyCalendar getMyCalendar() {
		return cal;
	}
}
