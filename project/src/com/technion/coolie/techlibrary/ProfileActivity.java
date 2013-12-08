package com.technion.coolie.techlibrary;


import com.technion.coolie.R;
import com.technion.coolie.techlibrary.LibraryCardActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	//code for login activity intent
	private static final int LOGGIN_CODE = 7;
	
	// pref logged key
	private static final String LOGGED_IN = "is_logged";
	
	private static final String SHARED_PREF = "lib_pref";

	private SharedPreferences mSharedPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lib_activity_profile);
		
		mSharedPref = getSharedPreferences(SHARED_PREF,0);
		// login?
		Intent intent = new Intent(this,
				LoginActivity.class);
		startActivityForResult(intent, LOGGIN_CODE);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case (LOGGIN_CODE): {
				if (resultCode != LoginActivity.RESULT_OK) {
					//TODO: generate error in login? maybe login was canceled?
				} else {
					String[] keys = { "user_first_name", "user_last_name", "user_id",
							 "user_address", "user_email", "user_telephone",
							 "user_home_library", "user_education_status" };
					String profileInfo = mSharedPref.getString(keys[0], "NULL") +"\n"+ 
						mSharedPref.getString(keys[1], "NULL")+"\n"+ 
						mSharedPref.getString(keys[2], "NULL")+"\n"+ 
						mSharedPref.getString(keys[3], "NULL")+"\n"+
						mSharedPref.getString(keys[4], "NULL")+"\n"+ 
						mSharedPref.getString(keys[5], "NULL")+"\n"+
						mSharedPref.getString(keys[6], "NULL") + "\n"+
						mSharedPref.getString(keys[7], "NULL") ;
						TextView tv = (TextView) findViewById(R.id.profileInfo);
						tv.setText(profileInfo);
				}
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_logout:
			SharedPreferences.Editor editor = mSharedPref.edit();
			editor.putBoolean(LOGGED_IN, false);
			editor.commit();
			finish();
			return true; //????
		}
		return super.onOptionsItemSelected(item);
	}

}
