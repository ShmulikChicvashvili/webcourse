package com.technion.coolie.studybuddy.views;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

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
import com.technion.coolie.studybuddy.presenters.CoursePresenter;

public class CourseOverViewFragment extends SherlockFragment implements
		Observer
{
	public static final String courseNumberArg = "courseNameArg";
	private LayoutInflater inflater;

	public static CourseOverViewFragment newInstance(String courseNumber)
	{

		CourseOverViewFragment fragment = new CourseOverViewFragment();
		Bundle args = new Bundle();
		args.putString(courseNumberArg, courseNumber);
		fragment.setArguments(args);
		return fragment;
	}

	private String courseNumber;

	private Course course;

	private CoursePresenter presenter;
	private View fragmentView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		DataStore.getInstance().addObserver(this);
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
		presenter = DataStore.getInstance().getCoursePresenter(courseNumber);
		this.inflater = inflater;
		fragmentView = inflater.inflate(R.layout.stb_view_course_main,
				container, false);
		//NowLayout layout = (NowLayout) fragmentView
			//	.findViewById(R.id.course_list);

		//layout.setAdapter(new TaskAdapter(getActivity()));
		drawGraph();

		return fragmentView;

	}

	@Override
	public void update(Observable observable, Object data)
	{
		if (fragmentView != null)
			drawGraph();
	}

	private void drawGraph()
	{
		LinearLayout linearLayout = (LinearLayout) fragmentView
				.findViewById(R.id.Chart_layout);
		View barChartView = GraphFactory.getCourseProgressGraph(
				inflater.getContext(), presenter.getProgressMap(),
				presenter.getCurrentWeekNum(new Date()),
				presenter.getSemesterLength());
		linearLayout.addView(barChartView);
	}

}
