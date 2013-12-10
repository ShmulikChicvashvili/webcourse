package com.technion.coolie.ug;

import android.os.Bundle;

import com.technion.coolie.CoolieActivity;

public class MainActivity extends CoolieActivity {

	public static final String DEBUG_TAG = "DEBUG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.ug_search_fragment_try);

		// startActivity(new Intent(this, GradesSheetActivity.class));
	}

	// final FragmentManager fm = getSupportFragmentManager();
	// final FragmentTransaction transaction = fm.beginTransaction();
	// transaction.add(R.id.fragment_place_holder, startFragment);
	// transaction.commit();
}
