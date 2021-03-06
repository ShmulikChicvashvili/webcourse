package com.technion.coolie.teletech;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.teletech.ContactSummaryFragment.OnContactSelectedListener;

public class PhoneBookActivity extends CoolieActivity implements
		OnContactSelectedListener, TabListener {

	public static List<ContactInformation> master;

	public static List<ContactInformation> contacts;

	public static ContactsAdapter adapter;

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	SearchView searchView;

	boolean favoriteSelected = false;

	DBTools db = new DBTools(this);

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		master = db.getAllContacts();

		contacts = new LinkedList<ContactInformation>();
		contacts.addAll(master);

		final int layout = com.technion.coolie.R.layout.teletech_contact_list;
		adapter = new ContactsAdapter(getApplicationContext(), layout, contacts);

		super.setContentView(R.layout.teletech_main);

		if (findViewById(com.technion.coolie.R.id.fragment_container) != null) {
			final FragmentTransaction trans = getSupportFragmentManager()
					.beginTransaction();
			trans.replace(com.technion.coolie.R.id.fragment_container,
					new ContactSummaryFragment());
			trans.commit();
		}
		setActionBar();

	}

	private void setActionBar() {

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.Tab tabContacts = actionBar.newTab();
		tabContacts.setText("ALL CONTACTS");
		tabContacts.setTabListener(this);
		actionBar.addTab(tabContacts);

		ActionBar.Tab tabFavs = actionBar.newTab();
		tabFavs.setText("FAVOURITES");
		tabFavs.setTabListener(this);
		actionBar.addTab(tabFavs);
	}


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
			args.putBoolean(FullContactInformation.ARG_FAVOURITE_STRING,
					favoriteSelected);
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

	public void onCheckboxClicked(View v) {
		boolean checked = ((CheckBox) v).isChecked();
		if (checked)
			addToFavourites();
		else
			removeFromFavourites();
	}

	void addToFavourites() {
		// TODO CHANGE LATER

		final int position = FullContactInformation.position();

		// added if for OutOfBoundsException
		if (position < PhoneBookActivity.contacts.size()) {
			final ContactInformation contact = PhoneBookActivity.contacts
					.get(position);
			contact.setFavourite(true);
			db.insertFavourite(contact);
		}

	}

	void removeFromFavourites() {
		// TODO CHANGE LATER

		final int position = FullContactInformation.position();
		// added if for OutOfBoundsException
		if (position < PhoneBookActivity.contacts.size()) {
			final ContactInformation contact = PhoneBookActivity.contacts
					.get(position);
			contact.setFavourite(false);
			db.deleteFavourite(contact.ID().toString());
		}
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
				adapter.getFilter().filter(textToSearch);
				return false;
			}

		});
		return true;
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current tab position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM))
			getSupportActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current tab position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getSupportActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		List<ContactInformation> favourites = db.getAllFavourites();

		favoriteSelected = false;
		contacts.clear();
		if (tab.getPosition() == 0)
			contacts.addAll(master);
		else {
			favoriteSelected = true;
			contacts.addAll(favourites);
		}
		if (adapter == null)
			Toast.makeText(getApplicationContext(), "ADAPTER IS NULL",
					Toast.LENGTH_SHORT).show();
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		//
	}

}
