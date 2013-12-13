package com.technion.coolie.ug.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.nodes.Document;

import android.content.Context;

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
	public Context context;

	private LinkedHashMap<CourseKey, Course> coursesHash;

	private UGDatabase() {
		// initialize lists and student info, from DB TODO

		groups = new ArrayList<RegistrationGroup>(Arrays.asList(
				new RegistrationGroup(1, Arrays.asList(new Meeting("2",
						"מר חביבי לחם", DayOfWeek.SUNDAY, new Date(), null,
						"ספריה")), null, 1),
				new RegistrationGroup(2, Arrays.asList(new Meeting("2",
						"פרופ יוסי מצליח", DayOfWeek.SUNDAY, new Date(), null,
						"טאוב 5")), null, 20)));

		allCourses = new ArrayList<Course>(
				Arrays.asList(new Course("213245",
						"מבוא לבינה מלאכותית במודלים מתקדמים ביותר 4", 2.0f,
						"הרג אותי", new Semester(2013, SemesterSeason.WINTER),
						Faculty.HUMANITIES, new GregorianCalendar(2014, 2, 11),
						new GregorianCalendar(2014, 2, 11), null, null, null),
						new Course("012932", "קורס בחידוד עפרונות", 5.0f,
								"ההרגשה היא אושר צרוף!!!", new Semester(2013,
										SemesterSeason.WINTER),
								Faculty.ARCHITECTURE, new GregorianCalendar(
										2014, 2, 11), new GregorianCalendar(
										2014, 2, 11), null, null, groups)));

		currentSeason = SemesterSeason.WINTER;
		currentSemesters = new Semester[3];
		currentSemesters[SemesterSeason.SPRING.getIdx()] = new Semester(2012,
				SemesterSeason.SPRING);
		currentSemesters[SemesterSeason.SUMMER.getIdx()] = new Semester(2012,
				SemesterSeason.SUMMER);
		currentSemesters[SemesterSeason.WINTER.getIdx()] = new Semester(2013,
				SemesterSeason.WINTER);

		currentStudent = null;
		coursesHash = new LinkedHashMap<CourseKey, Course>();
		for (Course course : allCourses)
			coursesHash.put(course.getCourseKey(), course);

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

	public List<CourseItem> getStudentCourses(Semester semester) {
		/*Document doc;
		switch (semester.getSs()) {
		case WINTER:
			doc = HtmlParser.parseFromFille("my_current_courses.html", MainActivity.context);
//			System.out.println(doc.toString());
			List<CourseItem> coursesList = HtmlParser.parseCoursesAndExamsDoc(doc);
			break;
		case SPRING:
			break;
		case SUMMER:
			break;
		}
*/
		return null;
	}
	// public List<String> getCoursesNames() {
	// return allCoursesNames;
	// }

}
