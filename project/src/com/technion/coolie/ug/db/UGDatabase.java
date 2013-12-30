package com.technion.coolie.ug.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.nodes.Document;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.technion.coolie.server.ug.ReturnCodesUg;
import com.technion.coolie.server.ug.api.UgFactory;
import com.technion.coolie.ug.HtmlParser;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.Server.ServerCourse;
import com.technion.coolie.ug.db.tablerows.AcademicEventRow;
import com.technion.coolie.ug.db.tablerows.AccomplishedCourseRow;
import com.technion.coolie.ug.db.tablerows.TrackRow;
import com.technion.coolie.ug.model.AcademicCalendarEvent;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.RegistrationGroup;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;
import com.technion.coolie.ug.model.UGLoginObject;
import com.technion.coolie.webcourse.gr_plusplus.asyncParse;

public class UGDatabase {

	private static UGDatabase INSTANCE;

	UGDBProvider dataProvider;

	private Student currentStudent;
	private List<RegistrationGroup> groups; // TODO delete this.
	// private List<Course> allCourses;
	// private List<String> allCoursesNames;
	private Semester[] currentSemesters;
	private SemesterSeason currentSeason;
	private ArrayList<CourseItem> coursesAndExamsList;
	private List<AccomplishedCourse> gradesSheet;
	// private ArrayList<Item> calendarList;
	private LinkedHashMap<CourseKey, Course> coursesHash;
	Context appContext;
	private UGLoginObject currentLoginObject;
	private List<CourseKey> myTrackingCourses;

	public static UGDatabase getInstance(Context context) {
		if (INSTANCE == null)
			INSTANCE = new UGDatabase(context.getApplicationContext());
		return INSTANCE;
	}

	private UGDatabase(Context appContext) {
		this.appContext = appContext;
		if (this.appContext == null)
			throw new NullPointerException();

		// when data is empty, we need to talk to the server first! and then do
		// these(loading screen). TODO
		// initialize lists and student info, from DB TODO

		initDB();
		initCourses();
		initGradesSheet();
		initializeSemesters();
	}

	private void initGradesSheet() {
		gradesSheet = new ArrayList<AccomplishedCourse>();
		gradesSheet = dataProvider.getAccomplishedCourses();
	}

	private void initDB() {
		dataProvider = new UGDBProvider(appContext);
	}

	// must be called when exiting the app!
	public void clean() {
		dataProvider.close();

	}

	private void initCourses() {

		initializeHashMap(dataProvider.getAllCourses());

		// Calendar cal = Calendar.getInstance();
		// Calendar cal2 = Calendar.getInstance();
		// cal2.add(Calendar.HOUR, 1);
		//
		// groups = new ArrayList<RegistrationGroup>(Arrays.asList(
		// new RegistrationGroup(12, Arrays.asList(new Meeting("12",
		// "×™×•×¡×™ ×§×•×¤×¨×ž×Ÿ", DayOfWeek.TUESDAY, cal.getTime(), cal2
		// .getTime(), "×�×•×œ×ž×Ÿ 309")), Arrays
		// .asList(new Meeting("111", "×ž×¨. ×�×‘×™ ×›×¥",
		// DayOfWeek.SUNDAY, cal.getTime(),
		// cal2.getTime(), "×”×•×ž× ×™×¡×˜×™×� 329")), 0),
		// new RegistrationGroup(13, Arrays.asList(new Meeting("13",
		// "×¤×¨×•×¤. ×©×™ ×¢×¦×™×•× ×™", DayOfWeek.THURSDAY, Calendar
		// .getInstance().getTime(), Calendar
		// .getInstance().getTime(), "×˜×�×•×‘ 10")), Arrays
		// .asList(new Meeting("122", "×ž×¨ ×“× ×™ ×§×•×¤×¨×ž×Ÿ",
		// DayOfWeek.SUNDAY, cal.getTime(),
		// cal2.getTime(), "×¤×™×©×‘×š 303"),
		//
		// new Meeting("123", "×ž×¨ ×ž×©×” ×¨×•×–× ×‘×œ×•×�",
		// DayOfWeek.WEDNESDAY, cal.getTime(),
		// cal2.getTime(), "×˜×�×•×‘ 2")), 25)));
		//
		// allCourses = new ArrayList<Course>(
		//
		// Arrays.asList(
		//
		// new Course(
		// "233245",
		// "×ž×‘×•×� ×œ×‘×™× ×” ×ž×œ×�×›×•×ª×™×ª",
		// 2.0f,
		// "During the class we will talk about the high level design and your personal roles. We will also discuss your project topic (with each team). Teams that we already approved will use the time to start the design process",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// null),
		//
		// new Course("074957", "×ª×•×¨×ª ×”×’×¨×¤×™×�", 5.0f, "",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course("043932", "×ž×‘× ×™ × ×ª×•× ×™×�", 2.0f, "",
		// new Semester(2013, SemesterSeason.SPRING),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course(
		// "232932",
		// "×œ×•×’×™×§×” ×•×ª×•×¨×ª ×”×§×‘×•×¦×•×ª",
		// 5.0f,
		// "Brain-Machine interfaces will fundamentally change the way humans interact with the world in the 21st century. By creating a direct channel of communication between the mind and devices external to it, this class of technology provides individuals with the ability to bypass their body entirely, and control their environment using thought alone.",
		// new Semester(2013, SemesterSeason.SPRING),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course("232932", "×ž×¤×¨×˜×™×� ×¤×•×¨×ž×œ×™×™×� ×‘×ž×¢×¨×›×•×ª ×ž×•×¨×›×‘×•×ª",
		// 5.0f, "", new Semester(2013,
		// SemesterSeason.WINTER), Faculty.CS,
		// new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course("012985", "×¤×™×¡×™×§×” 2×ž×ž", 5.0f, "",
		// new Semester(2013, SemesterSeason.SPRING),
		// Faculty.PHYS,
		// new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course(
		// "045932",
		// "×©×¤×•×ª ×ª×›× ×•×ª",
		// 2.5f,
		// "Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course(
		// "011236",
		// "×�×œ×’×‘×¨×” 2×ž×ž",
		// 5.0f,
		// "Dont even ask how hard this course is. You should be prepared for work hard.",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.MATH,
		// new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course(
		// "123932",
		// "×ž×‘×•×� ×œ×”× ×“×¡×ª ×ª×•×›× ×”",
		// 5.0f,
		// "Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.ARCHITECTURE, new GregorianCalendar(
		// 2014, 2, 11), new GregorianCalendar(
		// 2014, 2, 11), null, null, groups),
		//
		// new Course(
		// "023422",
		// "×ž×‘×•×� ×œ×¢×™×¦×•×‘",
		// 5.0f,
		// "Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.ARCHITECTURE, new GregorianCalendar(
		// 2014, 2, 11), new GregorianCalendar(
		// 2014, 2, 11), null, null, groups),
		//
		// new Course(
		// "243411",
		// "×ª×›× ×•×Ÿ ×ž×¢×¨×›×ª×™ 2",
		// 2.0f,
		// "Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.ARCHITECTURE, new GregorianCalendar(
		// 2013, 2, 11), new GregorianCalendar(
		// 2014, 3, 11), null, null, groups),
		//
		// new Course(
		// "025629",
		// "×ž×¢×¨×›×•×ª ×”×¤×¢×œ×”",
		// 4.5f,
		// "Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 5, 11), null, null,
		// groups),
		//
		// new Course(
		// "012342",
		// "×—×§×¨ ×”×—×œ×œ",
		// 5.0f,
		// "Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.AE, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups)
		//
		// ));

	}

	/**
	 * puts all the courses in a hashTable, mapping courseKey to course
	 * 
	 * @param courses
	 */
	private void initializeHashMap(List<Course> courses) {
		coursesHash = new LinkedHashMap<CourseKey, Course>();
		for (final Course course : courses)
			coursesHash.put(course.getCourseKey(), course);

	}

	// TODO
	private void initializeSemesters() {

		// currentSeason = findCurrentSemesters(currentSemesters);

		currentSeason = SemesterSeason.WINTER;
		currentSemesters = new Semester[3];
		currentSemesters[SemesterSeason.SPRING.getIdx()] = new Semester(2013,
				SemesterSeason.SPRING);
		currentSemesters[SemesterSeason.SUMMER.getIdx()] = new Semester(2013,
				SemesterSeason.SUMMER);
		currentSemesters[SemesterSeason.WINTER.getIdx()] = new Semester(2013,
				SemesterSeason.WINTER);

		currentStudent = null;
		// coursesHash = new LinkedHashMap<CourseKey, Course>();
		// for (final Course course : allCourses)
		// coursesHash.put(course.getCourseKey(), course);

	}

	public Course getCourseByKey(final CourseKey key) {
		return coursesHash.get(key);
	}

	public List<Course> getCourses() {
		return new ArrayList<Course>(coursesHash.values());
	}

	public Semester getRelevantSemester(final SemesterSeason season) {
		return currentSemesters[season.getIdx()];
	}

	public Semester getCurrentSemester() {
		return currentSemesters[currentSeason.getIdx()];
	}

	public List<AccomplishedCourse> getGradesSheet() {
		// getGradesSheetfromServer();
		return HtmlParser.parseGrades("stam");

		// return
		// UgFactory.getUgGradeSheet().getMyGradesSheet(currentLoginObject);
	}

	// SERVER PART
	public void getGradesSheetfromServer() {

		asyncParse<AccomplishedCourse> a = new myGradeParse();
		a.execute();
	}

	class myGradeParse extends asyncParse<AccomplishedCourse> {
		List<AccomplishedCourse> l;

		@Override
		protected List<AccomplishedCourse> doInBackground(String... params) {

			l = UgFactory.getUgGradeSheet().getMyGradesSheet(
					getCurrentLoginObject());
			return super.doInBackground(params);
		}

		@Override
		protected void onPostExecute(List<AccomplishedCourse> result) {
			if (l == null)
				Log.d("GRADES SHEET   ×›×›×’", "NULL");
			else
				Log.d("GRADES SHEET  ×’×›×’×› ", l.size() + "");

			// new myGradeParse().execute();
		}

	}

	public void getAllCoursesFromServer() {

		asyncParse<Course> a = new asyncParse<Course>() {
			List<ServerCourse> l;

			@Override
			protected List<Course> doInBackground(String... params) {

				List<ServerCourse> l = UgFactory.getUgCourse().getAllCourses(
						getCurrentSemester());
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(List<Course> result) {
				Log.d("all courses", l.size() + "");
			}

		};
		a.execute();
	}

	public void getCalendarEventsFromServer() {

		asyncParse<AcademicCalendarEvent> a = new asyncParse<AcademicCalendarEvent>() {
			List<AcademicCalendarEvent> l;

			@Override
			protected List<AcademicCalendarEvent> doInBackground(String... params) {

				// List<SectionedListItem> l =
				// UgFactory.getUgEvent().getAllAcademicEvents();
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(List<AcademicCalendarEvent> result) {
				Log.d("all courses", l.size() + "");
			}

		};
		a.execute();
	}
	
	
	public void addTrackingCourseToServer(UGLoginObject o,CourseKey ck) 
	{
		AsyncTask<CourseKey, Void , ReturnCodesUg> asyncTask = new AsyncTask<CourseKey, Void , ReturnCodesUg>()
		{
			@Override
			protected ReturnCodesUg doInBackground(CourseKey... params) 
			{
				if (params==null || params[0]==null) return null;
				ReturnCodesUg returnCode = UgFactory.getUgTracking().addTrackingStudent(getCurrentLoginObject(), params[0]);
				return returnCode;
			}
			
			@Override
			protected void onPostExecute(ReturnCodesUg returnCode) 
			{
				if (returnCode==null)
					{
						Log.v("addTrackingCourseToServer", "returnCode is null");
						return;
					}
				
				Log.v("addTrackingCourseToServer", returnCode.toString());
				if (returnCode!=ReturnCodesUg.SUCCESS)
				{
				}
			}
		};
		asyncTask.execute(ck);
	}
	
	
	public void deleteTrackingCourseFromServer(UGLoginObject o,CourseKey ck) 
	{
		AsyncTask<CourseKey, Void , ReturnCodesUg> asyncTask = new AsyncTask<CourseKey, Void , ReturnCodesUg>()
		{
			@Override
			protected ReturnCodesUg doInBackground(CourseKey... params) 
			{
				if (params==null || params[0]==null) return null;
				ReturnCodesUg returnCode = UgFactory.getUgTracking().removeTrackingStudentFromCourse(getCurrentLoginObject(), params[0]);
				return returnCode;
			}
			
			@Override
			protected void onPostExecute(ReturnCodesUg returnCode) 
			{
				if (returnCode==null)
				{
					Log.v("deleteTrackingCourseFromServer", "returnCode is null");
					return;
				}
			
			Log.v("deleteTrackingCourseFromServer", returnCode.toString());
				if (returnCode!=ReturnCodesUg.SUCCESS)
				{
					//cant remove this course on (server problem)
				}
			}
		};
		asyncTask.execute(ck);
	}
	
	public ArrayList<CourseItem> getStudentCourses(
			final SemesterSeason semesterseason) {
		Document doc = null;
		switch (semesterseason) {
		case WINTER:
			doc = HtmlParser.parseFromFille("ug_current_semester_courses.html",
					MainActivity.context);
			break;
		case SPRING:
			doc = HtmlParser.parseFromFille(
					"ug_previous_semester_courses.html", MainActivity.context);
			break;
		case SUMMER:
			doc = HtmlParser.parseFromFille(
					"ug_previous_semester_courses.html", MainActivity.context);
			break;
		}
		if (doc != null)
			coursesAndExamsList = HtmlParser.parseCoursesAndExamsDoc(doc);

		return coursesAndExamsList;
	}

	public List<AcademicCalendarEvent> getCalendar() {
		return HtmlParser.parseCalendar();
	}

	/**
	 * adds all the courses to the database and then to the Courses hash. if
	 * course exists, we update its content.
	 * 
	 */
	public void updateCourses(List<Course> courses) {
		try {

			Log.d("DEBUG", "updating courses!");

			// update the DB
			dataProvider.updateCourses(courses);
			// update the hash
			for (Course course : courses) {
				coursesHash.put(course.getCourseKey(), course);
			}

			dataProvider.getAcademicEventsDao().createOrUpdate(
					new AcademicEventRow(new AcademicCalendarEvent(Calendar
							.getInstance(), "OMG", "dd", null)));
			dataProvider.getAcademicEvents();
			dataProvider.getTrackingDao().createOrUpdate(
					new TrackRow(courses.get(0).getCourseKey()));
			dataProvider.getTrackingCourses();
			dataProvider.getAccopmlishedCoursesDao().createOrUpdate(
					new AccomplishedCourseRow(new AccomplishedCourse("3434",
							"3434", "3434", "201301", "3434", null, false)));
			dataProvider.getAccomplishedCourses();

		} catch (java.sql.SQLException e) {
			throw new NullPointerException(e.toString());
		}

	}

	public void setGradesSheet(List<AccomplishedCourse> courses) {
		Log.d("DEBUG", "setting grades!");
		// update the DB
		dataProvider.setAccomplishedCourses(courses);
		// update the list
		gradesSheet = courses;
	}

	public UGLoginObject getCurrentLoginObject() {
		if (currentLoginObject == null) {
			currentLoginObject = new UGLoginObject("1636", "11111100");
		}
		return currentLoginObject;
	}
	
	// replace this code with code that reads the data from local database 
		public List<CourseKey> getMyTrackingCourses()
		{
			if (myTrackingCourses!=null)
				return myTrackingCourses;
			else 
			{
				myTrackingCourses = new ArrayList<CourseKey>();
				
				//replace this code with reading tracking courses from from DB
				int maximumTracking = 5;
				for (int i=0; i< getCourses().size(); i++)
				{
					if (myTrackingCourses.size() >= maximumTracking) break;
					if (i % 2 ==0)
					myTrackingCourses.add(getCourses().get(i).getCourseKey());
				}
			}
			return myTrackingCourses;
		}
}
