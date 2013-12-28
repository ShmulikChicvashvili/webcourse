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
import com.technion.coolie.ug.model.AcademicCalendarEvent;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;

/**
 * contains all functions for accessing the UG database. must close this
 * provider class after finishing using it.
 */
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

	public void updateCourses(List<Course> list) {
		try {
			for (Course course : list) {
				getHelper().getCoursesDao().createOrUpdate(
						new CourseRow(course));
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
			throw new NullPointerException();
		}
	}

	public void setTrackingCourses(List<CourseKey> courses) {
		try {
			// clear all courses
			List<TrackRow> list = getHelper().getTrackingDao().queryForAll();
			getHelper().getTrackingDao().delete(list);
			// add courses
			for (CourseKey course : courses) {
				getHelper().getTrackingDao().create(new TrackRow(course));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<CourseKey> getTrackingCourse() {
		List<TrackRow> list = null;
		try {
			list = getHelper().getTrackingDao().queryForAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		List<CourseKey> $ = new ArrayList<CourseKey>();
		for (TrackRow row : list) {
			$.add(CourseKey.keyStringToCourseKey(row.getKey()));
		}
		return $;

	}

	public void setAccomplishedCourses(List<AccomplishedCourse> courses) {
		try {
			// clear all courses
			List<AccomplishedCourseRow> list = getHelper()
					.getAccopmlishedCoursesDao().queryForAll();
			getHelper().getAccopmlishedCoursesDao().delete(list);
			// add courses
			for (AccomplishedCourse course : courses) {
				getHelper().getAccopmlishedCoursesDao().create(
						new AccomplishedCourseRow(course));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<AccomplishedCourse> getAccomplishedCourses() {
		List<AccomplishedCourseRow> list = null;
		try {
			list = getHelper().getAccopmlishedCoursesDao().queryForAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		List<AccomplishedCourse> $ = new ArrayList<AccomplishedCourse>();
		for (AccomplishedCourseRow row : list) {
			$.add(row.getCourse());
		}
		return $;
	}

	public void setAcademicEvents(List<AcademicCalendarEvent> events) {
		try {
			// clear all events
			List<AcademicEventRow> list = getHelper().getAcademicEventsDao()
					.queryForAll();
			getHelper().getAcademicEventsDao().delete(list);
			// add events
			for (AcademicCalendarEvent academicCalendarEvent : events) {
				getHelper().getAcademicEventsDao().create(
						new AcademicEventRow(academicCalendarEvent));
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
			throw new NullPointerException();
		}
	}

	public List<AcademicCalendarEvent> getAllAcademicEvent() {
		List<AcademicEventRow> list = null;
		try {
			list = getHelper().getAcademicEventsDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace(System.err);
			throw new NullPointerException();
		}
		List<AcademicCalendarEvent> $ = new ArrayList<AcademicCalendarEvent>();
		for (AcademicEventRow row : list) {
			$.add(row.getEvent());
		}
		return $;
	}

	Dao<CourseRow, String> getCoursesDao() {
		try {
			return getHelper().getCoursesDao();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException("Can't get courses table!");
		}
	}

	Dao<AcademicEventRow, Long> getAcademicEventsDao() {
		try {
			return getHelper().getAcademicEventsDao();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException("Can't get courses table!");
		}
	}

	Dao<AccomplishedCourseRow, Long> getAccopmlishedCoursesDao() {
		try {
			return getHelper().getAccopmlishedCoursesDao();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException("Can't get courses table!");
		}
	}

	Dao<TrackRow, String> getTrackingDao() {
		try {
			return getHelper().getTrackingDao();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException("Can't get courses table!");
		}
	}

	/**
	 * this method must be called when app finishes the use of this class.
	 **/
	public void close() {
		if (UGDatabaseHelper != null) {
			OpenHelperManager.releaseHelper();
			UGDatabaseHelper = null;
		}
	}

	private UGSqlHelper getHelper() {
		if (UGDatabaseHelper == null) {
			UGDatabaseHelper = (UGSqlHelper) OpenHelperManager.getHelper(
					context, UGSqlHelper.class);
		}
		return UGDatabaseHelper;
	}

}
