package com.technion.coolie.ug.utils;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.technion.coolie.R;
import com.technion.coolie.ug.TransparentActivity;
import com.technion.coolie.ug.gui.courseDisplay.CourseDisplayFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchFilters;
import com.technion.coolie.ug.gui.searchCourses.SearchFragment;
import com.technion.coolie.ug.model.CourseKey;

/**
 * methods for navigation between the different fragments/activities in the ug
 * module
 * 
 */
public class NavigationUtils {

	/**
	 * opens courseDisplay activity with the specified course key
	 * 
	 * @param key
	 * @param context
	 */
	public static void goToCourseDisplay(final CourseKey key,
			final FragmentActivity activity) {

		final Bundle bundle = new Bundle();
		bundle.putSerializable(CourseDisplayFragment.ARGUMENTS_COURSE_KEY, key);

		activity.getResources().getConfiguration();
		if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			final int containerId = R.id.detail_container;
			final Fragment newFragment = new CourseDisplayFragment();
			newFragment.setArguments(bundle);
			final FragmentTransaction transaction = activity
					.getSupportFragmentManager().beginTransaction();
			transaction.replace(containerId, newFragment);
			transaction.addToBackStack(null);
			transaction.commit();

		} else {
			final Intent intent = new Intent(activity,
					TransparentActivity.class);
			bundle.putString("key", CourseDisplayFragment.class.toString());
			intent.putExtras(bundle);
			activity.startActivity(intent);
			return;

		}

	}

	public static void goToSearchDisplay(final String query,
			final SearchFilters filters, final FragmentActivity activity) {
		final Bundle bundle = new Bundle();
		final Intent intent = new Intent(activity, TransparentActivity.class);
		bundle.putString("key", SearchFragment.class.toString());
		bundle.putSerializable(SearchFragment.ARGUMENT_QUERY_KEY, query);
		bundle.putSerializable(SearchFragment.ARGUMENT_FILTERS_KEY, filters);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		return;

	}
}
