package com.technion.coolie.studybuddy.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.adapters.CourseListAdapter;
import com.technion.coolie.studybuddy.data.DataStore;

/**
 * Created by arik on 10/30/13.
 */
public class MainView extends SherlockFragment
{
	private static final String	ARG_SECTION_NUMBER	= "section_number";

	public static MainView newInstance(int sectionNumber)
	{
		MainView fragment = new MainView();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = null;
		rootView = inflater.inflate(R.layout.stb_view_main, container, false);

		NowLayout layout = (NowLayout) rootView.findViewById(R.id.course_list);

		CourseListAdapter adapter = new CourseListAdapter(getActivity());
		layout.setAdapter(adapter);
		DataStore.getInstance().addObserver(adapter);

		return rootView;
	}
}