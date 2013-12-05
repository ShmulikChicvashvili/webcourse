package com.technion.coolie.ug.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.technion.coolie.ug.Enums.DayOfWeek;
import com.technion.coolie.ug.Enums.Faculty;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.Meeting;
import com.technion.coolie.ug.model.RegistrationGroup;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;

public enum UGDatabase {
	INSTANCE;

	private UGDatabase() {
		// initialize lists and student

		allCoursesNames = new ArrayList<String>(Arrays.asList(
				"אנליזה נומרית 20134 ", "מבוא למבני נתונים 26666",
				"נושאים מתקדמים בפיצוחים 23013", "תורת הכיף 20313",
				"שמחת חנוכיה 32322", "שמחה בבקבוק 21233", "שמחה בבקבוק 21233",
				"שמחה בבקבוק 21233", "שמחה בבקבוק 21233", "שמחה בבקבוק 21233",
				"שמחה בבקבוק 21233"));

		groups = new ArrayList<RegistrationGroup>(Arrays.asList(
				new RegistrationGroup(1, Arrays.asList(new Meeting("2",
						"מר חביבי לחם", DayOfWeek.SUNDAY, new Date(), null,
						"ספריה")), null, 1),
				new RegistrationGroup(2, Arrays.asList(new Meeting("2",
						"פרופ יוסי מצליח", DayOfWeek.SUNDAY, new Date(), null,
						"טאוב 5")), null, 20)));

	}

	private Student currentStudent = null;
	private List<RegistrationGroup> groups;

	private List<Course> allCourses = new ArrayList<Course>(Arrays.asList(
			new Course("213245", "מבוא לבינה מלאכותית במודלים מתקדמים ביותר 4",
					2.0f, "הרג אותי",
					new Semester(2013, SemesterSeason.WINTER),
					Faculty.HUMANITIES, new Date(112000), new Date(112005),
					null, null, null),
			new Course("012932", "קורס בחידוד עפרונות", 5.0f,
					"ההרגשה היא אושר צרוף!!!", new Semester(2011,
							SemesterSeason.SUMMER), Faculty.ARCHITECTURE,
					new Date(12030), new Date(2313132), null,null, groups)));

	private List<String> allCoursesNames;

	public Course getCourseById(String id) {
		// TODO
		return null;
	}

	public List<Course> getCourses() {
		allCourses.addAll(allCourses);
		return allCourses;
	}

	public List<String> getCoursesNames() {
		return allCoursesNames;
	}

}
