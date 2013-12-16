package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Semester;

public class TrackingCoursesListFragment extends ListFragment {

	List<CourseKey> trackingCoursesList = new ArrayList<CourseKey>() {
		{

			add(new CourseKey("234111", new Semester(2014,
					SemesterSeason.WINTER)));
			add(new CourseKey("234123", new Semester(2014,
					SemesterSeason.WINTER)));
			add(new CourseKey("234141", new Semester(2014,
					SemesterSeason.WINTER)));
			add(new CourseKey("094412", new Semester(2014,
					SemesterSeason.WINTER)));
		}
	};

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		final TrackingListAdapter adapter = new TrackingListAdapter(
				inflater.getContext(), trackingCoursesList);

		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}
}