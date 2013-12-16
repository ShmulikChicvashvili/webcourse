package com.technion.coolie.teletech;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.teletech.ContactSummaryFragment.OnContactSelectedListener;
import com.technion.coolie.teletech.api.ITeletech;
import com.technion.coolie.teletech.api.TeletechFactory;

public class MainActivity extends CoolieActivity implements
		OnContactSelectedListener {

	public static List<ContactInformation> master;

	public static List<ContactInformation> contacts;

	SearchView searchView;

	// TODO: remove this

	DBTools db = new DBTools(this);

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		createDBFromStub();

		// master = db.getAllContacts();

		contacts = new LinkedList<ContactInformation>();
		contacts.addAll(master);

		System.out.println("GOT HERE");

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

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

		// new ClientAsyncContacts() {
		// @Override
		// protected void onPostExecute(final String result) {
		master = new ContactsTest().contactList;
		System.out.println("master size is: " + master.size());
		db.insertContacts(master);
		// }
		// }.execute();
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

	public void onPhoneNumberClicked(final View v) {
		final String phone_no = ((TextView) v).getText().toString()
				.replaceAll("-", "");
		if (phone_no.equals("NA"))
			return;
		final Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + phone_no));
		startActivity(callIntent);
	}

	public void onEmailClicked(final View v) {
		final Intent intent = new Intent(Intent.ACTION_VIEW);
		final String mailTo = ((TextView) v).getText().toString();
		if (mailTo == null)
			return;
		intent.setData(Uri.parse("mailto:" + mailTo));
		startActivity(intent);
	}

	public void onWebsiteClicked(final View v) {
		final String website = ((TextView) v).getText().toString();
		if (website == null)
			return;
		final Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Create ActionBar.
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(com.technion.coolie.R.menu.menu, menu);

		// Text changed listener for search implementation.
		searchView = (SearchView) menu
				.findItem(com.technion.coolie.R.id.search).getActionView();
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String arg0) {

				return false;
			}

			@Override
			public boolean onQueryTextChange(String textToSearch) {
				ContactSummaryFragment.adapter.getFilter().filter(textToSearch);
				return false;
			}

		});
		return true;
	}

	private class ClientAsyncContacts extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(getApplicationContext(), "Loading...",
					Toast.LENGTH_LONG).show();
		}

		@Override
		protected String doInBackground(final Void... params) {
			final ITeletech teletechAPI = TeletechFactory.getTeletechAPI();

			MainActivity.master = teletechAPI.getAllContacts();
			return null;
		}

	}

}
