package com.technion.coolie.letmein;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
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
import com.technion.coolie.letmein.service.InvitationSender;
import com.technion.coolie.letmein.service.InvitationSender.ConnectionException;
import com.technion.coolie.letmein.service.InvitationSender.InvitationFormatException;
import com.technion.coolie.letmein.service.InvitationSender.InvitationLimitExceededException;
import com.technion.coolie.letmein.service.InvitationSender.LoginException;
import com.technion.coolie.letmein.service.InvitationSender.UnknownErrorException;
import com.technion.coolie.letmein.util.AsyncTaskResult;

public class InvitationActivity extends DatabaseActivity implements CalendarSupplier {

	private static final int DROP_DOWN_LAYOUT_INDEX = android.R.layout.simple_dropdown_item_1line;
	private static final int TIME_AFTER_NOW = 5;

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
	public boolean onCreateOptionsMenu(final Menu menu) {
		getSupportMenuInflater().inflate(R.menu.lmi_menu, menu);

		menu.findItem(R.id.lmi_search).setVisible(false);
		menu.findItem(R.id.lmi_add_invitation).setVisible(false);
		menu.findItem(R.id.lmi_done).setEnabled(isDoneItemEnabled);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
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

		cal.setTime(cal.getHour(), cal.getMinute() + TIME_AFTER_NOW);

		final TextWatcher updateEnabilityWatcher = new TextWatcher() {
			@Override
			public void onTextChanged(final CharSequence s, final int start, final int before,
					final int count) {
				updateEnabilityOfDoneItem();
			}

			@Override
			public void beforeTextChanged(final CharSequence s, final int start, final int count,
					final int after) {
			}

			@Override
			public void afterTextChanged(final Editable s) {
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

		friendImage = (ImageView) InvitationActivity.this.findViewById(R.id.lmi_friend_image);

		friendCarNumberEdit = (EditText) findViewById(R.id.lmi_friend_car_number_edit);
		friendCarNumberEdit.addTextChangedListener(updateEnabilityWatcher);

		friendCarCompanySpinner = (Spinner) findViewById(R.id.lmi_friend_car_company_edit);

		datePicker = (TextView) findViewById(R.id.lmi_date_picker);
		datePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				new DatePickerFragment().show(getSupportFragmentManager(),
						DatePickerFragment.class.getSimpleName());
			}
		});
		datePicker.setText(cal.parseDate());

		timePicker = (TextView) findViewById(R.id.lmi_time_picker);
		timePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				new TimePickerFragment().show(getSupportFragmentManager(),
						TimePickerFragment.class.getSimpleName());
			}
		});
		timePicker.setText(cal.parseTime());

		friendCarColorEdit = (AutoCompleteTextView) findViewById(R.id.lmi_friend_car_color_edit);
		friendCarColorEdit.addTextChangedListener(updateEnabilityWatcher);
		friendCarColorEdit.setAdapter(new ArrayAdapter<String>(InvitationActivity.this,
				DROP_DOWN_LAYOUT_INDEX, getResources().getStringArray(R.array.lmi_car_colors)));
		friendCarColorEdit.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					final InputMethodManager inputMethodManager = (InputMethodManager) InvitationActivity.this
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(InvitationActivity.this
							.getCurrentFocus().getWindowToken(), 0);
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

	private class AutoCompletionGatheringTask extends AsyncTask<Void, Void, ContactsAdapter> {

		@Override
		protected ContactsAdapter doInBackground(final Void... params) {

			final String packName = getPackageName();
			final Uri defaultPicPath = Uri.parse("android.resource://" + packName + "/"
					+ R.drawable.lmi_google_man);

			final List<ContactInfo> l = ContactsUtils.getAllContacts(getContentResolver());
			for (final ContactInfo ci : l)
				if (ci.imageUri == null)
					ci.imageUri = defaultPicPath.toString();

			return new ContactsAdapter(InvitationActivity.this, l);
		}

		@Override
		protected void onPostExecute(final ContactsAdapter adapter) {
			friendNameEdit.setAdapter(adapter);

			final OnItemClickListener itemClickListener = new OnItemClickListener() {
				@Override
				public void onItemClick(final AdapterView<?> arg0, final View arg1,
						final int position, final long id) {
					final ContactInfo hm = (ContactInfo) arg0.getAdapter().getItem(position);
					friendImage.setImageURI(Uri.parse(hm.imageUri));
					friendCellphoneEdit.setText(hm.phoneNumber);
					currentSelectedId = hm.id;

					(new AsyncTask<Long, Void, Invitation>() {
						@Override
						protected Invitation doInBackground(final Long... params) {
							List<Invitation> l;
							try {
								l = getHelper().getDataDao().queryBuilder().limit(1L)
										.orderBy(Contract.Invitation.DATE, false).where()
										.eq(Contract.Invitation.CONTACT_ID, params[0]).query();
								if (l.size() > 0)
									return l.get(0);
								else
									return null;

							} catch (final SQLException e) {
								return null;
							}
						}

						@Override
						protected void onPostExecute(final Invitation inv) {
							if (inv != null) {
								friendCarNumberEdit.setText(inv.getCarNumber());
								friendCarColorEdit.setText(inv.getCarColor());
								friendCarCompanySpinner.setSelection(inv.getCarManufacturer()
										.ordinal());
							}
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

		savedInstanceState.putCharSequence(String.valueOf(R.id.lmi_friend_name_edit),
				friendNameEdit.getText());
		savedInstanceState.putCharSequence(String.valueOf(R.id.lmi_friend_cellphone_edit),
				friendCellphoneEdit.getText());
		savedInstanceState.putCharSequence(String.valueOf(R.id.lmi_friend_car_number_edit),
				friendCarNumberEdit.getText());
		savedInstanceState.putInt(String.valueOf(R.id.lmi_friend_car_company_edit),
				friendCarCompanySpinner.getSelectedItemPosition());
		savedInstanceState.putCharSequence(String.valueOf(R.id.lmi_friend_car_color_edit),
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

		if (isUserPickedTimeInThePast()) {
			Toast.makeText(getApplicationContext(),
					R.string.lmi_user_picked_time_in_the_past_message, Toast.LENGTH_SHORT).show();
			return;
		}

		final Invitation i = Invitation.builder().contactId(currentSelectedId).date(cal.getTime())
				.status(Status.CREATED).contactName(friendName).contactPhoneNumber(friendCellphone)
				.carNumber(carNumber).carManufacturer(carCompany).carColor(carColor).build();

		getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE).getBoolean(
				Consts.IS_LOGGED_IN, false);

		final String username = getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE)
				.getString(Consts.USERNAME, "");

		final String password = getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE)
				.getString(Consts.PASSWORD, "");

		(new AsyncTask<Object, Void, AsyncTaskResult<Boolean>>() {
			@Override
			protected AsyncTaskResult<Boolean> doInBackground(final Object... params) {
				AsyncTaskResult<Boolean> $;
				try {
					getHelper().getDataDao().create((Invitation) params[2]);
					$ = new AsyncTaskResult<Boolean>(InvitationSender.addInvitation(
							(String) params[0], (String) params[1], (Invitation) params[2]));
				} catch (final ConnectionException e) {
					$ = new AsyncTaskResult<Boolean>(e);
					Toast.makeText(InvitationActivity.this, "There was a connection problem",
							Toast.LENGTH_LONG).show();
				} catch (final UnknownErrorException e) {
					$ = new AsyncTaskResult<Boolean>(e);
					Toast.makeText(InvitationActivity.this, "There was a unknown problem",
							Toast.LENGTH_LONG).show();
				} catch (final LoginException e) {
					$ = new AsyncTaskResult<Boolean>(e);
					Toast.makeText(InvitationActivity.this, "There was a login problem",
							Toast.LENGTH_LONG).show();
				} catch (final InvitationFormatException e) {
					$ = new AsyncTaskResult<Boolean>(e);
					Toast.makeText(InvitationActivity.this, "There was a format problem",
							Toast.LENGTH_LONG).show();
				} catch (final InvitationLimitExceededException e) {
					$ = new AsyncTaskResult<Boolean>(e);
					Toast.makeText(InvitationActivity.this, "There was a Limit problem",
							Toast.LENGTH_LONG).show();
				}

				return $;
			}

			@Override
			protected void onPostExecute(final AsyncTaskResult<Boolean> result) {
				if (result.getError() == null)
					// TODO
					return;
			}

		}).execute(username, password, i);

		if (!(getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE).getBoolean(
				Consts.IS_FIRST_INVITATION_INPUT, false))) {

			getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE).edit()
					.putBoolean(Consts.IS_FIRST_INVITATION_INPUT, true).apply();

			Toast.makeText(getApplicationContext(), R.string.lmi_first_inv_text, Toast.LENGTH_SHORT)
					.show();

			// new
			// AlertDialog.Builder(this).setTitle(R.string.lmi_first_inv_title)
			// .setMessage(R.string.lmi_first_inv_text)
			// .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(final DialogInterface dialog, final int
			// which) {
			// finish();
			// }
			// }).setNegativeButton("", null).setCancelable(false).show();
		}

		finish();
	}

	private boolean isUserPickedTimeInThePast() {
		final MyCalendar now = new MyCalendar();
		return cal.getTime().getTime() <= now.getTime().getTime();
	}

	@Override
	public void setDate(final int year, final int month, final int day) {
		cal.setDate(year, month, day);
		datePicker.setText(cal.parseDate());
	}

	@Override
	public void setTime(final int hour, final int minute) {
		cal.setTime(hour, minute);
		timePicker.setText(cal.parseTime());
	}

	@Override
	public MyCalendar getMyCalendar() {
		return cal;
	}
}
