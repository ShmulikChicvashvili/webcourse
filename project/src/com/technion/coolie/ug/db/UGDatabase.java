package com.technion.coolie.ug.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.technion.coolie.ug.Enums.DayOfWeek;
import com.technion.coolie.ug.Enums.Faculty;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.GroupInfo;
import com.technion.coolie.ug.model.RegistrationGroup;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;

public enum UGDatabase {
	INSTANCE;

	private Student currentStudent;
	private List<RegistrationGroup> groups; // TODO delete this.
	private List<Course> allCourses;
	private List<String> allCoursesNames;

	private LinkedHashMap<CourseKey, Course> coursesHash;

	private UGDatabase() {
		// initialize lists and student info, from DB TODO

		allCoursesNames = new ArrayList<String>(Arrays.asList(
				"אנליזה נומרית 20134 ", "מבוא למבני נתונים 26666",
				"נושאים מתקדמים בפיצוחים 23013", "תורת הכיף 20313",
				"שמחת חנוכיה 32322", "שמחה בבקבוק 21233", "שמחה בבקבוק 21233",
				"שמחה בבקבוק 21233", "שמחה בבקבוק 21233", "שמחה בבקבוק 21233",
				"שמחה בבקבוק 21233"));

		groups = new ArrayList<RegistrationGroup>(Arrays.asList(
				new RegistrationGroup("1", Arrays.asList(new GroupInfo("2",
						"מר חביבי לחם", DayOfWeek.SUNDAY, new Date(), null,
						"ספריה")), null, 1),
				new RegistrationGroup("1", Arrays.asList(new GroupInfo("2",
						"פרופ יוסי מצליח", DayOfWeek.SUNDAY, new Date(), null,
						"טאוב 5")), null, 20)));

		allCourses = new ArrayList<Course>(Arrays.asList(new Course("213245",
				"מבוא לבינה מלאכותית במודלים מתקדמים ביותר 4", "2.0",
				"הרג אותי", new Semester(2013, SemesterSeason.WINTER),
				Faculty.HUMANITIES, new Date(112000), new Date(112005),
				new ArrayList<String>(Arrays.asList("21345,23111")), null),
				new Course("012932", "קורס בחידוד עפרונות", "5.0",
						"ההרגשה היא אושר צרוף!!!", new Semester(2013,
								SemesterSeason.WINTER), Faculty.ARCHITECTURE,
						new Date(12030), new Date(2313132),
						new ArrayList<String>(Arrays.asList("21345,23111")),
						groups)));

		currentStudent = null;
		coursesHash = new LinkedHashMap<CourseKey, Course>();
		coursesHash.put(allCourses.get(0).getCourseKey(), allCourses.get(0));
		coursesHash.put(allCourses.get(1).getCourseKey(), allCourses.get(1)); // TODO
																				// make
																				// this
																				// a
																				// method

	}

	public Course getCourseByKey(CourseKey key) {
		return coursesHash.get(key);
	}

	public List<Course> getCourses() {
		allCourses.addAll(allCourses); // TODO delete this
		return allCourses;
	}

	// public List<String> getCoursesNames() {
	// return allCoursesNames;
	// }

}
