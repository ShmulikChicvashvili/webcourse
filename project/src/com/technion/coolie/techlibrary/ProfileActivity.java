package com.technion.coolie.techlibrary;


import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.LibraryCardActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

public class ProfileActivity extends CoolieActivity{
	//code for login activity intent
	private static final int LOGGIN_CODE = 7;
	
	// pref logged key
	private static final String LOGGED_IN = "is_logged";
	
	private static final String SHARED_PREF = "lib_pref";

	private SharedPreferences mSharedPref;
	private Editor mSharedPrefEditor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lib_activity_profile);
		
		mSharedPref = getSharedPreferences(SHARED_PREF,0);
		mSharedPrefEditor = mSharedPref.edit();
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
		super.onCreateOptionsMenu(menu);
		
		//TODO: change order of menu items!
		MenuItem logout = menu.add("Logout");
		logout.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		logout.setOnMenuItemClickListener(new OnMenuItemClickListener()
        {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				mSharedPrefEditor.putBoolean(LOGGED_IN, false);
				mSharedPrefEditor.commit();
				finish(); //work?
				return true; //????
			}
		});
		return true;
	}
}
	
