package com.technion.coolie.ug.gui.courseView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;

/**
 * activity for searching courses and finding available courses. must supply
 * course key in EXTRAS_COURSE_KEY before calling this activity/fragment.
 * 
 * 
 */
public class CourseDisplayActivity extends CoolieActivity {

	Course courseToView;
	Context context;
	public final static String EXTRAS_COURSE_KEY = "course";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_screen_layout);
		context = this;

		recieveCourse(savedInstanceState);
		updateCourseDisplay();
	}

	private void updateCourseDisplay() {
		// TODO Auto-generated method stub

	}

	private void recieveCourse(Bundle savedInstanceState) {

		CourseKey key = null;
		if (savedInstanceState != null) {
			key = (CourseKey) savedInstanceState
					.getSerializable(EXTRAS_COURSE_KEY);
		} else if (getIntent().getExtras() != null) {
			key = (CourseKey) getIntent().getExtras().getSerializable(
					EXTRAS_COURSE_KEY);
		} else {
			Log.e(MainActivity.DEBUG_TAG, "CANT FIND COURSE EXTRAS , exisiting");
			finish();
		}

		courseToView = UGDatabase.INSTANCE.getCourseByKey(key);
		if (courseToView == null) {
			Log.e(MainActivity.DEBUG_TAG, "CANT FIND COURSE  , exisiting");
			finish();
		}

	}
}