package com.technion.coolie.ug.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.nodes.Document;

import android.content.Context;
import android.util.Log;

import com.technion.coolie.ug.HtmlParser;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.db.tablerows.AcademicEventRow;
import com.technion.coolie.ug.db.tablerows.AccomplishedCourseRow;
import com.technion.coolie.ug.db.tablerows.CourseRow;
import com.technion.coolie.ug.db.tablerows.TrackRow;
import com.technion.coolie.ug.gradessheet.Item;
import com.technion.coolie.ug.model.AcademicCalendarEvent;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.RegistrationGroup;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;

public class UGDatabase {

	private static UGDatabase INSTANCE;

	UGDBProvider dataProvider;

	private Student currentStudent;
	private List<RegistrationGroup> groups; // TODO delete this.
	private List<Course> allCourses;
	// private List<String> allCoursesNames;
	private Semester[] currentSemesters;
	private SemesterSeason currentSeason;
	private ArrayList<CourseItem> coursesAndExamsList;
	// private ArrayList<Item> calendarList;
	private LinkedHashMap<CourseKey, Course> coursesHash;
	Context context;

	public static UGDatabase getInstance(Context context) {
		if (INSTANCE == null)
			INSTANCE = new UGDatabase(context.getApplicationContext());
		return INSTANCE;
	}

	private UGDatabase(Context context) {
		this.context = context;
		if (this.context == null)
			throw new NullPointerException();

		// when data is empty, we need to talk to the server first! and then do
		// these(loading screen). TODO
		// initialize lists and student info, from DB TODO

		initDB();
		initCourses();
		initializeHashMap();
		initializeSemesters();
	}

	private void initDB() {
		dataProvider = new UGDBProvider(context);
	}

	// must be called when existing the app!
	public void clean() {
		dataProvider.close();
	}

	private void initCourses() {

		allCourses = dataProvider.getAllCourses();

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
	 */
	private void initializeHashMap() {
		coursesHash = new LinkedHashMap<CourseKey, Course>();
		for (final Course course : allCourses)
			coursesHash.put(course.getCourseKey(), course);

	}

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
		return allCourses;
	}

	public Semester getRelevantSemester(final SemesterSeason season) {
		return currentSemesters[season.getIdx()];
	}

	public Semester getCurrentSemester() {
		return currentSemesters[currentSeason.getIdx()];
	}

	public ArrayList<Item> getGradesSheet() {
		return HtmlParser.parseGrades("stam");
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

	public ArrayList<Item> getCalendar() {
		return HtmlParser.parseCalendar();
	}

	/**
	 * for every course in the list, adds the course to the database. if course
	 * exists, updates its content.
	 * 
	 * @param courses
	 */
	public void updateCourses(List<Course> courses) {
		try {
			for (Course course : courses) {
				dataProvider.getCoursesDao().createOrUpdate(
						new CourseRow(course));
			}
			Log.d("DEBUG", "adding courses!");

			dataProvider.getAcademicEventsDao().createOrUpdate(
					new AcademicEventRow(new AcademicCalendarEvent(Calendar
							.getInstance(), "OMG", "dd")));
			dataProvider.getTrackingDao().createOrUpdate(
					new TrackRow(courses.get(0).getCourseKey()));
			dataProvider.getAccopmlishedCoursesDao().createOrUpdate(
					new AccomplishedCourseRow(new AccomplishedCourse("3434",
							"3434", "3434", new Semester(2,
									SemesterSeason.SPRING), "3434")));

		} catch (java.sql.SQLException e) {
			throw new NullPointerException(e.toString());
		}

		// Log.d(this.getClass().getName(), "OMG ADDED COURSE TO DB");
		// CourseRow r = courseDao.queryForSameId(new CourseRow(course,
		// course
		// .getCourseKey()));
		// Log.d(this.getClass().getName(), "OMG GOT COURSE "
		// + r.getCourse().getName() + " TO DB");
	}
}
