package com.technion.coolie.ug.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.nodes.Document;

import android.content.Context;
import android.util.Log;

import com.technion.coolie.server.ug.api.UgFactory;
import com.technion.coolie.ug.HtmlParser;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.Server.ServerCourse;
import com.technion.coolie.ug.db.tablerows.AcademicEventRow;
import com.technion.coolie.ug.db.tablerows.AccomplishedCourseRow;
import com.technion.coolie.ug.db.tablerows.TrackRow;
import com.technion.coolie.ug.gradessheet.SectionedListItem;
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

	// this class has one studentId per instance.
	String studentId;

	private static String DEBUG_TAG = "coolie.src.com.technion.coolie.ug.databaseDebug";

	private Student currentStudent;
	private List<RegistrationGroup> groups; // TODO delete this.
	// private List<Course> allCourses;
	// private List<String> allCoursesNames;
	private Semester[] currentSemesters;
	private SemesterSeason currentSeason;

	private ArrayList<CourseItem> coursesAndExamsList;
	private List<AccomplishedCourse> gradesSheet;
	private List<CourseKey> trackingCourses;
	private List<AcademicCalendarEvent> calendarEvents;
	private LinkedHashMap<CourseKey, Course> coursesHash;
	Context appContext;
	private UGLoginObject currentLoginObject;

	public static UGDatabase getInstance(Context context) {
		if (INSTANCE == null)
			INSTANCE = new UGDatabase(context.getApplicationContext());
		return INSTANCE;
	}

	private UGDatabase(Context appContext) {
		this.appContext = appContext;
		if (this.appContext == null)
			throw new NullPointerException();

		// when data is empty, we need to talk to the server first and wait for
		// it(loading screen). TODO

		// initialize lists and student info, from DB TODO

		// GET THE CURRENT STUDENT ID FROM UG LOGIN and use it with the
		// provider! TODO

		initStudentId();
		initDB();
		initCourses();
		initGradesSheet();
		initTrackingCourses();
		initAcademicCalendar();
		initializeSemesters();
	}

	private void initStudentId() {
		studentId = getStudentId();

	}

	private String getStudentId() {
		return "11";
	}

	private void initAcademicCalendar() {
		calendarEvents = dataProvider.getAcademicEvents();
	}

	private void initTrackingCourses() {
		trackingCourses = dataProvider.getTrackingCourses(studentId);
	}

	private void initGradesSheet() {
		gradesSheet = dataProvider.getAccomplishedCourses(studentId);
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
		// "יוסי קופרמן", DayOfWeek.TUESDAY, cal.getTime(), cal2
		// .getTime(), "אולמן 309")), Arrays
		// .asList(new Meeting("111", "מר. אבי כץ",
		// DayOfWeek.SUNDAY, cal.getTime(),
		// cal2.getTime(), "הומניסטים 329")), 0),
		// new RegistrationGroup(13, Arrays.asList(new Meeting("13",
		// "פרופ. שי עציוני", DayOfWeek.THURSDAY, Calendar
		// .getInstance().getTime(), Calendar
		// .getInstance().getTime(), "טאוב 10")), Arrays
		// .asList(new Meeting("122", "מר דני קופרמן",
		// DayOfWeek.SUNDAY, cal.getTime(),
		// cal2.getTime(), "פישבך 303"),
		//
		// new Meeting("123", "מר משה רוזנבלום",
		// DayOfWeek.WEDNESDAY, cal.getTime(),
		// cal2.getTime(), "טאוב 2")), 25)));
		//
		// allCourses = new ArrayList<Course>(
		//
		// Arrays.asList(
		//
		// new Course(
		// "233245",
		// "מבוא לבינה מלאכותית",
		// 2.0f,
		// "During the class we will talk about the high level design and your personal roles. We will also discuss your project topic (with each team). Teams that we already approved will use the time to start the design process",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// null),
		//
		// new Course("074957", "תורת הגרפים", 5.0f, "",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course("043932", "מבני נתונים", 2.0f, "",
		// new Semester(2013, SemesterSeason.SPRING),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course(
		// "232932",
		// "לוגיקה ותורת הקבוצות",
		// 5.0f,
		// "Brain-Machine interfaces will fundamentally change the way humans interact with the world in the 21st century. By creating a direct channel of communication between the mind and devices external to it, this class of technology provides individuals with the ability to bypass their body entirely, and control their environment using thought alone.",
		// new Semester(2013, SemesterSeason.SPRING),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course("232932", "מפרטים פורמליים במערכות מורכבות",
		// 5.0f, "", new Semester(2013,
		// SemesterSeason.WINTER), Faculty.CS,
		// new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course("012985", "פיסיקה 2ממ", 5.0f, "",
		// new Semester(2013, SemesterSeason.SPRING),
		// Faculty.PHYS,
		// new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course(
		// "045932",
		// "שפות תכנות",
		// 2.5f,
		// "Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 2, 11), null, null,
		// groups),
		//
		// new Course(
		// "011236",
		// "אלגברה 2ממ",
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
		// "מבוא להנדסת תוכנה",
		// 5.0f,
		// "Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.ARCHITECTURE, new GregorianCalendar(
		// 2014, 2, 11), new GregorianCalendar(
		// 2014, 2, 11), null, null, groups),
		//
		// new Course(
		// "023422",
		// "מבוא לעיצוב",
		// 5.0f,
		// "Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.ARCHITECTURE, new GregorianCalendar(
		// 2014, 2, 11), new GregorianCalendar(
		// 2014, 2, 11), null, null, groups),
		//
		// new Course(
		// "243411",
		// "תכנון מערכתי 2",
		// 2.0f,
		// "Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.ARCHITECTURE, new GregorianCalendar(
		// 2013, 2, 11), new GregorianCalendar(
		// 2014, 3, 11), null, null, groups),
		//
		// new Course(
		// "025629",
		// "מערכות הפעלה",
		// 4.5f,
		// "Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
		// new Semester(2013, SemesterSeason.WINTER),
		// Faculty.CS, new GregorianCalendar(2014, 2, 11),
		// new GregorianCalendar(2014, 5, 11), null, null,
		// groups),
		//
		// new Course(
		// "012342",
		// "חקר החלל",
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

	public List<SectionedListItem> getGradesSheet() {
		// getGradesSheetfromServer();
		return HtmlParser.parseGrades("stam");

		// return
		// UgFactory.getUgGradeSheet().getMyGradesSheet(currentLoginObject);
	}

	public void getGradesSheetfromServer() {

		asyncParse<SectionedListItem> a = new myGradeParse();
		a.execute();
	}

	class myGradeParse extends asyncParse<SectionedListItem> {
		List<SectionedListItem> l;

		@Override
		protected List<SectionedListItem> doInBackground(String... params) {

			l = UgFactory.getUgGradeSheet().getMyGradesSheet(
					getCurrentLoginObject());
			return super.doInBackground(params);
		}

		@Override
		protected void onPostExecute(List<SectionedListItem> result) {
			if (l == null)
				Log.d("GRADES SHEET   ככג", "NULL");
			else
				Log.d("GRADES SHEET  גכגכ ", l.size() + "");

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

		asyncParse<SectionedListItem> a = new asyncParse<SectionedListItem>() {
			List<SectionedListItem> l;

			@Override
			protected List<SectionedListItem> doInBackground(String... params) {

				// List<SectionedListItem> l =
				// UgFactory.getUgEvent().getAllAcademicEvents();
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(List<SectionedListItem> result) {
				Log.d("all courses", l.size() + "");
			}

		};
		a.execute();
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

	public ArrayList<SectionedListItem> getCalendar() {
		return HtmlParser.parseCalendar();
	}

	/**
	 * adds all the courses to the database and then to the Courses hash. if
	 * course exists, we update its content.
	 * 
	 */
	public void updateCourses(List<Course> courses) {
		try {

			Log.d(DEBUG_TAG, "updating courses!");

			// update the DB
			dataProvider.updateCourses(courses);
			// update the hash
			for (Course course : courses) {
				coursesHash.put(course.getCourseKey(), course);
			}

			dataProvider.getAcademicEventsDao().createOrUpdate(
					new AcademicEventRow(new AcademicCalendarEvent(Calendar
							.getInstance(), "OMG", "dd")));
			Log.d(DEBUG_TAG, dataProvider.getAcademicEvents().size() + "");
			dataProvider.getTrackingDao().createOrUpdate(
					new TrackRow(courses.get(0).getCourseKey(), studentId));

			Log.d(DEBUG_TAG, dataProvider.getTrackingCourses(studentId).size()
					+ "");
			dataProvider.getAccopmlishedCoursesDao()
					.createOrUpdate(
							new AccomplishedCourseRow(new AccomplishedCourse(
									"3434", "3434", "3434", new Semester(2,
											SemesterSeason.SPRING), "3434"),
									studentId));
			Log.d(DEBUG_TAG, dataProvider.getAccomplishedCourses(studentId)
					.size() + "");

		} catch (java.sql.SQLException e) {
			throw new NullPointerException(e.toString());
		}

	}

	public void setGradesSheet(List<AccomplishedCourse> courses) {
		Log.d(DEBUG_TAG, "setting grades!");
		// update the DB
		dataProvider.setAccomplishedCourses(courses, studentId);
		// update the list
		gradesSheet = courses;
	}

	public void setTrackingCourses(List<CourseKey> toTrack) {
		Log.d(DEBUG_TAG, "set tracking Courses!");
		dataProvider.setTrackingCourses(toTrack, studentId);
		trackingCourses = toTrack;
	}

	public void setAcademicCalendar(List<AcademicCalendarEvent> calendarEvents) {
		Log.d(DEBUG_TAG, "set academic events");
		dataProvider.setAcademicEvents(calendarEvents);
		this.calendarEvents = calendarEvents;
	}

	public UGLoginObject getCurrentLoginObject() {
		if (currentLoginObject == null) {
			currentLoginObject = new UGLoginObject("1636", "11111100");
		}
		return currentLoginObject;
	}
}
