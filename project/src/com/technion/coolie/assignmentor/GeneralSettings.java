package com.technion.coolie.assignmentor;

import com.technion.coolie.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class GeneralSettings extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public static final String KEY_GS_CALENDAR_SYNC = "pref_sync";
	public static final String KEY_GS_UPDATES_FREQ = "pref_updates_freq";
	public static final String KEY_GS_REMINDER = "pref_reminder";
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.am_preferences);
		PreferenceManager.setDefaultValues(this, R.xml.am_preferences, false);
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		// Settings prefs summary to match the current saved prefs.
		Preference pref = findPreference(KEY_GS_UPDATES_FREQ);
		String summary = sharedPrefs.getString(KEY_GS_UPDATES_FREQ, "Never");
		pref.setSummary(summary);
		
		pref = findPreference(KEY_GS_REMINDER);
		summary = sharedPrefs.getString(KEY_GS_REMINDER, "No Reminder");
		pref.setSummary(summary);
		
		sharedPrefs.registerOnSharedPreferenceChangeListener(this);
		getActionBar().setSubtitle("AssignMentor - Genreal Settings");
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		
		Preference pref;
		
		if (key.equals(KEY_GS_CALENDAR_SYNC)) {
			pref = findPreference(key);
		} else if (key.equals(KEY_GS_UPDATES_FREQ)) {
			pref = findPreference(key);
			pref.setSummary(sharedPreferences.getString(key, ""));
		} else if (key.equals(KEY_GS_REMINDER)) {
			pref = findPreference(key);
			pref.setSummary(sharedPreferences.getString(key, ""));
		}
		onContentChanged();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

}
