package com.tecnion.coolie.ug.utils;

import java.io.Serializable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.technion.coolie.R;
import com.technion.coolie.ug.TransparentActivity;
import com.technion.coolie.ug.gui.courseDisplay.CourseDisplayFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchFragment;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.SearchFilters;

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
	public static void goToCourseDisplay(CourseKey key,
			FragmentActivity activity) {

		Bundle bundle = new Bundle();
		bundle.putSerializable(CourseDisplayFragment.ARGUMENTS_COURSE_KEY, key);

		if (activity.getResources().getConfiguration().orientation == activity
				.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
			int containerId = R.id.detail_container;
			Fragment newFragment = new CourseDisplayFragment();
			newFragment.setArguments(bundle);
			final FragmentTransaction transaction = activity
					.getSupportFragmentManager().beginTransaction();
			transaction.replace(containerId, newFragment);
			transaction.addToBackStack(null);
			transaction.commit();

		} else {
			Intent intent = new Intent(activity, TransparentActivity.class);
			bundle.putString("key", CourseDisplayFragment.class.toString());
			intent.putExtras(bundle);
			activity.startActivity(intent);
			return;

		}

	}

	public static void goToSearchDisplay(String query, SearchFilters filters,
			FragmentActivity activity) {
		Bundle bundle = new Bundle();
		Intent intent = new Intent(activity, TransparentActivity.class);
		bundle.putString("key", SearchFragment.class.toString());
		bundle.putSerializable(SearchFragment.ARGUMENT_QUERY_KEY, query);
		bundle.putSerializable(SearchFragment.ARGUMENT_FILTERS_KEY,
				(Serializable) filters);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		return;

	}
}
