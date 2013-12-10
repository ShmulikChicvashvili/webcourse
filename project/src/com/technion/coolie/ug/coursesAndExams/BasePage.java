package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.technion.coolie.R;

abstract public class BasePage extends Fragment {

	
	protected ArrayList<CourseItem> parentItems = new ArrayList<CourseItem>();
	protected ArrayList<Object> childItems = new ArrayList<Object>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("ENTER");
		View view = inflater.inflate(R.layout.ug_courses_and_exams_tab,
				container, false);
		ExpandableListView expandableList = (ExpandableListView) view
				.findViewById(R.id.list);

		expandableList.setDividerHeight(2);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);

		setGroupParents();
		setChildData();

		CoursesAndExamsAdapter adapter = new CoursesAndExamsAdapter(
				parentItems, childItems);

		adapter.setInflater(
				(LayoutInflater) getActivity().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE), getActivity());
		expandableList.setAdapter(adapter);
		// expandableList.setOnChildClickListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	abstract public void setGroupParents();
	abstract public void setChildData();
}
