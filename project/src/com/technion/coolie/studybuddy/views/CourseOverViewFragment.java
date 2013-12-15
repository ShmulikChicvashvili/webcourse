package com.technion.coolie.studybuddy.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.adapters.TaskAdapter;

public class CourseOverViewFragment extends SherlockFragment
{
	public static final String courseNameArg = "courseNameArg";
	private String courseName;

	public static CourseOverViewFragment newInstance(String course)
	{

		CourseOverViewFragment fragment = new CourseOverViewFragment();
		Bundle args = new Bundle();
		args.putString(courseNameArg, course);
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
			courseName = getArguments().getString(courseNameArg);
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
		View view = inflater
				.inflate(R.layout.stb_view_course_main, container, false);
		NowLayout layout = (NowLayout) view.findViewById(R.id.course_list);
		layout.setAdapter(new TaskAdapter(getActivity()));
		return view;

	}

}
