package com.technion.coolie.ug.gui.searchCourses;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technion.coolie.R;
<<<<<<< HEAD
=======
import com.technion.coolie.ug.ITrackingCourseTrasferrer;
>>>>>>> 1eb8cecff97625d97e095f9abf8d84c1190e5d27
import com.technion.coolie.ug.model.CourseKey;

public class SearchForTrackingFragment extends SearchFragment {
	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = inflater.inflate(R.layout.ug_search_screen_fragment,
				container, false);

		return view;
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	protected int onSearchPressed(final String query) {
		final int results = searchQueryAndUpdate(query);
		return results;
	}

	@Override
	public void goToCourseDisplay(CourseKey key, FragmentActivity activity) {
<<<<<<< HEAD
		// call matvey function with coursekey!!! TODO
=======
		((ITrackingCourseTrasferrer)activity).onCourseForTrackingSelected(key);
>>>>>>> 1eb8cecff97625d97e095f9abf8d84c1190e5d27
	}

}
