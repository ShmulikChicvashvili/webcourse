package com.technion.coolie.teletech;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.teletech.ContactSummaryFragment.OnContactSelectedListener;

public class MainActivity extends CoolieActivity implements
		OnContactSelectedListener {

	public static List<ContactInformation> contacts;

	private DBTools db = new DBTools(this);

	// TODO: remove this

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createDBFromStub();
		contacts = db.getAllContacts();
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		super.setContentView(R.layout.teletech_main);
		// TODO: fetch the data from the server and put it back to the DB.

		if (findViewById(com.technion.coolie.R.id.fragment_container) != null) {
			final FragmentTransaction trans = getSupportFragmentManager()
					.beginTransaction();
			trans.replace(com.technion.coolie.R.id.fragment_container,
					new ContactSummaryFragment());
			trans.commit();
		}

	}

	private void createDBFromStub() {
		db.insertContacts(new ContactsTest().contactList);
	}

	// @Override
	// protected void onDestroy() {
	// super.onDestroy();
	// clearDBStub();
	// }
	//
	// private void clearDBStub() {
	// db.clearTables();
	// }

	@Override
	public void onContactSelected(final int position) {
		final FullContactInformation contact = (FullContactInformation) getSupportFragmentManager()
				.findFragmentById(com.technion.coolie.R.id.contact_fragment);
		if (contact != null)
			// This means that the layout is large and that we only need to
			// update the view to display.
			contact.updateContactInformationView(position);
		else {
			// We are in a small layout and we need to replace the fragment that
			// is presented.
			final FullContactInformation newContact = new FullContactInformation();
			final Bundle args = new Bundle();
			args.putInt(FullContactInformation.ARG_POSITION_STRING, position);
			newContact.setArguments(args);
			final FragmentTransaction trans = getSupportFragmentManager()
					.beginTransaction();
			trans.replace(com.technion.coolie.R.id.fragment_container,
					newContact);
			trans.addToBackStack(null);
			trans.commit();
		}
	}

	/**
	 * @param v
	 */
	public void onPhoneNumberClicked(final View v) {
		final String phone_no = ((TextView) v).getText().toString()
				.replaceAll("-", "");
		if (phone_no.equals("NA"))
			return;
		final Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + phone_no));
		startActivity(callIntent);
	}

	/**
	 * @param v
	 */
	public void onEmailClicked(final View v) {
		final Intent intent = new Intent(Intent.ACTION_VIEW);
		final String mailTo = ((TextView) v).getText().toString();
		if (mailTo == null)
			return;
		intent.setData(Uri.parse("mailto:" + mailTo));
		startActivity(intent);
	}

	/**
	 * @param v
	 */
	public void onWebsiteClicked(final View v) {
		final String website = ((TextView) v).getText().toString();
		if (website == null)
			return;
		final Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
		startActivity(i);
	}

	/**
	 * @param v
	 */
	public void OnAddToFavoriteChecked(final View v) {

		final TextView contactID = (TextView) findViewById(com.technion.coolie.R.id.contactID);
		
		db.insertFavourite(db.getContactInfo(contactID.getText().toString()));
		Log.d("insert new favourite",
				"SUCCESS in inserting the new contact to the favourite list");
	}

}
