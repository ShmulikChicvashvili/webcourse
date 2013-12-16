package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.CourseItem;

abstract public class BasePage extends Fragment {

	protected ArrayList<CourseItem> parentItems;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.ug_courses_and_exams_tab,
				container, false);
		final ExpandableListView expandableList = (ExpandableListView) view
				.findViewById(R.id.list);

		expandableList.setDividerHeight(2);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);
		setGroupParents();

		final CoursesAndExamsAdapter adapter = new CoursesAndExamsAdapter(
				parentItems, getActivity());

		adapter.setInflater(
				(LayoutInflater) getActivity().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE), getActivity());
		expandableList.setAdapter(adapter);
		return view;
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	abstract public void setGroupParents();
}
