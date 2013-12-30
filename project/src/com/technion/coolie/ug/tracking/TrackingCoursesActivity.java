package com.technion.coolie.ug.tracking;


import android.content.Context;
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

//import android.view.Menu;

public class TrackingCoursesActivity extends CoolieActivity {

	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ug_tracking_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// getSupportMenuInflater().inflate(R.menu.am_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
