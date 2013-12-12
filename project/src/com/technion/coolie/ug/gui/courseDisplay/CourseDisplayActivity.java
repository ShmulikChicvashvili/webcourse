package com.technion.coolie.ug.gui.courseDisplay;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.RegistrationGroup;

//TODO ADD FACULTY SOMEWHERE
//TODO onClick radio group
//TODO kdamim
//TODO add to maakav
//TODO selectable group list view

/**
 * activity for searching courses and finding available courses. must supply
 * course key in EXTRAS_COURSE_KEY before calling this activity/fragment.
 * 
 * 
 */
public class CourseDisplayActivity extends CoolieActivity {

	Course courseToView;
	Context context;
	CourseGroupsAdapter groupAdapter;

	public final static String EXTRAS_COURSE_KEY = "course";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ug_course_screen_layout);
		context = this;

		recieveCourse(savedInstanceState);
		initLayout();
		updateCourseDisplay();
	}

	private void initLayout() {
		RadioGroup b = (RadioGroup) findViewById(R.id.course_screen_semester_radio_group);
		// TODO set the names of the semesters in order.
		b.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find out what semster responds to that semster index(in
				// UGdatabase).
				// get course from new courseKey
				// if course is available call update coursedisplay
				// if course is not available replace with blank screen with not
				// learned at that semester text.

			}
		});

	}

	private void updateCourseDisplay() {
		TextView nameTextView = (TextView) findViewById(R.id.course_screen_name);
		nameTextView.setText(courseToView.getName());
		TextView pointsTextView = (TextView) findViewById(R.id.course_screen_points);
		pointsTextView.setText("" + courseToView.getPoints());
		TextView numberTextView = (TextView) findViewById(R.id.course_screen_number);
		numberTextView.setText("" + courseToView.getCourseNumber());
		TextView facultyTextView = (TextView) findViewById(R.id.course_screen_faculty);
		facultyTextView.setText("" + courseToView.getFaculty().toString());
		TextView descTextView = (TextView) findViewById(R.id.course_screen_description);
		descTextView.setText(courseToView.getDescription());
		TextView examATextView = (TextView) findViewById(R.id.course_screen_exam_a);
		examATextView.setText(courseToView.getMoedA().getTime().toString()
				+ ":מועד א");
		TextView examBTextView = (TextView) findViewById(R.id.course_screen_exam_b);
		examBTextView.setText(courseToView.getMoedB().getTime().toString()
				+ ":מועד ב");
		if (courseToView.getRegistrationGroups() != null)
			groupAdapter = new CourseGroupsAdapter(this,
					courseToView.getRegistrationGroups(), new onClickGroup());

	}

	private void updateRegistrationGroup(RegistrationGroup group) {
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

	static class onClickGroup implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			// mark group as selected TODO

		}

	}
}