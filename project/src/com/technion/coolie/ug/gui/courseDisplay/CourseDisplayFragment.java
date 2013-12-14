package com.technion.coolie.ug.gui.courseDisplay;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

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
public class CourseDisplayFragment extends Fragment {

	Course courseToView;
	Context context;
	CourseGroupsAdapter groupAdapter;

	public final static String ARGUMENTS_COURSE_KEY = "course";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.ug_course_screen_layout, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		context = getActivity();
		recieveCourse(getArguments());
		initLayout();
		updateCourseDisplay();
		super.onActivityCreated(savedInstanceState);
	}

	private void initLayout() {
		RadioGroup b = (RadioGroup) getActivity().findViewById(
				R.id.course_screen_semester_radio_group);
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
		final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		TextView nameTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_name);
		nameTextView.setText(courseToView.getName());
		TextView pointsTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_points);
		pointsTextView.setText("" + courseToView.getPoints());
		TextView numberTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_number);
		numberTextView.setText("" + courseToView.getCourseNumber());

		TextView facultyTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_faculty);
		facultyTextView.setText("" + courseToView.getFaculty().toString());

		TextView descTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_description);
		descTextView.setText(courseToView.getDescription());

		TextView examATextView = (TextView) getActivity().findViewById(
				R.id.course_screen_exam_a);
		examATextView.setText(df.format(courseToView.getMoedA().getTime()));

		TextView examBTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_exam_b);
		examBTextView.setText(df.format(courseToView.getMoedB().getTime()));

		if (courseToView.getRegistrationGroups() != null)
			groupAdapter = new CourseGroupsAdapter(context,
					courseToView.getRegistrationGroups(), new onClickGroup());

	}

	private void updateRegistrationGroup(RegistrationGroup group) {
		// TODO Auto-generated method stub

	}

	private void recieveCourse(Bundle bundle) {

		CourseKey key = null;
		if (bundle == null) {
			Log.e(MainActivity.DEBUG_TAG, "CANT FIND COURSE EXTRAS , exisiting");
			throw new NullPointerException();
		}
		key = (CourseKey) bundle.getSerializable(ARGUMENTS_COURSE_KEY);
		courseToView = UGDatabase.INSTANCE.getCourseByKey(key);
		if (courseToView == null) {
			Log.e(MainActivity.DEBUG_TAG,
					"CANT FIND COURSEKEY IN DB, exisiting");
			throw new NullPointerException();
		}

	}

	static class onClickGroup implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			// mark group as selected TODO

		}

	}
}