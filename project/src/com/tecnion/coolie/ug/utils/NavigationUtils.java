package com.tecnion.coolie.ug.utils;

import java.io.Serializable;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.technion.coolie.ug.gui.courseDisplay.CourseDisplayActivity;
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
	public static void goToCourseDisplay(CourseKey key,
			FragmentActivity activity/* , int placeHolderResourceId */) {

		Intent intent = new Intent(activity, CourseDisplayActivity.class);
		intent.putExtra(CourseDisplayActivity.EXTRAS_COURSE_KEY,
				(Serializable) key);
		activity.startActivity(intent);

		// Fragment newFragment = new SearchFragment();
		// final FragmentTransaction transaction = activity
		// .getSupportFragmentManager().beginTransaction();
		// transaction.replace(placeHolderResourceId, newFragment);
		// transaction.addToBackStack(null);
		// transaction.commit();

	}

}
