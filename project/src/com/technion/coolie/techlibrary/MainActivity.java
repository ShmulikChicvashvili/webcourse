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

public class MainActivity extends CoolieActivity implements ActionBar.OnNavigationListener {
	private String[] droplist = {"Library Card"};

	private static final String LOGGED_IN = "is_logged";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_activity_main);
        
        //setting the dropdown list
        ArrayAdapter<String> list = new ArrayAdapter<String>(this, R.layout.sherlock_spinner_item, droplist);
        
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(list, this);
        
        /*******************************************/
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LibraryCardActivity.class);
				startActivity(intent);
			}
		});
        
        //open shared file and set login as false if not exists.
        SharedPreferences sharedPref = getSharedPreferences("lib_pref",
				0);
        if(!sharedPref.contains(LOGGED_IN)) {
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(LOGGED_IN , false);
        }
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Boolean result = super.onCreateOptionsMenu(menu);
        
        MenuItem profile = menu.add("Profile");
        profile.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        profile.setOnMenuItemClickListener(new OnMenuItemClickListener()
        {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
				startActivity(intent);
				return true;
			}
		});
        return result;
    }
	
	@Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
//		SherlockFragment frag = new LibraryCardFragment();
//        // we get the 'childFragmentManager' for our transaction
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        // make the back button return to the main screen
//        // and supply the tag 'left' to the backstack
////        transaction.addToBackStack("left");
//        // add our new nested fragment
//        transaction.add(frag, "left");
//        // commit the transaction
//        transaction.commit();
//		
        return true;
    }
}


