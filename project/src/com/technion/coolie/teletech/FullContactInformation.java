/**
 * 
 */
package com.technion.coolie.teletech;

import junit.framework.Assert;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * @author Argaman
 * 
 */
public class FullContactInformation extends SherlockFragment {

	DBTools db = new DBTools(getActivity());

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
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
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
	public void onSaveInstanceState(final Bundle outState) {
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
		final Bundle args = getArguments();
		if (args != null)
			updateContactInformationView(args.getInt(ARG_POSITION_STRING));
		else
			updateContactInformationView(mCurrentPosition);
	}

	/**
	 * @param position
	 */
	public void updateContactInformationView(final int position) {
		final ContactInformation currentContact = MainActivity.contacts
				.get(position);

		updateMainInfo(currentContact);

		updatePhoneNumbers(currentContact);

		updateAdditionalData(currentContact);

		final CheckBox star = (CheckBox) getActivity().findViewById(
				com.technion.coolie.R.id.addToFavorite);
		// should find the contact in the DB and check if he is in the favorite
		// DB, if so, check the contact
		if (star.isChecked())
			star.setChecked(true);

		final TextView contactID = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.contactID);
		System.out.println(currentContact.firstName() + " " + currentContact.lastName());
		Assert.assertNotNull(currentContact.ID());
		
		contactID.setText(currentContact.ID().toString());

		mCurrentPosition = position;

	}


	/**
	 * @param currentContact
	 */
	private void updateAdditionalData(final ContactInformation currentContact) {
		final TextView email = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalMailInfo);
		email.setText(currentContact.techMail());

		final TextView office = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalOfficeInfo);
		office.setText(currentContact.office().toString());

		final TextView officeHours = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalOfficeHourInfo);
		officeHours
				.setText(currentContact.officeHours() == null ? new OfficeHour()
						.toString() : currentContact.officeHours().toString());

		final TextView website = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.websiteInfo);
		website.setText(currentContact.website());
	}

	/**
	 * @param currentContact
	 */
	private void updatePhoneNumbers(final ContactInformation currentContact) {
		final TextView mobile = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalPhoneInfo);
		mobile.setText(currentContact.mobileNumber() == null ? "NA"
				: currentContact.mobileNumber());

		final TextView officeNum = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalPhoneInfo1);
		officeNum.setText(currentContact.officeNumber() == null ? "NA"
				: currentContact.officeNumber());

		final TextView home = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.personalPhoneInfo2);
		home.setText(currentContact.homeNumber() == null ? "NA"
				: currentContact.homeNumber());
	}

	/**
	 * @param currentContact
	 */
	private void updateMainInfo(final ContactInformation currentContact) {
		final TextView firstName = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.privateName);
		firstName.setText(currentContact.firstName());

		final TextView lastName = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.addLastName);
		lastName.setText(currentContact.lastName());

		final TextView currPosition = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.addPosition);
		currPosition.setText(currentContact.contactPosition().toString());

		final TextView faculty = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.addfaculty);
		faculty.setText(currentContact.faculty());
	}

}
