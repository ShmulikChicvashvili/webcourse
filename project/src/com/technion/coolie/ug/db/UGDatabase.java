package com.technion.coolie.ug.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.nodes.Document;

import com.technion.coolie.ug.HtmlParser;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.DayOfWeek;
import com.technion.coolie.ug.Enums.Faculty;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.gradessheet.Item;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Meeting;
import com.technion.coolie.ug.model.RegistrationGroup;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;

public enum UGDatabase {
	INSTANCE;

	private Student currentStudent;
	private List<RegistrationGroup> groups; // TODO delete this.
	private List<Course> allCourses;
	// private List<String> allCoursesNames;
	private Semester[] currentSemesters;
	private SemesterSeason currentSeason;
	private ArrayList<CourseItem> coursesAndExamsList;
	// private ArrayList<Item> calendarList;
	private LinkedHashMap<CourseKey, Course> coursesHash;

	private UGDatabase() {
		// initialize lists and student info, from DB TODO
		initCourses();
		initializeHashMap();
		initializeSemesters();
	}

	private void initCourses() {

		groups = new ArrayList<RegistrationGroup>(Arrays.asList(
				new RegistrationGroup(1, Arrays.asList(new Meeting("12",
						"Mr. Yossi happy", DayOfWeek.SUNDAY, Calendar
								.getInstance().getTime(), Calendar
								.getInstance().getTime(), "Ulman 329")), Arrays
						.asList(new Meeting("111", "Mr. Avi katz",
								DayOfWeek.SUNDAY, Calendar.getInstance()
										.getTime(), Calendar.getInstance()
										.getTime(), "Humanistics 329")), 0),
				new RegistrationGroup(2, Arrays.asList(new Meeting("13",
						"Prof. Monik", DayOfWeek.SUNDAY, Calendar.getInstance()
								.getTime(), Calendar.getInstance().getTime(),
						"Taub 10")), Arrays.asList(new Meeting("122",
						"Mr. Dani Cooperman", DayOfWeek.SUNDAY, Calendar
								.getInstance().getTime(), Calendar
								.getInstance().getTime(), "Humanistics 329"),

				new Meeting("123", "Mr. Moshiko Rozenblum", DayOfWeek.SUNDAY,
						Calendar.getInstance().getTime(), Calendar
								.getInstance().getTime(), "Humanistics 329")),
						25)));

		allCourses = new ArrayList<Course>(

				Arrays.asList(

						new Course(
								"233245",
								"Introduction to complicated algorithems 4",
								2.0f,
								"During the class we will talk about the high level design and your personal roles. We will also discuss your project topic (with each team). Teams that we already approved will use the time to start the design process",
								new Semester(2013, SemesterSeason.WINTER),
								Faculty.HUMANITIES, new GregorianCalendar(2014,
										2, 11), new GregorianCalendar(2014, 2,
										11), null, null, null),

						new Course("074957", "Convoluted systems 2", 5.0f, "",
								new Semester(2013, SemesterSeason.WINTER),
								Faculty.ARCHITECTURE, new GregorianCalendar(
										2014, 2, 11), new GregorianCalendar(
										2014, 2, 11), null, null, groups),

						new Course("043932", "Data Structures", 2.0f, "",
								new Semester(2013, SemesterSeason.SPRING),
								Faculty.HUMANITIES, new GregorianCalendar(2014,
										2, 11), new GregorianCalendar(2014, 2,
										11), null, null, groups),

						new Course(
								"232932",
								"logic in software",
								5.0f,
								"Brain-Machine interfaces will fundamentally change the way humans interact with the world in the 21st century. By creating a direct channel of communication between the mind and devices external to it, this class of technology provides individuals with the ability to bypass their body entirely, and control their environment using thought alone.",
								new Semester(2013, SemesterSeason.SPRING),
								Faculty.ARCHITECTURE, new GregorianCalendar(
										2014, 2, 11), new GregorianCalendar(
										2014, 2, 11), null, null, groups),

						new Course("232932",
								"Advanced practices in formal design", 5.0f,
								"", new Semester(2013, SemesterSeason.WINTER),
								Faculty.ARCHITECTURE, new GregorianCalendar(
										2014, 2, 11), new GregorianCalendar(
										2014, 2, 11), null, null, groups),

						new Course("012985", "Physics 2mm", 5.0f, "",
								new Semester(2013, SemesterSeason.SPRING),
								Faculty.PHYS,
								new GregorianCalendar(2014, 2, 11),
								new GregorianCalendar(2014, 2, 11), null, null,
								groups),

						new Course(
								"045932",
								"logic and formal languages",
								2.5f,
								"Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
								new Semester(2013, SemesterSeason.WINTER),
								Faculty.CS, new GregorianCalendar(2014, 2, 11),
								new GregorianCalendar(2014, 2, 11), null, null,
								groups),

						new Course(
								"011236",
								"Algebra 2mm",
								5.0f,
								"Dont even ask how hard this course is. You should be prepared for work hard.",
								new Semester(2013, SemesterSeason.WINTER),
								Faculty.ARCHITECTURE, new GregorianCalendar(
										2014, 2, 11), new GregorianCalendar(
										2014, 2, 11), null, null, groups),

						new Course(
								"123932",
								"Software engineering",
								5.0f,
								"Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
								new Semester(2013, SemesterSeason.WINTER),
								Faculty.ARCHITECTURE, new GregorianCalendar(
										2014, 2, 11), new GregorianCalendar(
										2014, 2, 11), null, null, groups),

						new Course(
								"023422",
								"Intoduction to formal design",
								5.0f,
								"Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
								new Semester(2013, SemesterSeason.WINTER),
								Faculty.ARCHITECTURE, new GregorianCalendar(
										2014, 2, 11), new GregorianCalendar(
										2014, 2, 11), null, null, groups),

						new Course(
								"012342",
								"System operations",
								5.0f,
								"Introduction. Numerical instability, numerical errors, loss of significant digits (cancellation). Iterative solution of scalar nonlinear equations: bisection method, Newton-Raphson method, secant method, convergence analysis. Approximation of functions: norms and seminorms, inner product, orthogonal systems, least squares, polynomial interpolation",
								new Semester(2013, SemesterSeason.WINTER),
								Faculty.ARCHITECTURE, new GregorianCalendar(
										2014, 2, 11), new GregorianCalendar(
										2014, 2, 11), null, null, groups)

				));
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
		currentSeason = SemesterSeason.WINTER;
		currentSemesters = new Semester[3];
		currentSemesters[SemesterSeason.SPRING.getIdx()] = new Semester(2013,
				SemesterSeason.SPRING);
		currentSemesters[SemesterSeason.SUMMER.getIdx()] = new Semester(2013,
				SemesterSeason.SUMMER);
		currentSemesters[SemesterSeason.WINTER.getIdx()] = new Semester(2013,
				SemesterSeason.WINTER);

		currentStudent = null;
		coursesHash = new LinkedHashMap<CourseKey, Course>();
		for (final Course course : allCourses)
			coursesHash.put(course.getCourseKey(), course);

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

}
