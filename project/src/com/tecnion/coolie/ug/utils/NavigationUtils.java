package com.tecnion.coolie.ug.utils;

import java.io.Serializable;

import android.content.Context;
import android.content.Intent;

import com.technion.coolie.ug.gui.courseView.CourseDisplayActivity;
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
	public static void goToCourseDisplay(CourseKey key, Context context) {

		Intent intent = new Intent(context, CourseDisplayActivity.class);
		intent.putExtra(CourseDisplayActivity.EXTRAS_COURSE_KEY,
				(Serializable) key);
		context.startActivity(intent);
	}

}
