package com.technion.coolie.letmein;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
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

import com.technion.coolie.R;
import com.technion.coolie.letmein.model.ContactInfo;
import com.technion.coolie.letmein.model.Invitation;
import com.technion.coolie.letmein.model.Invitation.Status;
import com.technion.coolie.letmein.model.adapters.ContactsAdapter;

public class InvitationActivity extends DatabaseActivity implements CalendarSupplier {

	private static final int DROP_DOWN_LAYOUT_INDEX = android.R.layout.simple_dropdown_item_1line;

	private final Calendar calendar = Calendar.getInstance();

	private AutoCompleteTextView friendNameEdit;
	private EditText friendCellphoneEdit;
	private EditText friendCarNumberEdit;
	private Spinner friendCarCompanySpinner;
	private AutoCompleteTextView friendCarColorEdit;
	private TextView datePicker;
	private TextView timePicker;
	private ImageView friendImage;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmi_activity_invitation);

		friendNameEdit = (AutoCompleteTextView) findViewById(R.id.lmi_friend_name_edit);
		friendCellphoneEdit = (EditText) findViewById(R.id.lmi_friend_cellphone_edit);
		friendNameEdit.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT)
					friendCellphoneEdit.setText(getFriendCellPhoneNumber());

				return false;
			}
		});

		friendImage = (ImageView) InvitationActivity.this.findViewById(R.id.lmi_friend_image);

		friendCarNumberEdit = (EditText) findViewById(R.id.lmi_friend_car_number_edit);

		friendCarCompanySpinner = (Spinner) findViewById(R.id.lmi_friend_car_company_edit);

		datePicker = (TextView) findViewById(R.id.lmi_date_picker);
		setDate(calendar);

		timePicker = (TextView) findViewById(R.id.lmi_time_picker);
		setTime(calendar);

		friendCarColorEdit = (AutoCompleteTextView) findViewById(R.id.lmi_friend_car_color_edit);
		friendCarColorEdit.setAdapter(new ArrayAdapter<String>(InvitationActivity.this,
				DROP_DOWN_LAYOUT_INDEX, getResources().getStringArray(R.array.lmi_car_colors)));
		friendCarColorEdit.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					final InputMethodManager inputMethodManager = (InputMethodManager) InvitationActivity.this
							.getSystemService(Activity.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(InvitationActivity.this
							.getCurrentFocus().getWindowToken(), 0);
				}

				return false;
			}
		});
	}

	public void showTimePickerDialog(final View v) {
		new TimePickerFragment().show(getSupportFragmentManager(),
				TimePickerFragment.class.getSimpleName());
	}

	public void showDatePickerDialog(final View v) {
		new DatePickerFragment().show(getSupportFragmentManager(),
				DatePickerFragment.class.getSimpleName());
	}

	@Override
	protected void onStart() {
		super.onStart();
		new AutoCompletionGatheringTask().execute();
	}

	private class AutoCompletionGatheringTask extends AsyncTask<Void, Void, ContactsAdapter> {

		@Override
		protected ContactsAdapter doInBackground(final Void... params) {

			// Each row in the list stores country name, currency and flag
			List<ContactInfo> aList = new ArrayList<ContactInfo>();

			final ContentResolver contentResolver = getContentResolver();
			final String whereName = ContactsContract.Data.MIMETYPE + " = ?";
			final String[] whereNameParams = new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE };
			final Cursor nameCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null,
					whereName, whereNameParams,
					ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);

			while (nameCur.moveToNext()) {
				String name = nameCur
						.getString(nameCur
								.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));

				Long id = nameCur.getLong(nameCur
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName._ID));

				String imageUri = nameCur
						.getString(nameCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));

				if (imageUri == null) {
					String packName = InvitationActivity.this.getPackageName();
					Uri path = Uri.parse("android.resource://" + packName
							+ R.drawable.lmi_facebook_man);
					imageUri = path.toString();
				}

				String phoneNumber = nameCur.getString(nameCur
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

				aList.add(new ContactInfo(name, id, imageUri, phoneNumber));

			}
			nameCur.close();

			return new ContactsAdapter(InvitationActivity.this, aList);
		}

		@Override
		protected void onPostExecute(final ContactsAdapter adapter) {
			friendNameEdit.setAdapter(adapter);

			OnItemClickListener itemClickListener = new OnItemClickListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

					ContactInfo hm = (ContactInfo) arg0.getAdapter().getItem(position);

					friendImage.setImageURI(Uri.parse(hm.imageUri));
					friendCellphoneEdit.setText(hm.phoneNumber);
				}

			};
			friendNameEdit.setOnItemClickListener(itemClickListener);
		}

	}

	// // private class AutoCompletionGatheringTask2 extends
	// AsyncTask<Void, Void, SimpleAdapter> {
	//
	// @Override
	// protected SimpleAdapter doInBackground(final Void... params) {
	//
	// // Each row in the list stores country name, currency and flag
	// //List<ContactInfo> aList = new ArrayList<ContactInfo>();
	// List<HashMap<String,String>> aList = new
	// ArrayList<HashMap<String,String>>();
	//
	//
	// final ContentResolver contentResolver = getContentResolver();
	// final String whereName = ContactsContract.Data.MIMETYPE + " = ?";
	// final String[] whereNameParams = new String[] {
	// ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE };
	// final Cursor nameCur = contentResolver.query(
	// ContactsContract.Data.CONTENT_URI, null, whereName,
	// whereNameParams,
	// ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
	//
	// while (nameCur.moveToNext()) {
	// String name = nameCur
	// .getString(nameCur
	// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
	//
	// String id = nameCur
	// .getString(nameCur
	// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredName._ID));
	//
	// String imageUri = nameCur
	// .getString(nameCur
	// .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
	//
	// //aList.add(new ContactInfo(name, id, imageUri, "000"));
	// HashMap<String, String> hm = new HashMap<String,String>();
	// hm.put("txt", name);
	// hm.put("id",id);
	// if (imageUri==null)
	// {
	// String packName = InvitationActivity.this.getPackageName();
	// Uri path = Uri.parse("android.resource://" + packName +
	// R.drawable.lmi_facebook_man);
	// hm.put("flag", path.toString());
	//
	// } else {
	// hm.put("flag", imageUri );
	// aList.add(hm);
	// }
	// //hm.put("cur", currency[i]);
	// }
	//
	// nameCur.close();
	//
	// // Keys used in Hashmap
	// String[] from = { "flag","txt"}; //
	//
	// // Ids of views in listview_layout
	// int[] to = { R.id.lmi_contact_image,R.id.lmi_contacts_auto_name}; //,
	//
	// //return new ContactsAdapter(InvitationActivity.this, aList);
	// return new SimpleAdapter(getBaseContext(), aList,
	// R.layout.lmi_contacts_autocomplete_layout, from, to);
	// }
	//
	// @Override
	// protected void onPostExecute(final SimpleAdapter adapter) {
	// friendNameEdit.setAdapter(adapter);
	// //friendImage.setImageURI(Uri.parse(this.last_uri))
	//
	// OnItemClickListener itemClickListener = new OnItemClickListener() {
	// @SuppressWarnings("unchecked")
	// @Override
	// public void onItemClick(AdapterView<?> arg0, View arg1,
	// int position, long id) {
	//
	//
	// HashMap<String, String> hm = (HashMap<String, String>) arg0
	// .getAdapter().getItem(position);
	//
	// //Toast.makeText(InvitationActivity.this, hm.get("flag").toString(),
	// Toast.LENGTH_SHORT).show();
	// friendImage.setImageURI(Uri.parse(hm.get("flag")));
	//
	// // TextView tvCurrency = (TextView)
	// // findViewById(R.id.tv_currency) ;
	//
	//
	// // tvCurrency.setText("Currency : " + hm.get("cur"));
	// }
	//
	// };
	// friendNameEdit.setOnItemClickListener(itemClickListener);
	// }
	//
	// }

	private String getFriendCellPhoneNumber() {
		final String friendName = friendNameEdit.getText().toString();

		if ("".equals(friendName))
			return "";

		/*
		 * TODO: change to the real deal, means, trying to find cellphone number
		 * of the friend.
		 */
		return "0502759102";
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
		savedInstanceState.putIntArray(Consts.CALENDAR_INFO, new int[] { getYear(), getMonth(),
				getDay(), getHour(), getMinute() });
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

		final int[] arr = savedInstanceState.getIntArray(Consts.CALENDAR_INFO);
		setYear(arr[0]);
		setMonth(arr[1]);
		setDay(arr[2]);
		setHour(arr[3]);
		setMinute(arr[4]);
		setDate(calendar);
		setTime(calendar);
	}

	public void setYear(final int year) {
		calendar.set(Calendar.YEAR, year);
	}

	public void setMonth(final int month) {
		calendar.set(Calendar.MONTH, month);
	}

	public void setDay(final int day) {
		calendar.set(Calendar.DAY_OF_MONTH, day);
	}

	public void setHour(final int hour) {
		calendar.set(Calendar.HOUR_OF_DAY, hour);
	}

	public void setMinute(final int minute) {
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
	public void setDate(final Calendar calendar) {
		this.calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		this.calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		this.calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

		datePicker.setText(getString(R.string.lmi_date_of_arrival) + ":\n" + getDay() + "/"
				+ (getMonth() + 1) + "/" + getYear());
	}

	@Override
	public void setTime(final Calendar calendar) {
		this.calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
		this.calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));

		final String fixedHour = (getHour() < 10 ? "0" : "") + getHour();
		final String fixedMinute = (getMinute() < 10 ? "0" : "") + getMinute();
		timePicker.setText(getString(R.string.lmi_time_of_arrival) + ":\n" + fixedHour + ":"
				+ fixedMinute);
	}

	public void executeInvitation(final View v) {
		final String friendName = friendNameEdit.getText().toString();
		final String friendCellphone = friendCellphoneEdit.getText().toString();
		final String carNumber = friendCarNumberEdit.getText().toString();
		final CarManufacturer carCompany = CarManufacturer.values()[friendCarCompanySpinner
				.getSelectedItemPosition()];
		final String carColor = friendCarColorEdit.getText().toString();

		if (isUserForgotAField(friendName, carNumber, carColor))
			return;

		final Invitation i = Invitation.builder().contactId(friendName).date(calendar.getTime())
				.status(Status.CREATED).contactName(friendName).contactPhoneNumber(friendCellphone)
				.carNumber(carNumber).carManufacturer(carCompany).carColor(carColor).build();

		getHelper().getDataDao().create(i);

		finish();
	}

	private boolean isUserForgotAField(final String friendName, final String carNumber,
			final String carColor) {
		if ("".equals(friendName))
			Toast.makeText(getApplicationContext(), "insert name", Toast.LENGTH_SHORT).show();
		else if ("".equals(carNumber))
			Toast.makeText(getApplicationContext(), "insert car number", Toast.LENGTH_SHORT).show();
		else if ("".equals(carColor))
			Toast.makeText(getApplicationContext(), "insert car color", Toast.LENGTH_SHORT).show();
		else
			return false;

		return true;
	}
}
