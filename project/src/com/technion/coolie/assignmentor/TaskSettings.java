package com.technion.coolie.assignmentor;

import com.sileria.android.event.PrefsSeekBarListener;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.Menu;

public class TaskSettings extends CoolieActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		String taskNameTitle = getIntent().getStringExtra("taskName");
//		getActionBar().setTitle(taskNameTitle);
//		String courseNameSubtitle = getIntent().getStringExtra("courseName");
//		getActionBar().setSubtitle(courseNameSubtitle);
		
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		TaskSettingsFragment settingsFrag = new TaskSettingsFragment();
		// Add the tasks data to the bundle and pass it to the fragment.
		Bundle b = new Bundle();
		settingsFrag.setArguments(b);
		getFragmentManager().beginTransaction().replace(android.R.id.content, settingsFrag).commit();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
	}
	

	
	public static class TaskSettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

		private static final String KEY_TASK_NAME = "task_pref_task_name";
		private static final String KEY_COURSE_NAME = "task_pref_course_name";
		private static final String KEY_COURSE_ID = "task_pref_course_id";
		private static final String KEY_DUE_DATE = "task_pref_due_date";
		private static final String KEY_DIFFICULTY = "task_pref_difficulty";
		private static final String KEY_IMPORTANCE = "task_pref_importance";
		private static final String KEY_PROGRESS = "task_pref_progress";
		private static final String KEY_TASK_REMINDER = "task_pref_reminder";
		
		private Context c;
		
		@Override
		public void onAttach(Activity activity) {
			c = activity;
			super.onAttach(activity);
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.am_task_preferences);
			
			PreferenceCategory preferenceCategory = (PreferenceCategory) findPreference("task_pref_properties_title");
			
			RatingPreference difficultyPref = new RatingPreference(c);
			difficultyPref.setKey(KEY_DIFFICULTY);
			difficultyPref.setTitle(R.string.task_pref_difficulty);
			difficultyPref.setDefaultValue(1);
			difficultyPref.setMax(5);
			difficultyPref.setOnPreferenceChangeListener(new PrefsRatingBarListener(difficultyPref));
			preferenceCategory.addPreference(difficultyPref);
			
			RatingPreference importancePref = new RatingPreference(c);
			importancePref.setKey(KEY_IMPORTANCE);
			importancePref.setTitle(R.string.task_pref_importance);
			importancePref.setDefaultValue(1);
			importancePref.setMax(5);
			importancePref.setOnPreferenceChangeListener(new PrefsRatingBarListener(importancePref));
			preferenceCategory.addPreference(importancePref);
			
			ProgressPreference progressPref = new ProgressPreference(c);
			progressPref.setKey(KEY_PROGRESS);
			
			progressPref.setTitle(R.string.task_pref_progress);
			progressPref.setDefaultValue(0);
			progressPref.setMax(100);
			progressPref.setOnPreferenceChangeListener(new PrefsSeekBarListener(progressPref));
			preferenceCategory.addPreference(progressPref);
			
			
			PreferenceManager.setDefaultValues(c, R.xml.am_preferences, false);
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(c);
			sharedPrefs.registerOnSharedPreferenceChangeListener(this);
		}
		
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			
			// note that DatePreference handles the summary changes itself (for any changes in due date),
			// as well as Difficulty, Importance and Progress prefs.
			
			Preference pref;
			
			if(key.equals(KEY_TASK_NAME)) {
				pref = findPreference(key);
				pref.setSummary(sharedPreferences.getString(key, ""));
			} else if (key.equals(KEY_COURSE_NAME)) {
				pref = findPreference(key);
				pref.setSummary(sharedPreferences.getString(key, ""));
			} else if (key.equals(KEY_COURSE_ID)) {
				pref = findPreference(key);
				pref.setSummary(sharedPreferences.getString(key, ""));
			} else if (key.equals(KEY_DUE_DATE)) {
				pref = findPreference(key);
				pref.setSummary(sharedPreferences.getString(key, ""));
			} else if (key.equals(KEY_TASK_REMINDER)) {
				pref = findPreference(key);
				pref.setSummary(sharedPreferences.getString(key, ""));
			}
		}
	}
	
}