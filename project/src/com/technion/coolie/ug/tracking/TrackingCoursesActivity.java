package com.technion.coolie.ug.tracking;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.Course;

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
