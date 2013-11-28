package com.technion.coolie.skeleton;

import java.util.List;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.technion.coolie.R;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;


public class PreferencesScreen extends SherlockPreferenceActivity {

	@SuppressWarnings("deprecation")
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    if (Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB) {
	      addPreferencesFromResource(R.xml.skel_preferences);
	    }
	  }

	  @Override
	  public void onBuildHeaders(List<Header> target) {
	    loadHeadersFromResource(R.xml.skel_preference_headers, target);
	  }
	  
	  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	  public static class PreferenceFrag extends PreferenceFragment {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);

	      addPreferencesFromResource(R.xml.skel_preferences);
	    }
	  }
}
