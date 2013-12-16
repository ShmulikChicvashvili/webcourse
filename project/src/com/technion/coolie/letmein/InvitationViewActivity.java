package com.technion.coolie.letmein;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.letmein.model.Invitation;
import com.technion.coolie.letmein.model.adapters.InvitationAdapter;

public class InvitationViewActivity extends DatabaseActivity {

	private final String LOG_TAG = Consts.LOG_PREFIX + getClass().getSimpleName();

	private AutoCompleteTextView friendNameEdit;
	private EditText friendCellphoneEdit;
	private EditText friendCarNumberEdit;
	private Spinner friendCarCompanySpinner;
	private AutoCompleteTextView friendCarColorEdit;
	private TextView datePicker;
	private TextView timePicker;
	private ImageView friendImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmi_activity_invitation);

		friendNameEdit = (AutoCompleteTextView) findViewById(R.id.lmi_friend_name_edit);
		friendCellphoneEdit = (EditText) findViewById(R.id.lmi_friend_cellphone_edit);
		friendCarNumberEdit = (EditText) findViewById(R.id.lmi_friend_car_number_edit);
		friendCarCompanySpinner = (Spinner) findViewById(R.id.lmi_friend_car_company_edit);
		friendCarColorEdit = (AutoCompleteTextView) findViewById(R.id.lmi_friend_car_color_edit);
		datePicker = (TextView) findViewById(R.id.lmi_date_picker);
		timePicker = (TextView) findViewById(R.id.lmi_time_picker);
		friendImage = (ImageView) findViewById(R.id.lmi_friend_image);

		disableComponents();

		int position = getIntent().getExtras().getInt(Consts.POSITION);
		Invitation invitation = new InvitationAdapter(InvitationViewActivity.this, getHelper()).getItem(position);

		friendNameEdit.setText(invitation.getContactName());
		friendCellphoneEdit.setText(invitation.getContactPhoneNumber());
		friendCarNumberEdit.setText(invitation.getCarNumber());
		friendCarCompanySpinner.setSelection(invitation.getCarCompany().ordinal());
		friendCarColorEdit.setText(invitation.getCarColor());

		MyCalendar cal = new MyCalendar();
		cal.restoreFromTime(invitation.getDate());
		datePicker.setText(cal.parseDate());
		timePicker.setText(cal.parseTime());
		// friendImage TODO
	}

	private void disableComponents() {
		friendNameEdit.setFocusable(false);
		friendCellphoneEdit.setFocusable(false);
		friendCarNumberEdit.setFocusable(false);
		friendCarCompanySpinner.setEnabled(false);
		friendCarColorEdit.setFocusable(false);
		datePicker.setClickable(false);
		datePicker.setFocusable(true);
		timePicker.setClickable(false);
		timePicker.setFocusable(true);
	}
}
