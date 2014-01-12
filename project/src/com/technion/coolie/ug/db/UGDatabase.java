package com.technion.coolie.ug.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.nodes.Document;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.technion.coolie.ug.HtmlParser;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.model.AcademicCalendarEvent;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;
import com.technion.coolie.ug.model.UGLoginObject;
import com.technion.coolie.ug.utils.UGAsync;

public class UGDatabase {

	// make loading courses async TODO

	private static UGDatabase INSTANCE;

	UGDBProvider dataProvider;

	// this class has one studentId per instance.
	final String studentId;

	private Student currentStudent;
	private Semester[] currentSemesters;
	
	

	private SemesterSeason currentSeason;

	private List<CourseItem> coursesAndExamsList;
	private volatile boolean hasCoursesAndExamsList = false;

	private List<AccomplishedCourse> gradesSheet;
	private volatile boolean hasGradeSheet = false;

	private List<CourseKey> trackingCourses;
	//
	private List<AcademicCalendarEvent> calendarEvents;
	private volatile boolean hasCalendarEvents = false;

	private LinkedHashMap<CourseKey, Course> coursesHash;
	private volatile boolean hasCourses = false;

	Handler handler;

	Context appContext;
	private UGLoginObject currentLoginObject;

	/**
	 * assumes a student is logged in to the application, and we can retrieve
	 * his id.
	 */
	public synchronized static UGDatabase getInstance(Context context) {
		if (INSTANCE == null) {
			log("[Creating UG database for the first time]]");
			INSTANCE = new UGDatabase(context.getApplicationContext());

		} else if (changedStudent()) {
			log("[Creating UG database because we changed students]");
			INSTANCE = new UGDatabase(context.getApplicationContext());

		}
		return INSTANCE;
	}

	/**
	 * returns true if the student that is logged in to this app is different
	 * from the current known student.
	 * 
	 * @return
	 */
	private static boolean changedStudent() {
		return !(INSTANCE.getStudentId().equals(INSTANCE.studentId));
	}

	// private UGDatabase() {
	// this.studentId = "";
	// }

	private UGDatabase(Context appContext) {
		this.appContext = appContext;
		this.studentId = getStudentId();
		if (this.appContext == null || studentId == null)
			throw new NullPointerException();

		// when entering the app for the first time. we need to talk to the
		// server first and wait for it(loading screen).
		// This is not done in this class, but rather in the main activity. TODO

		handler = new Handler();
		initDB();

		log("[finished Creating UG database]");

	}

	private void initDB() {
		dataProvider = new UGDBProvider(appContext);

		initStudent();
		initCourses();
		initGradesSheet();
		initTrackingCourses();
		initAcademicCalendar();
		initRegisteredCourses();
		initializeSemesters();
	}

	private void initAcademicCalendar() {
		calendarEvents = dataProvider.getAcademicEvents();
		// ServerAsyncCommunication.getCalendarEventsFromServer();
	}

	private void initTrackingCourses() {
		trackingCourses = dataProvider.getTrackingCourses(studentId);
	}

	private void initGradesSheet() {
		gradesSheet = dataProvider.getAccomplishedCourses(studentId);
		// gradesSheet = new ArrayList<AccomplishedCourse>();
		// gradesSheet.add(new
		// AccomplishedCourse("234111","OS","4","201301","95","80",false));
		// ServerAsyncCommunication.getGradesSheetfromServer();
	}

	private void initRegisteredCourses() {
		coursesAndExamsList = dataProvider.getCoursesAndExams(studentId);
	}

	private void initStudent() {
		currentStudent = dataProvider.getStudentInfo(studentId);
	}

	// must be called when exiting the app!
	public void clean() {
		log("cleaning UGDatabase");
		dataProvider.close();

	}

	private void initCourses() {
		coursesHash = new LinkedHashMap<CourseKey, Course>();
		doAsync(new Runnable() {

			@Override
			public void run() {
				updateHashMap(dataProvider.getAllCourses());

			}
		});

		// getAllCoursesFromServer();
	}

	/**
	 * puts all the courses in a hashTable, mapping courseKey to course
	 * 
	 * @param courses
	 */
	private synchronized void updateHashMap(List<Course> courses) {

		for (final Course course : courses)
			coursesHash.put(course.getCourseKey(), course);

	}

	// TODO
	private void initializeSemesters() {

		currentSeason = SemesterSeason.WINTER;
		currentSemesters = new Semester[3];
		currentSemesters[SemesterSeason.SPRING.getIdx()] = new Semester(2013,
				SemesterSeason.SPRING);
		currentSemesters[SemesterSeason.SUMMER.getIdx()] = new Semester(2013,
				SemesterSeason.SUMMER);
		currentSemesters[SemesterSeason.WINTER.getIdx()] = new Semester(2013,
				SemesterSeason.WINTER);

	}

	public String getCurrentStudentId() {
		return studentId;
	}

	public Course getCourseByKey(final CourseKey key) {
		return coursesHash.get(key);
	}

	public Semester getRelevantSemester(final SemesterSeason season) {
		return currentSemesters[season.getIdx()];
	}

	public Semester getCurrentSemester() {
		return currentSemesters[currentSeason.getIdx()];
	}

	public List<CourseKey> getTrackingCourses() {
		if (trackingCourses == null)
			trackingCourses = dataProvider.getTrackingCourses(studentId);
		log("getting " + trackingCourses.size() + " tracking Courses");
		return trackingCourses;

	}
	
	public Semester[] getCurrentSemesters() {
		return currentSemesters;
	}

	
	public List<CourseItem> getCoursesAndExams() {
		Log.i("1", "coursesAndExamsList :" + coursesAndExamsList.size());
		if (coursesAndExamsList == null)
			coursesAndExamsList = dataProvider.getCoursesAndExams(studentId);
		log("getting " + coursesAndExamsList.size() + " registered Courses");
		return coursesAndExamsList;
	}

	public Student getStudentInfo() {
		return currentStudent;
	}

	public List<Course> getCourses() {
		List<Course> courses = new ArrayList<Course>(coursesHash.values());
		log("getting " + courses.size() + " courses!");
		return courses;
	}

	public List<AccomplishedCourse> getGradesSheet() {
		if (gradesSheet == null)
			gradesSheet = dataProvider.getAccomplishedCourses(studentId);
		log("getting " + gradesSheet.size() + " accomplished Courses");
		return gradesSheet;

	}

	public List<AcademicCalendarEvent> getCalendar() {
		if (calendarEvents == null)
			calendarEvents = dataProvider.getAcademicEvents();
		log("getting " + calendarEvents.size() + " calendar events");
		return calendarEvents;
	}

	/**
	 * use getCoursesAndExams
	 * 
	 * @param semesterseason
	 * @return
	 */
	@Deprecated
	public List<CourseItem> getStudentCourses(
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

	/**
	 * adds all the courses to the database and then to the Courses hash. if
	 * course exists, we update its content.
	 * 
	 */
	public void updateCourses(final List<Course> courses) {
		checkListParam(courses);
		log("updating " + courses.size() + " courses!");

		// update the DB
		doAsync(new Runnable() {
			@Override
			public void run() {
				UGDBProvider provider = new UGDBProvider(appContext);
				provider.updateCourses(courses);
				log("[DB]updated " + courses.size() + " courses in db.");
				provider.close();
			}
		});
		updateHashMap(courses);

	}

	public void setGradesSheet(final List<AccomplishedCourse> courses) {
		checkListParam(courses);
		log("setting " + courses.size() + " grades!");
		// update the DB
		doAsync(new Runnable() {
			public void run() {
				UGDBProvider provider = new UGDBProvider(appContext);
				provider.setAccomplishedCourses(courses, studentId);
				log("[DB]finished setting " + courses.size() + " grades!");
				provider.close();
			}
		});
		// update the list
		gradesSheet = courses;
	}

	public void setTrackingCourses(List<CourseKey> toTrack) {
		if (toTrack == null)
			throw new NullPointerException();
		log("setting " + toTrack.size() + " tracking Courses!");
		dataProvider.setTrackingCourses(toTrack, studentId);
		trackingCourses = toTrack;
	}

	public void setAcademicCalendar(
			final List<AcademicCalendarEvent> calendarEvents) {
		checkListParam(calendarEvents);
		log("setting " + calendarEvents.size() + " academic events");
		doAsync(new Runnable() {
			@Override
			public void run() {
				UGDBProvider provider = new UGDBProvider(appContext);
				provider.setAcademicEvents(calendarEvents);
				log("[DB]finished setting " + calendarEvents.size()
						+ " academic events");
				provider.close();
			}
		});

		this.calendarEvents = calendarEvents;
	}

	public void setCoursesAndExams(List<CourseItem> courses) {
		if (courses == null)
			throw new NullPointerException();
		log("setting " + courses.size() + " registered Courses and exams!");
		dataProvider.setCoursesAndExams(courses, studentId);
		coursesAndExamsList = courses;
	}
	
	public void setStudentInfo(Student student) {
		if (student == null)
			throw new NullPointerException();
		if (!student.getId().equals(studentId))
			throw new IllegalArgumentException(
					"setting different student than the current");
		log("setting student! of id " + student.getId());
		dataProvider.setStudentInfo(student, studentId);
		currentStudent = student;
	}

	private String getStudentId() {
		// GET THE CURRENT STUDENT ID FROM UG LOGIN and use it with the
		// provider! TODO
		return "22";
	}
	
	public void setCurrentSemesters(Semester[] currentSemesters) {
		this.currentSemesters = currentSemesters;
	}

	public UGLoginObject getCurrentLoginObject() {
		if (currentLoginObject == null) {
			currentLoginObject = new UGLoginObject("1636", "11111100");
		}
		return currentLoginObject;
	}

	private static String DEBUG_TAG = "coolie.src.com.technion.coolie.ug.databaseDebug";
	private static String DEBUG_STRING_TITLE = "[UG_DATABASE]";

	private static void log(String msg) {

		Log.d(DEBUG_TAG, DEBUG_STRING_TITLE + msg);
	}

	private void checkListParam(List list) {
		if (list == null)
			throw new IllegalArgumentException(
					"null list is overriding database");
		if (list.isEmpty())
			throw new IllegalArgumentException(
					"empty list is overriding database");
	}

	private void doAsync(final Runnable r) {
		new UGAsync<Object>() {
			@Override
			protected List<Object> doInBackground(String... params) {
				r.run();
				return super.doInBackground(params);
			}
		}.execute("");
	}

}
