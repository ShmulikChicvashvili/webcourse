package com.technion.coolie.ug.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.technion.coolie.ug.model.Course;

public class UGDBProvider {

	private Dao<CourseRow, String> courseDao;
	// all daos..

	private UGDatabaseHelper UGDatabaseHelper = null;

	/**
	 * 
	 * @param context
	 *            - the application context;
	 */
	public UGDBProvider(Context applicationContext) {
		try {
			courseDao = getHelper(applicationContext).getCoursesDao();
			// all daos..

		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}

	private UGDatabaseHelper getHelper(Context context) {
		if (UGDatabaseHelper == null) {
			UGDatabaseHelper = (UGDatabaseHelper) OpenHelperManager.getHelper(
					context, UGDatabaseHelper.class);
		}
		return UGDatabaseHelper;
	}

	public List<Course> getAllCourses() {
		List<CourseRow> list = null;

		try {
			list = courseDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace(System.err);
			throw new NullPointerException();
		}
		List<Course> $ = new ArrayList<Course>();
		for (CourseRow courseRow : list) {
			$.add(courseRow.getCourse());
		}
		return $;
	}

	/**
	 * this must be called when finishing the use of this class.
	 **/
	public void clean() {
		UGDatabaseHelper.close();
	}
}
