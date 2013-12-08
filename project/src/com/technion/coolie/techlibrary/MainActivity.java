package com.technion.coolie.techlibrary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class MainActivity extends CoolieActivity {


	private static final String LOGGED_IN = "is_logged";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.lib_activity_main);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
				
			}
		});
        
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
    
}


