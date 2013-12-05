/**
 * 
 */
package com.technion.coolie.teletech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * @author Argaman
 * 
 */
public class FullContactInformation extends SherlockFragment {

	final static String ARG_POSITION_STRING = "position";
	int mCurrentPosition = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null)
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION_STRING);
		return inflater.inflate(
				com.technion.coolie.R.layout.teletech_adittional_info,
				container, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_POSITION_STRING, mCurrentPosition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		super.onStart();
		Bundle args = getArguments();
		if (args != null)
			updateContactInformationView(args.getInt(ARG_POSITION_STRING));
		else
			updateContactInformationView(mCurrentPosition);
	}

	public void updateContactInformationView(int position) {
		ContactInformation currentContact = TeletechManager.getContacts().get(
				position);
		updateMainInfo(currentContact);

		updatePhoneNumbers(currentContact);

		updateAdditionalData(currentContact);

		RadioButton star = (RadioButton) getActivity().findViewById(
				com.technion.coolie.R.id.addToFavorite);
		// should find the contact in the DB and check if he is in the favorite
		// DB, if so, check the contact
		if (star.isChecked())
			star.setSelected(true);

		mCurrentPosition = position;

	}

	/**
	 * @param currentContact
	 */
	private void updateAdditionalData(ContactInformation currentContact) {
		TextView email = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalMailInfo);
		email.setText(currentContact.techMail());

		TextView office = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalOfficeInfo);
		office.setText(currentContact.office().toString());

		TextView officeHours = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalOfficeHourInfo);
		officeHours.setText(currentContact.officeHours().toString());

		TextView website = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.websiteInfo);
		website.setText(currentContact.website());
	}

	/**
	 * @param currentContact
	 */
	private void updatePhoneNumbers(ContactInformation currentContact) {
		TextView mobile = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalPhoneInfo);
		mobile.setText(currentContact.mobileNumber() == null ? "NA"
				: currentContact.mobileNumber());

		TextView officeNum = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalPhoneInfo1);
		officeNum.setText(currentContact.officeNumber() == null ? "NA"
				: currentContact.officeNumber());

		TextView home = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalPhoneInfo2);
		home.setText(currentContact.homeNumber() == null ? "NA"
				: currentContact.homeNumber());
	}

	/**
	 * @param currentContact
	 */
	private void updateMainInfo(ContactInformation currentContact) {
		TextView firstName = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.privateName);
		firstName.setText(currentContact.firstName());

		TextView lastName = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.addLastName);
		lastName.setText(currentContact.lastName());

		TextView currPosition = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.addPosition);
		currPosition.setText(currentContact.contactPosition().toString());

		TextView faculty = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.addfaculty);
		faculty.setText(currentContact.faculty());
	}

}
