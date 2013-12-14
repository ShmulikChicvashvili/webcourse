package com.technion.coolie.ug.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.nodes.Document;

import com.technion.coolie.ug.HtmlParser;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.DayOfWeek;
import com.technion.coolie.ug.Enums.Faculty;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.coursesAndExams.CourseItem;
import com.technion.coolie.ug.gradessheet.Item;
import com.technion.coolie.ug.model.Course;
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
	private List<String> allCoursesNames;
	private Semester[] currentSemesters;
	private SemesterSeason currentSeason;
	private ArrayList<CourseItem> coursesAndExamsList;
	private ArrayList<Item> calendarList;
	private LinkedHashMap<CourseKey, Course> coursesHash;

	private UGDatabase() {
		// initialize lists and student info, from DB TODO

		initializeCourses();

		initializeSemesters();

		currentStudent = null;
		initializeHashMap();

	}

	private void initializeCourses() {
		groups = new ArrayList<RegistrationGroup>(Arrays.asList(
				new RegistrationGroup(1, Arrays.asList(new Meeting("2",
						"Mr. Yossi happy", DayOfWeek.SUNDAY, new Date(), null,
						"Ulman 329")), null, 1),
				new RegistrationGroup(2, Arrays.asList(new Meeting("2",
						"Prof. Monik", DayOfWeek.SUNDAY, new Date(), null,
						"Taub 10")), null, 20)));

		allCourses = new ArrayList<Course>(

		Arrays.asList(

		new Course("213245", "Introduction to complicated algorithems 4", 2.0f,
				"Interesting desctiption about the course", new Semester(2013,
						SemesterSeason.WINTER), Faculty.HUMANITIES,
				new GregorianCalendar(2014, 2, 11), new GregorianCalendar(2014,
						2, 11), null, null, null),

		new Course("012932", "Convoluted systems 2", 5.0f,
				"ok course. please come.", new Semester(2013,
						SemesterSeason.WINTER), Faculty.ARCHITECTURE,
				new GregorianCalendar(2014, 2, 11), new GregorianCalendar(2014,
						2, 11), null, null, groups),

		new Course("043932", "Data Structures in history", 2.0f, "fun",
				new Semester(2013, SemesterSeason.SPRING), Faculty.HUMANITIES,
				new GregorianCalendar(2014, 2, 11), new GregorianCalendar(2014,
						2, 11), null, null, groups),

		new Course("232932", "logic in software", 5.0f, "unbelievable",
				new Semester(2013, SemesterSeason.SPRING),
				Faculty.ARCHITECTURE, new GregorianCalendar(2014, 2, 11),
				new GregorianCalendar(2014, 2, 11), null, null, groups),

		new Course("012932", "Advanced practices in formal systems", 5.0f,
				"Group 1 is for students with hats only.", new Semester(2013,
						SemesterSeason.WINTER), Faculty.ARCHITECTURE,
				new GregorianCalendar(2014, 2, 11), new GregorianCalendar(2014,
						2, 11), null, null, groups),

		new Course("012932", "Advanced coding habits", 5.0f, "No idea.",
				new Semester(2013, SemesterSeason.SPRING),
				Faculty.ARCHITECTURE, new GregorianCalendar(2014, 2, 11),
				new GregorianCalendar(2014, 2, 11), null, null, groups)

		));

	}

	/**
	 * puts all the courses in a hashTable, mapping courseKey to course
	 */
	private void initializeHashMap() {
		coursesHash = new LinkedHashMap<CourseKey, Course>();
		for (Course course : allCourses)
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
	}

	public Course getCourseByKey(CourseKey key) {
		return coursesHash.get(key);
	}

	public List<Course> getCourses() {

		return allCourses;
	}

	public Semester getRelevantSemester(SemesterSeason season) {
		return currentSemesters[season.getIdx()];
	}

	public Semester getCurrentSemester() {
		return currentSemesters[currentSeason.getIdx()];
	}

	public List<Item> getGradesSheet() {
		return null;
	}

	public ArrayList<CourseItem> getStudentCourses(SemesterSeason semesterseason) {
		Document doc = null;
		// List<CourseItem> coursesList;
		switch (semesterseason) {
		case WINTER:
			doc = HtmlParser.parseFromFille("current_semester_courses.html",
					MainActivity.context);
			break;
		case SPRING:
			doc = HtmlParser.parseFromFille("previous_semester_courses.html",
					MainActivity.context);
			break;
		case SUMMER:
			doc = HtmlParser.parseFromFille("previous_semester_courses.html",
					MainActivity.context);
			break;
		}
		if (doc != null) {
			coursesAndExamsList = HtmlParser.parseCoursesAndExamsDoc(doc);
		}

		return coursesAndExamsList;
	}

	public ArrayList<Item> getCalendar() {
		return HtmlParser.parseCalendar();
	}
	// public List<String> getCoursesNames() {
	// return allCoursesNames;
	// }

}
