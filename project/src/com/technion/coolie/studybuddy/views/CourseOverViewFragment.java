package com.technion.coolie.studybuddy.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.adapters.TaskAdapter;
import com.technion.coolie.studybuddy.data.DataStore;
import com.technion.coolie.studybuddy.graphs.GraphFactory;
import com.technion.coolie.studybuddy.models.Course;

public class CourseOverViewFragment extends SherlockFragment
{
	public static final String courseNumberArg = "courseNameArg";
	private String courseNumber;
	private Course course;

	public static CourseOverViewFragment newInstance(String courseNumber)
	{

		CourseOverViewFragment fragment = new CourseOverViewFragment();
		Bundle args = new Bundle();
		args.putString(courseNumberArg, courseNumber);
		fragment.setArguments(args);
		return fragment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
		{
			courseNumber = getArguments().getString(courseNumberArg);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		course = DataStore.coursesById.get(courseNumber);
		View view = inflater.inflate(R.layout.stb_view_course_main, container,
				false);
		NowLayout layout = (NowLayout) view.findViewById(R.id.course_list);
		layout.setAdapter(new TaskAdapter(getActivity()));
		LinearLayout linearLayout = (LinearLayout) view
				.findViewById(R.id.Chart_layout);
		View barChartView = GraphFactory.getProgressGraphFromCourseAndSemester(
				inflater.getContext(), course, DataStore.semester);
		linearLayout.addView(barChartView);
		return view;

	}

}
