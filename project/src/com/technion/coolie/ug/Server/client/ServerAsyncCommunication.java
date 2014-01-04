package com.technion.coolie.ug.Server.client;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.technion.coolie.server.ug.ReturnCodesUg;
import com.technion.coolie.server.ug.api.UgFactory;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.Server.ServerCourse;
import com.technion.coolie.ug.calendar.AcademicCalendarListFragment;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.gradessheet.GradesSheetListFragment;
import com.technion.coolie.ug.model.AcademicCalendarEvent;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.UGLoginObject;
import com.technion.coolie.ug.utils.UGAsync;

/**
 * 
 * wrapper functions of the (UGFactory)server functions. all a-sync tasks should
 * be defined here.
 * 
 */
public class ServerAsyncCommunication {

	// static Context appContext;
	static public MainActivity mainActivity;

	/**
	 * sets the semester array with the current arrays
	 * 
	 * @param semesters
	 * @return
	 */
	public static SemesterSeason findCurrentSemesters(Semester[] semesters) {

		return SemesterSeason.WINTER;
	}

	// SERVER PART
	static public void getGradesSheetfromServer() {

		UGAsync<AccomplishedCourse> a = new UGAsync<AccomplishedCourse>() {
			List<AccomplishedCourse> l;

			@Override
			protected List<AccomplishedCourse> doInBackground(String... params) {
				// should do this on the ui thread.
				UGDatabase db = UGDatabase.getInstance(mainActivity);
				l = UgFactory.getUgGradeSheet().getMyGradesSheet(
						db.getCurrentLoginObject());
				db.setGradesSheet(l);
				// return super.doInBackground(params);
				return l;
			}

			@Override
			protected void onPostExecute(List<AccomplishedCourse> result) {
				if (l == null || l.size() == 0)
					return;
				// UGDatabase.getInstance(mainActivity).setGradesSheet(l);
				GradesSheetListFragment f = mainActivity
						.getGradesSheetFragment();
				// f = new AcademicCalendarListFragment();
				f.updateData();
				// f.getAdapter().notifyDataSetChanged();

			}
		};
		a.execute();
	}

	public static void getAllCoursesFromServer() {

		UGAsync<Course> a = new UGAsync<Course>() {
			List<ServerCourse> l;

			@Override
			protected List<Course> doInBackground(String... params) {

				Semester s = new Semester(2013, SemesterSeason.WINTER);
				List<ServerCourse> l = UgFactory.getUgCourse().getAllCourses(s);
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(List<Course> result) {
				Log.d("all courses", l.size() + "");
			}

		};
		a.execute();
	}

	static public void getCalendarEventsFromServer() {

		UGAsync<AcademicCalendarEvent> a = new UGAsync<AcademicCalendarEvent>() {

			List<AcademicCalendarEvent> l;

			@Override
			protected List<AcademicCalendarEvent> doInBackground(
					String... params) {

				l = UgFactory.getUgEvent().getAllAcademicEvents();
				UGDatabase.getInstance(mainActivity).setAcademicCalendar(l);
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(List<AcademicCalendarEvent> result) {
				if (l == null || l.size() == 0)
					return;
				UGDatabase.getInstance(mainActivity).setAcademicCalendar(l);
				AcademicCalendarListFragment f = mainActivity
						.getCalendarFragment();
				if (f == null)
					return;
				f.updateData();
			}

		};
		a.execute();
	}

	public void addTrackingCourseToServer(UGLoginObject o, CourseKey ck) {
		AsyncTask<CourseKey, Void, ReturnCodesUg> asyncTask = new AsyncTask<CourseKey, Void, ReturnCodesUg>() {
			@Override
			protected ReturnCodesUg doInBackground(CourseKey... params) {
				if (params == null || params[0] == null)
					return null;
				ReturnCodesUg returnCode = UgFactory.getUgTracking()
						.addTrackingStudent(
								UGDatabase.getInstance(mainActivity)
										.getCurrentLoginObject(), params[0]);
				return returnCode;
			}

			@Override
			protected void onPostExecute(ReturnCodesUg returnCode) {
				if (returnCode == null) {
					Log.v("addTrackingCourseToServer", "returnCode is null");
					return;
				}

				Log.v("addTrackingCourseToServer", returnCode.toString());
				if (returnCode != ReturnCodesUg.SUCCESS) {
				}
			}
		};
		asyncTask.execute(ck);
	}

	public void deleteTrackingCourseFromServer(UGLoginObject o, CourseKey ck) {
		AsyncTask<CourseKey, Void, ReturnCodesUg> asyncTask = new AsyncTask<CourseKey, Void, ReturnCodesUg>() {
			@Override
			protected ReturnCodesUg doInBackground(CourseKey... params) {
				if (params == null || params[0] == null)
					return null;
				ReturnCodesUg returnCode = UgFactory.getUgTracking()
						.removeTrackingStudentFromCourse(
								UGDatabase.getInstance(mainActivity)
										.getCurrentLoginObject(), params[0]);
				return returnCode;
			}

			@Override
			protected void onPostExecute(ReturnCodesUg returnCode) {
				if (returnCode == null) {
					Log.v("deleteTrackingCourseFromServer",
							"returnCode is null");
					return;
				}

				Log.v("deleteTrackingCourseFromServer", returnCode.toString());
				if (returnCode != ReturnCodesUg.SUCCESS) {
					// cant remove this course on (server problem)
				}
			}
		};
		asyncTask.execute(ck);
	}
	// getAllInfo

	// getcourses

	// getTrackingCourses

	// getAcademic

}
