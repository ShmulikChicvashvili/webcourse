package com.technion.coolie.techlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

import com.technion.coolie.techlibrary.SearchElements.SearchFragment;

public class MainActivity extends CoolieActivity implements
		ActionBar.OnNavigationListener {
	private String[] droplist = {  "Library Card" , "Open Hours", "Search"};

	// shared pref
	private SharedPreferences mSharedPref;
	private SharedPreferences.Editor mSharedPrefEditor;

	public static final String LOGGED_IN = "is_logged";
	private static final String SHARED_PREF = "lib_pref";
	//to save state of navigation list
	private static final String LAST_VIEWED_FRAGMENT_TAG = "last_item";

	public static int currPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lib_activity_main);

		// setting the dropdown list
		ArrayAdapter<String> list = new ArrayAdapter<String>(this,
				R.layout.sherlock_spinner_item, droplist);

		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list, this);
		//check if list item position is saved
		getSupportActionBar().setSelectedNavigationItem(savedInstanceState != null
	            ? savedInstanceState.getInt(LAST_VIEWED_FRAGMENT_TAG) : 0);

		SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF, 0);
		if (!sharedPref.contains(LOGGED_IN)) {
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(LOGGED_IN, false);
			editor.commit();
		}

		mSharedPref = getSharedPreferences(SHARED_PREF, 0);
		mSharedPrefEditor = mSharedPref.edit();
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		if (itemPosition == 0) {
			SherlockFragment frag = new LibraryCardFragment();
			currPosition = 0;
			transaction.replace(R.id.lib_frame_container, frag);
		} else if (itemPosition == 1) {
			SherlockFragment frag = new OpenHoursFragment();
			currPosition = 1;
			transaction.replace(R.id.lib_frame_container, frag);
		} else if (itemPosition == 2) {
			SherlockFragment frag = new SearchFragment();
			currPosition = 2;
			transaction.replace(R.id.lib_frame_container, frag);
		} 
		transaction.commit();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);

		// ~~~~~~~ PROFILE ~~~~~~~
		MenuItem profile = menu.add("Profile");
		profile.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		profile.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(MainActivity.this,
						ProfileActivity.class);
				startActivity(intent);
				return true;
			}
		});

		// TODO: change order of menu items!
		// ~~~~~~~ LOGOUT ~~~~~~~
		MenuItem logout = menu.add("Logout");
		logout.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		logout.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				mSharedPrefEditor.putBoolean(LOGGED_IN, false);
				mSharedPrefEditor.commit();
				
				finish(); // work?
				return true; // ????
			}
		});
		return true;
	}
	

    @Override
	public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
      //save list item position
        savedInstanceState.putInt(LAST_VIEWED_FRAGMENT_TAG, getSupportActionBar().getSelectedNavigationIndex());
    }
}
