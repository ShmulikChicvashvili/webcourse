package com.technion.coolie.skeleton;

import com.technion.coolie.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;


public class MyPreferencesScreen extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.skel_preferences);
	}
}