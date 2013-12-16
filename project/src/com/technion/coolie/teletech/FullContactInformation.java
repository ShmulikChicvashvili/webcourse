package com.technion.coolie.teletech;

import junit.framework.Assert;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class FullContactInformation extends SherlockFragment {

	DBTools db = new DBTools(getActivity());

	final static String ARG_POSITION_STRING = "position";
	CheckBox favButton;
	int mCurrentPosition = 0;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		if (savedInstanceState != null)
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION_STRING);

		// favButton = (CheckBox) getActivity().findViewById(
		// com.technion.coolie.R.id.addToFavorite);
		// favButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		// @Override
		// public void onCheckedChanged(CompoundButton arg0, boolean isChecked)
		// {
		// if (isChecked)
		// addToFavourites();
		// else
		// removeFromFavourites();
		//
		// }
		//
		// });

		return inflater.inflate(
				com.technion.coolie.R.layout.teletech_adittional_info,
				container, false);
	}

	private void removeFromFavourites() {
		// TODO CHANGE LATER
		if (MainActivity.contacts.get(mCurrentPosition).isFavourite()) {
			ContactInformation contact = MainActivity.contacts
					.get(mCurrentPosition);
			contact.setFavourite(false);
			db.deleteFavourite(contact.ID().toString());
		}

	}

	private void addToFavourites() {
		// TODO CHANGE LATER
		MainActivity.contacts.get(mCurrentPosition).setFavourite(true);
	}

	@Override
	public void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_POSITION_STRING, mCurrentPosition);
	}

	@Override
	public void onStart() {
		super.onStart();
		final Bundle args = getArguments();
		if (args != null)
			updateContactInformationView(args.getInt(ARG_POSITION_STRING));
		else
			updateContactInformationView(mCurrentPosition);
	}

	public void updateContactInformationView(final int position) {

		System.out.println("hello trying to inflate the layout, the list is"
				+ MainActivity.contacts.size());

		final ContactInformation currentContact = MainActivity.contacts
				.get(position);

		updateMainInfo(currentContact);

		updatePhoneNumbers(currentContact);

		updateAdditionalData(currentContact);

		final TextView contactID = (TextView) getActivity().findViewById(
				com.technion.coolie.R.id.contactID);
		System.out.println(currentContact.firstName() + " "
				+ currentContact.lastName());
		Assert.assertNotNull(currentContact.ID());

		contactID.setText(currentContact.ID().toString());

		mCurrentPosition = position;

	}

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
