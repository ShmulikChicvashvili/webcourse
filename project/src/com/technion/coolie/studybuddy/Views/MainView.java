package com.technion.coolie.studybuddy.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.Adapters.CourseAdapter;

/**
 * Created by arik on 10/30/13.
 */
public class MainView extends SherlockFragment
{
	private static final String ARG_SECTION_NUMBER = "section_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = null;
		rootView = inflater.inflate(R.layout.stb_view_main, container, false);

		ImageView imageView = (ImageView) rootView
				.findViewById(R.id.graph_view);
		imageView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getActivity().startActivity(
						new Intent(getActivity(), TasksActicity.class));
			}
		});
		NowLayout layout = (NowLayout) rootView.findViewById(R.id.course_list);
	
		CourseAdapter adapter = new CourseAdapter(getActivity());
		layout.setAdapter(adapter);
		return rootView;
	}

	public static MainView newInstance(int sectionNumber)
	{
		MainView fragment = new MainView();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}
}