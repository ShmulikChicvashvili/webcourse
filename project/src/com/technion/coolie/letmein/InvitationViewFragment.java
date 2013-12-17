package com.technion.coolie.letmein;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.letmein.InvitationListFragment.AdapterSupplier;
import com.technion.coolie.letmein.model.ContactsUtils;
import com.technion.coolie.letmein.model.Invitation;

public class InvitationViewFragment extends Fragment {

	private AdapterSupplier activity;

	private AutoCompleteTextView friendNameEdit;
	private EditText friendCellphoneEdit;
	private EditText friendCarNumberEdit;
	private Spinner friendCarCompanySpinner;
	private AutoCompleteTextView friendCarColorEdit;
	private TextView datePicker;
	private TextView timePicker;
	private ImageView friendImage;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (!(activity instanceof AdapterSupplier)) {
			throw new ClassCastException(activity.toString() + " must implement "
					+ AdapterSupplier.class.getSimpleName());
		}

		this.activity = (AdapterSupplier) activity;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lmi_activity_invitation, container, false);

		friendNameEdit = (AutoCompleteTextView) view.findViewById(R.id.lmi_friend_name_edit);
		friendCellphoneEdit = (EditText) view.findViewById(R.id.lmi_friend_cellphone_edit);
		friendCarNumberEdit = (EditText) view.findViewById(R.id.lmi_friend_car_number_edit);
		friendCarCompanySpinner = (Spinner) view.findViewById(R.id.lmi_friend_car_company_edit);
		friendCarColorEdit = (AutoCompleteTextView) view
				.findViewById(R.id.lmi_friend_car_color_edit);
		datePicker = (TextView) view.findViewById(R.id.lmi_date_picker);
		timePicker = (TextView) view.findViewById(R.id.lmi_time_picker);
		friendImage = (ImageView) view.findViewById(R.id.lmi_friend_image);

		int position = getArguments().getInt(Consts.POSITION);
		Invitation invitation = activity.getAdapter().getItem(position);

		friendNameEdit.setText(invitation.getContactName());
		friendCellphoneEdit.setText(invitation.getContactPhoneNumber());
		friendCarNumberEdit.setText(invitation.getCarNumber());
		friendCarCompanySpinner.setSelection(invitation.getCarManufacturer().ordinal());
		friendCarColorEdit.setText(invitation.getCarColor());

		MyCalendar cal = new MyCalendar();
		cal.restoreFromTime(invitation.getDate());
		datePicker.setText(cal.parseDate());
		timePicker.setText(cal.parseTime());

		friendImage.setImageURI(ContactsUtils.contactIdToTumbnailPhoto(invitation.getContactId(),
				getActivity().getContentResolver()));

		disableComponents();
		return view;
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
