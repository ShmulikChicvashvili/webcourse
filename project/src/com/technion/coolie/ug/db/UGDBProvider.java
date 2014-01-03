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
import com.technion.coolie.ug.db.tablerows.RegisteredCourseRow;
import com.technion.coolie.ug.db.tablerows.StudentRow;
import com.technion.coolie.ug.db.tablerows.TrackRow;
import com.technion.coolie.ug.model.AcademicCalendarEvent;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Student;

/**
 * contains all functions for accessing the UG database. must close this
 * provider class after finishing using it.
 */
public class UGDBProvider {

	private UGSqlHelper UGDatabaseHelper = null;

	Context context;

	/**
	 * 
	 * @param applicationContext
	 *            - the application context;
	 *            <P>
	 *            WARNING: must close the provider instance via <b>close()</b>
	 *            method after end of use.
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

	void updateCourses(List<Course> list) {
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

	void setTrackingCourses(List<CourseKey> courses, String studentId) {
		try {
			// find all courses of studentId, for replacing them
			List<TrackRow> toDeleteCourses = getHelper().getTrackingDao()
					.queryBuilder().where().eq("studentId", studentId).query();

			// delete the list TODO delete after adding, and not before
			getHelper().getTrackingDao().delete(toDeleteCourses);

			// add courses
			for (CourseKey course : courses) {
				getHelper().getTrackingDao().create(
						new TrackRow(course, studentId));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	List<CourseKey> getTrackingCourses(String studentId) {
		List<TrackRow> list = null;
		try {
			list = getHelper().getTrackingDao().queryBuilder().where()
					.eq("studentId", studentId).query();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		List<CourseKey> $ = new ArrayList<CourseKey>();
		for (TrackRow row : list) {
			$.add(row.getCourseKey());
		}
		return $;

	}

	void setCoursesAndExams(List<CourseItem> courses, String studentId) {
		try {
			// find all courses of studentId, for replacing them
			// List<RegisteredCourseRow> toDeleteCourses = getHelper()
			// .getRegisteredCoursesDao().queryBuilder().where()
			// .eq("studentId", studentId).query();
			//
			// // delete the list TODO delete after adding, and not before
			// getHelper().getRegisteredCoursesDao().delete(toDeleteCourses);

			getHelper().getRegisteredCoursesDao().deleteBuilder().delete();

			// add courses
			for (CourseItem course : courses) {
				getHelper().getRegisteredCoursesDao().create(
						new RegisteredCourseRow(course, studentId));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	ArrayList<CourseItem> getCoursesAndExams(String studentId) {
		List<RegisteredCourseRow> list = null;
		try {
			list = getHelper().getRegisteredCoursesDao().queryBuilder().where()
					.eq("studentId", studentId).query();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		ArrayList<CourseItem> $ = new ArrayList<CourseItem>();
		for (RegisteredCourseRow row : list) {
			$.add(row.getCourse());
		}
		return $;
	}

	void setAccomplishedCourses(List<AccomplishedCourse> courses,
			String studentId) {
		try {
			// clear all courses
			getHelper().getAccopmlishedCoursesDao().deleteBuilder().delete();
			// List<AccomplishedCourseRow> list = getHelper()
			// .getAccopmlishedCoursesDao().queryBuilder().where()
			// .eq("studentId", studentId).query();
			// getHelper().getAccopmlishedCoursesDao().delete(list);
			// add courses
			for (AccomplishedCourse course : courses) {
				getHelper().getAccopmlishedCoursesDao().create(
						new AccomplishedCourseRow(course, studentId));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	List<AccomplishedCourse> getAccomplishedCourses(String studentId) {
		List<AccomplishedCourseRow> list = null;
		try {
			list = getHelper().getAccopmlishedCoursesDao().queryBuilder()
					.where().eq("studentId", studentId).query();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		List<AccomplishedCourse> $ = new ArrayList<AccomplishedCourse>();
		for (AccomplishedCourseRow row : list) {
			$.add(row.getCourse());
		}
		return $;
	}

	void setAcademicEvents(List<AcademicCalendarEvent> events) {
		try {
			// clear all events
			getHelper().getAcademicEventsDao().deleteBuilder().delete();
			// List<AcademicEventRow> list = getHelper().getAcademicEventsDao()
			// .queryForAll();
			// getHelper().getAcademicEventsDao().delete(list);
			// // add events
			for (AcademicCalendarEvent academicCalendarEvent : events) {
				getHelper().getAcademicEventsDao().create(
						new AcademicEventRow(academicCalendarEvent));
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
			throw new NullPointerException();
		}
	}

	List<AcademicCalendarEvent> getAcademicEvents() {
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

	Student getStudentInfo(String studentId) {
		Student student = new Student(studentId);
		try {
			StudentRow studentRow = getHelper().getStudentInfoDao().queryForId(
					studentId);
			if (studentRow != null)
				student = studentRow.getStudent();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return student;
	}

	void setStudentInfo(Student student, String studentId) {
		try {
			getHelper().getStudentInfoDao().createOrUpdate(
					new StudentRow(student, studentId));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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

	Dao<RegisteredCourseRow, Long> getRegisteredCoursesDao() {
		try {
			return getHelper().getRegisteredCoursesDao();
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
