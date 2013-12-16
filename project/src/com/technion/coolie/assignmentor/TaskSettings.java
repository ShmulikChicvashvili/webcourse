package com.technion.coolie.assignmentor;

import com.sileria.android.event.PrefsSeekBarListener;
import com.sileria.android.view.SeekBarPreference;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

public class TaskSettings extends CoolieActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(android.R.layout.preference_category);
		
		int position = getIntent().getIntExtra("position", 0);
		TasksInfo task = (TasksInfo) MainActivity.mAdapter.getItem(position);
		String subtitle = task.taskName + " - " + task.courseName;
		getActionBar().setSubtitle(subtitle);
		
//		String[] info = getIntent().getStringArrayExtra("info");
//		int[] properties = getIntent().getIntArrayExtra("properties");
		Log.i(MainActivity.AM_TAG, "TaskSettings -> onCreate -> item position: " + String.valueOf(position));
		
		
		TaskSettingsFragment settingsFrag = new TaskSettingsFragment();
		
		// Add the tasks data to the bundle and pass it to the fragment.
		Bundle args = new Bundle();
		args.putInt("position", position);
//		b.putStringArray("info", info);
//		b.putIntArray("properties", properties);
		settingsFrag.setArguments(args);
		getFragmentManager().beginTransaction().replace(android.R.id.content, settingsFrag).commit();

		Intent resultIntent = new Intent();
		resultIntent.putExtra("position", position);
		setResult(RESULT_OK, resultIntent);
		
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		return super.onCreateOptionsMenu(menu);
//	}
	
	public static class TaskSettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

		public static final String KEY_TS_TASK_NAME = "task_pref_task_name";
		public static final String KEY_TS_COURSE_NAME = "task_pref_course_name";
		public static final String KEY_TS_COURSE_ID = "task_pref_course_id";
		public static final String KEY_TS_DUE_DATE = "task_pref_due_date";
		public static final String KEY_TS_DIFFICULTY = "task_pref_difficulty";
		public static final String KEY_TS_IMPORTANCE = "task_pref_importance";
		public static final String KEY_TS_PROGRESS = "task_pref_progress";
		public static final String KEY_TS_TASK_REMINDER = "task_pref_reminder";
		public static final String KEY_TS_COURSE_WEBSITE = "task_pref_course_website";
		
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
			
			int position = getArguments().getInt("position");
			Log.i(MainActivity.AM_TAG, "Setting properties for item at position: " + String.valueOf(position));
			
			// Initialize special preferences such as rating bars (difficulty, importance) 
			// and seek bar (progress).
			PreferenceCategory preferenceCategory = (PreferenceCategory) findPreference("task_pref_properties_title");
			
			RatingPreference difficultyPref = new RatingPreference(c);
			difficultyPref.setKey(KEY_TS_DIFFICULTY);
			difficultyPref.setTitle(R.string.task_pref_difficulty);
			difficultyPref.setDefaultValue(1);
			difficultyPref.setMax(5);
			difficultyPref.setOnPreferenceChangeListener(new PrefsRatingBarListener(difficultyPref));
			preferenceCategory.addPreference(difficultyPref);
			
			RatingPreference importancePref = new RatingPreference(c);
			importancePref.setKey(KEY_TS_IMPORTANCE);
			importancePref.setTitle(R.string.task_pref_importance);
			importancePref.setDefaultValue(1);
			importancePref.setMax(5);
			importancePref.setOnPreferenceChangeListener(new PrefsRatingBarListener(importancePref));
			preferenceCategory.addPreference(importancePref);
			
			ProgressPreference progressPref = new ProgressPreference(c);
			progressPref.setKey(KEY_TS_PROGRESS);
			
			progressPref.setTitle(R.string.task_pref_progress);
			progressPref.setDefaultValue(0);
			progressPref.setMax(100);
			progressPref.setOnPreferenceChangeListener(new PrefsSeekBarListener(progressPref));
			preferenceCategory.addPreference(progressPref);
			
			PreferenceManager.setDefaultValues(c, R.xml.am_preferences, false);
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(c);
			sharedPrefs.registerOnSharedPreferenceChangeListener(this);
			
			// Set the settings from the task that were clicked.
			setTaskProperties();
		}
		
		@Override
		public void onPause() {
			
			int position = getArguments().getInt("position");
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(c);
			int difficulty = sharedPrefs.getInt(KEY_TS_DIFFICULTY, 0);
			int importance = sharedPrefs.getInt(KEY_TS_IMPORTANCE, 0);
			int progress = sharedPrefs.getInt(KEY_TS_PROGRESS, 0);
			MainActivity.mAdapter.setProperties(position, difficulty, importance, progress);
			super.onPause();
		}
		
		private void setTaskProperties() {
			int position = getArguments().getInt("position");
			Log.i(MainActivity.AM_TAG, "Setting properties for item at position: " + String.valueOf(position));
			
			TasksInfo task = (TasksInfo) MainActivity.mAdapter.getItem(position);
			
			// Display the clicked task properties.
			findPreference(KEY_TS_TASK_NAME).setSummary(task.taskName.toString());
			findPreference(KEY_TS_COURSE_NAME).setSummary(task.courseName);
			findPreference(KEY_TS_COURSE_ID).setSummary(task.courseId);
			findPreference(KEY_TS_DUE_DATE).setSummary(task.dueDate);
			findPreference(KEY_TS_COURSE_NAME).setSummary(task.courseName);
			Intent myIntent = new Intent("android.intent.action.VIEW", Uri.parse(task.url));
			findPreference(KEY_TS_COURSE_WEBSITE).setIntent(myIntent);
			RatingPreference ratingPref = (RatingPreference) findPreference(KEY_TS_DIFFICULTY);
			ratingPref.setRating(task.difficulty);
			
			ratingPref = (RatingPreference) findPreference(KEY_TS_IMPORTANCE);
			ratingPref.setRating(task.importance);
			
			SeekBarPreference sbPref = (SeekBarPreference) findPreference(KEY_TS_PROGRESS);
			sbPref.setProgress(task.progress);
			
		}
		
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			
			// note that DatePreference handles the summary changes itself (for any changes in due date),
			// as well as Difficulty, Importance and Progress prefs.
			
			Preference pref;
			
			if(key.equals(KEY_TS_TASK_NAME)) {
				pref = findPreference(key);
				pref.setSummary(sharedPreferences.getString(key, ""));
			} else if (key.equals(KEY_TS_COURSE_NAME)) {
				pref = findPreference(key);
				pref.setSummary(sharedPreferences.getString(key, ""));
			} else if (key.equals(KEY_TS_COURSE_ID)) {
				pref = findPreference(key);
				pref.setSummary(sharedPreferences.getString(key, ""));
			} else if (key.equals(KEY_TS_DUE_DATE)) {
				pref = findPreference(key);
				pref.setSummary(sharedPreferences.getString(key, ""));
			} else if (key.equals(KEY_TS_TASK_REMINDER)) {
				pref = findPreference(key);
				pref.setSummary(sharedPreferences.getString(key, ""));
			}
		}
	}
}