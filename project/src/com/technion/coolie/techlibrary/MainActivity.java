package com.technion.coolie.techlibrary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import com.technion.coolie.techlibrary.BookItems;
import com.technion.coolie.techlibrary.BookItems.LibraryElement;
import com.technion.coolie.techlibrary.SearchElements.SearchFragment;

public class MainActivity extends CoolieActivity implements
		ActionBar.OnNavigationListener {
	private String[] droplist = { "Search", "Open Hours", "Library Card" };

	private LibraryCardFragment fLibCard = null;

	// shared pref
	private SharedPreferences mSharedPref;
	private SharedPreferences.Editor mSharedPrefEditor;

	public static final String LOGGED_IN = "is_logged";
	private static final String SHARED_PREF = "lib_pref";
	// to save state of navigation list
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
		// check if list item position is saved
		getSupportActionBar().setSelectedNavigationItem(
				savedInstanceState != null ? savedInstanceState
						.getInt(LAST_VIEWED_FRAGMENT_TAG) : 0);

		SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF, 0);
		if (!sharedPref.contains(LOGGED_IN)) {
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(LOGGED_IN, false);
			editor.commit();
		}
		// fLibCard = new LibraryCardFragment();
		mSharedPref = getSharedPreferences(SHARED_PREF, 0);
		mSharedPrefEditor = mSharedPref.edit();
		
		//TESTING!!!!!!!!!!!
//		Intent intent = new Intent(this, testIntentService.class); 
//		intent.putExtra("userID", mSharedPref.getString("user_id", null));
//		startService(intent);
		
//		startAlarm();     //<<---- works.... not after rebooting
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		if (itemPosition == 2) {
			fLibCard = new LibraryCardFragment();
			Log.d("the activity in the main is", "" + fLibCard);
			currPosition = 2;
			transaction.replace(R.id.lib_frame_container, fLibCard);
		} else if (itemPosition == 1) {
			SherlockFragment frag = new OpenHoursFragment();
			currPosition = 1;
			transaction.replace(R.id.lib_frame_container, frag);
		} else if (itemPosition == 0) {
			SherlockFragment frag = new SearchFragment();
			currPosition = 0;
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

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		//TODO use request code to identify calls
		if (data != null && data.getStringExtra("activity") != null && data.getStringExtra("activity").equals("bookDescription")) {
			Log.d("onnnn reeeesssuult", "YEA");
//			addToWishList((new BookItems()).new LibraryElement("1",
//					data.getStringExtra("name"), "empty", "book", "C.S"));
		}
	}

	public void addToWishList(LibraryElement libElement) {
		fLibCard.addToWishList(libElement);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		// save list item position
		savedInstanceState.putInt(LAST_VIEWED_FRAGMENT_TAG,
				getSupportActionBar().getSelectedNavigationIndex());
	}
	
	public void startAlarm(/*long intervalMilis*/) {
		Log.d("int startAlarm", "begin");
		Intent intent = new Intent(this, testIntentService.class);
		if(mSharedPref.getString("user_id", null) == null){
			Log.d("startAlarm", "user_id = null");
			return;
		}
		intent.putExtra("userID", mSharedPref.getString("user_id", null));
		PendingIntent mAlarmSender = PendingIntent.getService(this, 1234,
				intent, 0);

		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME,
				SystemClock.elapsedRealtime(),30000/* 30 seconds*/, mAlarmSender);
		///////////////////////////////////////////////////////////////////
		
		Log.d("int startAlarm", "setComponentEnabledSetting.. not working");
		ComponentName receiver = new ComponentName(this, BootReceiver.class);
		PackageManager pm = this.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);

		// (1)
		/*
		 * (2) public class SampleBootReceiver extends BroadcastReceiver {
		 * 
		 * @Override public void onReceive(Context context, Intent intent) { if
		 * (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
		 * // Set the alarm here. } } }
		 * 
		 * (3) (4) //enable the resiever when the user starts the alarms.....
		 * ComponentName receiver = new ComponentName(context,
		 * SampleBootReceiver.class); PackageManager pm =
		 * context.getPackageManager();
		 * 
		 * pm.setComponentEnabledSetting(receiver,
		 * PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
		 * PackageManager.DONT_KILL_APP);
		 */

	}
}
