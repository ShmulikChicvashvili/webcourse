package com.technion.coolie.skeleton;

import java.util.List;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.technion.coolie.skeleton.PrivateCoolieAccount;
import com.technion.coolie.FacebookLogin;
import com.technion.coolie.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

public class PreferencesScreen extends SherlockPreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			addPreferencesFromResource(R.xml.skel_preferences);
			addAccounts(this, getPreferenceScreen());
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
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

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class AccountFrag extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setPreferenceScreen(getPreferenceManager().createPreferenceScreen(
					getActivity()));
			addAccounts(getActivity(), getPreferenceScreen());
		}

		
		
	}

	private static void addAccounts(final Activity act, PreferenceScreen screen) {
		for (final PrivateCoolieAccount acc : PrivateCoolieAccount.values()) {
			if (!acc.equals(PrivateCoolieAccount.NONE)) {
				final AccountPreference pref = acc.getPreference(act);
				pref.setKey(acc.getPreferenceName());
				pref.setEnabled(true);
				pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference arg0) {
						acc.openSigninDialog(act);
						return true;
					}
				});
				screen.addPreference(pref);
			}
		}
	}

	@Override
	protected boolean isValidFragment(String fragmentName) {
		if (PreferenceFrag.class.getName().equals(fragmentName)
				|| AccountFrag.class.getName().equals(fragmentName))
			return true;
		return super.isValidFragment(fragmentName);
	}
	   @Override 
	     protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
		   if (PrivateCoolieAccount.FACEBOOK.mLoggedAccount == null){
	    		 FacebookLogin.onResult(this, requestCode, resultCode, data);
	    		 return;
	    	 }
	   }

}
