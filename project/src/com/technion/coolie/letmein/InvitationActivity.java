package com.technion.coolie.letmein;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.technion.coolie.R;
import com.technion.coolie.letmein.model.Invitation;
import com.technion.coolie.letmein.model.Invitation.Status;
import com.technion.coolie.letmein.model.InvitationBuilder;

public class InvitationActivity extends DatabaseActivity implements CalendarSupplier {

	private static final int DROP_DOWN_LAYOUT_INDEX = android.R.layout.simple_dropdown_item_1line;

	private Calendar calendar = Calendar.getInstance();

	private AutoCompleteTextView friendNameEdit;
	private EditText friendCellphoneEdit;
	private EditText friendCarNumberEdit;
	private AutoCompleteTextView friendCarCompanyEdit;
	private AutoCompleteTextView friendCarColorEdit;
	private TextView datePicker;
	private TextView timePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmi_activity_invitation);

		friendNameEdit = (AutoCompleteTextView) findViewById(R.id.lmi_friend_name_edit);
		friendCellphoneEdit = (EditText) findViewById(R.id.lmi_friend_cellphone_edit);
		friendNameEdit.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					friendCellphoneEdit.setText(getFriendCellPhoneNumber());
				}

				return false;
			}
		});

		friendCarNumberEdit = (EditText) findViewById(R.id.lmi_friend_car_number_edit);

		friendCarCompanyEdit = (AutoCompleteTextView) findViewById(R.id.lmi_friend_car_company_edit);
		friendCarCompanyEdit.setAdapter(new ArrayAdapter<String>(InvitationActivity.this,
				DROP_DOWN_LAYOUT_INDEX, getResources().getStringArray(R.array.lmi_car_companies)));

		datePicker = (TextView) findViewById(R.id.lmi_date_picker);
		setDate(calendar);

		timePicker = (TextView) findViewById(R.id.lmi_time_picker);
		setTime(calendar);

		friendCarColorEdit = (AutoCompleteTextView) findViewById(R.id.lmi_friend_car_color_edit);
		friendCarColorEdit.setAdapter(new ArrayAdapter<String>(InvitationActivity.this,
				DROP_DOWN_LAYOUT_INDEX, getResources().getStringArray(R.array.lmi_car_colors)));
		friendCarColorEdit.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					InputMethodManager inputMethodManager = (InputMethodManager) InvitationActivity.this
							.getSystemService(Activity.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(InvitationActivity.this
							.getCurrentFocus().getWindowToken(), 0);
				}

				return false;
			}
		});
	}

	public void showTimePickerDialog(View v) {
		new TimePickerFragment().show(getSupportFragmentManager(),
				TimePickerFragment.class.getSimpleName());
	}

	public void showDatePickerDialog(View v) {
		new DatePickerFragment().show(getSupportFragmentManager(),
				DatePickerFragment.class.getSimpleName());
	}

	@Override
	protected void onStart() {
		super.onStart();
		new AutoCompletionGatheringTask().execute();
	}

	private class AutoCompletionGatheringTask extends AsyncTask<Void, Void, ArrayAdapter<String>> {
		@Override
		protected ArrayAdapter<String> doInBackground(Void... params) {
			// TODO: change to the real deal:
			/*
			 * List<String> friendNames = Arrays.asList("osher", "gilad",
			 * "yaniv", "osher2", "osher3", "osher4", "osher5", "osher6",
			 * "osher7", "osher8", "osher9", "osher10", "osher11", "osher12",
			 * "osher13");
			 */

			ArrayList<String> friendNames = new ArrayList<String>();
			ContentResolver contentResolver = getContentResolver();
			String whereName = ContactsContract.Data.MIMETYPE + " = ?";
			String[] whereNameParams = new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE };
			Cursor nameCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null,
					whereName, whereNameParams,
					ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
			while (nameCur.moveToNext()) {
				friendNames
						.add(nameCur.getString(nameCur
								.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)));
			}
			nameCur.close();

			return new ArrayAdapter<String>(InvitationActivity.this, DROP_DOWN_LAYOUT_INDEX,
					friendNames);

			/*
			 * // Gets the URI of the db Uri uri =
			 * ContactsContract.Contacts.CONTENT_URI; // What to grab from the
			 * db String[] projection = new String[] {
			 * ContactsContract.Contacts._ID,
			 * ContactsContract.Contacts.DISPLAY_NAME,
			 * ContactsContract.Contacts.PHOTO_ID };
			 * 
			 * String sortOrder = ContactsContract.Contacts.DISPLAY_NAME +
			 * " COLLATE LOCALIZED ASC";
			 * 
			 * Cursor cursor = managedQuery(uri, projection, null, null,
			 * sortOrder);
			 * 
			 * String[] fields = new String[] {
			 * ContactsContract.Data.DISPLAY_NAME };
			 * 
			 * int[] values = { R.id.contactEntryText };
			 * 
			 * return new
			 * ArrayAdapter<String>(this,R.layout.lmi_contact_entry,R.
			 * id.lmi_contactEntryText,
			 * 
			 * return new SimpleCursorAdapter(this, R.layout.contact_entry,
			 * cursor, fields, values);
			 */
		}

		@Override
		protected void onPostExecute(ArrayAdapter<String> adapter) {
			friendNameEdit.setAdapter(adapter);
		}

	}

	private String getFriendCellPhoneNumber() {
		String friendName = friendNameEdit.getText().toString();

		if ("".equals(friendName)) {
			return "";
		}

		/*
		 * TODO: change to the real deal, means, trying to find cellphone number
		 * of the friend.
		 */
		return "0502759102";
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putCharSequence(String.valueOf(R.id.lmi_friend_name_edit),
				friendNameEdit.getText());
		savedInstanceState.putCharSequence(String.valueOf(R.id.lmi_friend_cellphone_edit),
				friendCellphoneEdit.getText());
		savedInstanceState.putCharSequence(String.valueOf(R.id.lmi_friend_car_number_edit),
				friendCarNumberEdit.getText());
		savedInstanceState.putCharSequence(String.valueOf(R.id.lmi_friend_car_company_edit),
				friendCarCompanyEdit.getText());
		savedInstanceState.putCharSequence(String.valueOf(R.id.lmi_friend_car_color_edit),
				friendCarColorEdit.getText());

		savedInstanceState.putIntArray(Consts.CALENDAR_INFO, new int[] { getYear(), getMonth(),
				getDay(), getHour(), getMinute() });
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		friendNameEdit.setText(savedInstanceState.getCharSequence(String
				.valueOf(R.id.lmi_friend_name_edit)));
		friendCellphoneEdit.setText(savedInstanceState.getCharSequence(String
				.valueOf(R.id.lmi_friend_cellphone_edit)));
		friendCarNumberEdit.setText(savedInstanceState.getCharSequence(String
				.valueOf(R.id.lmi_friend_car_number_edit)));
		friendCarCompanyEdit.setText(savedInstanceState.getCharSequence(String
				.valueOf(R.id.lmi_friend_car_company_edit)));
		friendCarColorEdit.setText(savedInstanceState.getCharSequence(String
				.valueOf(R.id.lmi_friend_car_color_edit)));

		int[] arr = savedInstanceState.getIntArray(Consts.CALENDAR_INFO);
		setYear(arr[0]);
		setMonth(arr[1]);
		setDay(arr[2]);
		setHour(arr[3]);
		setMinute(arr[4]);
		setDate(calendar);
		setTime(calendar);
	}

	public void setYear(int year) {
		calendar.set(Calendar.YEAR, year);
	}

	public void setMonth(int month) {
		calendar.set(Calendar.MONTH, month);
	}

	public void setDay(int day) {
		calendar.set(Calendar.DAY_OF_MONTH, day);
	}

	public void setHour(int hour) {
		calendar.set(Calendar.HOUR_OF_DAY, hour);
	}

	public void setMinute(int minute) {
		calendar.set(Calendar.MINUTE, minute);
	}

	@Override
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}

	@Override
	public int getMonth() {
		return calendar.get(Calendar.MONTH);
	}

	@Override
	public int getDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	public int getHour() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	@Override
	public int getMinute() {
		return calendar.get(Calendar.MINUTE);
	}

	@Override
	public void setDate(Calendar calendar) {
		this.calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		this.calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		this.calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

		datePicker.setText(getString(R.string.lmi_date_of_arrival) + ":\n" + getDay() + "/"
				+ (getMonth() + 1) + "/" + getYear());
	}

	@Override
	public void setTime(Calendar calendar) {
		this.calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
		this.calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));

		String fixedHour = (getHour() < 10 ? "0" : "") + getHour();
		String fixedMinute = (getMinute() < 10 ? "0" : "") + getMinute();
		timePicker.setText(getString(R.string.lmi_time_of_arrival) + ":\n" + fixedHour + ":"
				+ fixedMinute);
	}

	public void executeInvitation(View v) {
		String friendName = friendNameEdit.getText().toString();
		String friendCellphone = friendCellphoneEdit.getText().toString();
		String carNumber = friendCarNumberEdit.getText().toString();
		String carCompany = friendCarCompanyEdit.getText().toString();
		String carColor = friendCarColorEdit.getText().toString();

		if (isUserForgotAField(friendName, carNumber, carCompany, carColor)) {
			return;
		}

		Invitation i = new InvitationBuilder().setContactId(friendName).setDate(calendar.getTime())
				.setStatus(Status.CREATED).setFriendName(friendName)
				.setFriendCellphone(friendCellphone).setCarNumber(carNumber)
				.setCarCompany(carCompany).setCarColor(carColor).build();

		getHelper().getDataDao().create(i);

		finish();
	}

	private boolean isUserForgotAField(String friendName, String carNumber, String carCompany,
			String carColor) {
		if ("".equals(friendName)) {
			Toast.makeText(getApplicationContext(), "insert name", Toast.LENGTH_SHORT).show();
		} else if ("".equals(carNumber)) {
			Toast.makeText(getApplicationContext(), "insert car number", Toast.LENGTH_SHORT).show();
		} else if ("".equals(carCompany)) {
			Toast.makeText(getApplicationContext(), "insert car company", Toast.LENGTH_SHORT)
					.show();
		} else if ("".equals(carColor)) {
			Toast.makeText(getApplicationContext(), "insert car color", Toast.LENGTH_SHORT).show();
		} else {
			return false;
		}

		return true;
	}
}
