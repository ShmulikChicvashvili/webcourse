package com.technion.coolie.skeleton;

import java.util.List;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.technion.coolie.skeleton.CoolieAccount;
import com.technion.coolie.R;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;


public class PreferencesScreen extends SherlockPreferenceActivity {

	@SuppressWarnings("deprecation")
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    if (Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB) {
	      addPreferencesFromResource(R.xml.skel_preferences);
	      addPreferencesFromResource(R.xml.skel_accounts_preferences);
	      
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
	      addPreferencesFromResource(R.xml.skel_accounts_preferences);
	      for(final CoolieAccount acc : CoolieAccount.values())
	      {
	    	  if(!acc.equals(CoolieAccount.NONE))
	    	  {
	    		  final AccountPreference pref = acc.getPreference(getActivity());
	    		  pref.setKey(acc.getPreferenceName());
	    		  pref.setEnabled(true);
	    		  pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					
					@Override
					public boolean onPreferenceClick(Preference arg0) {
						
						Dialog d = pref.getDialog();
						d.show();
						
						//SignonDialog dialog = new SignonDialog(acc);
						
						//getFragmentManager()
						//dialog.show(, "bla");
						return false;
					}
	    		  });
	    		  //pref.onClick();
	    		  getPreferenceScreen().addPreference(pref);
	    	  }
	      }
	    }
	    
	  }
}
