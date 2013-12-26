package com.technion.coolie.ug.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.technion.coolie.ug.db.tablerows.AcademicEventRow;
import com.technion.coolie.ug.db.tablerows.AccomplishedCourseRow;
import com.technion.coolie.ug.db.tablerows.CourseRow;
import com.technion.coolie.ug.db.tablerows.TrackRow;
import com.technion.coolie.ug.model.Course;

public class UGDBProvider {

	private UGSqlHelper UGDatabaseHelper = null;

	Context context;

	/**
	 * 
	 * @param context
	 *            - the application context;
	 *            <P>
	 *            WARNING: must close the provider instance after end of use.
	 */
	public UGDBProvider(Context applicationContext) {
		context = applicationContext;
	}

	private UGSqlHelper getHelper() {
		if (UGDatabaseHelper == null) {
			UGDatabaseHelper = (UGSqlHelper) OpenHelperManager.getHelper(
					context, UGSqlHelper.class);
		}
		return UGDatabaseHelper;
	}

	public List<Course> getAllCourses() {
		List<CourseRow> list = null;

		try {
			list = getHelper().getCoursesDao().queryForAll();
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

	public Dao<CourseRow, String> getCoursesDao() {
		try {
			return getHelper().getCoursesDao();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException("Can't get courses table!");
		}
	}

	public Dao<AcademicEventRow, Long> getAcademicEventsDao() {
		try {
			return getHelper().getAcademicEventsDao();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException("Can't get courses table!");
		}
	}

	public Dao<AccomplishedCourseRow, Long> getAccopmlishedCoursesDao() {
		try {
			return getHelper().getAccopmlishedCoursesDao();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException("Can't get courses table!");
		}
	}

	public Dao<TrackRow, String> getTrackingDao() {
		try {
			return getHelper().getTrackingDao();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException("Can't get courses table!");
		}
	}

	/**
	 * this must be called when finishing the use of this class.
	 **/
	public void close() {
		if (UGDatabaseHelper != null) {
			OpenHelperManager.releaseHelper();
			UGDatabaseHelper = null;
		}
	}
}
