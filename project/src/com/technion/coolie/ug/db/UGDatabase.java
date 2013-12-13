package com.technion.coolie.ug.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

import com.technion.coolie.ug.Enums.DayOfWeek;
import com.technion.coolie.ug.Enums.Faculty;
import com.technion.coolie.ug.Enums.SemesterSeason;
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
						"�� ����� ���", DayOfWeek.SUNDAY, new Date(), null,
						"�����")), null, 1),
				new RegistrationGroup(2, Arrays.asList(new Meeting("2",
						"���� ���� �����", DayOfWeek.SUNDAY, new Date(), null,
						"���� 5")), null, 20)));

		allCourses = new ArrayList<Course>(
				Arrays.asList(
						new Course("213245",
								"���� ����� �������� ������� ������� ����� 4",
								2.0f, "��� ����", new Semester(2013,
										SemesterSeason.WINTER),
								Faculty.HUMANITIES, new GregorianCalendar(2014,
										2, 11), new GregorianCalendar(2014, 2,
										11), null, null, null),
						new Course("012932", "���� ������ �������", 5.0f,
								"������ ��� ���� ����!!!", new Semester(2013,
										SemesterSeason.WINTER),
								Faculty.ARCHITECTURE, new GregorianCalendar(
										2014, 2, 11), new GregorianCalendar(
										2014, 2, 11), null, null, groups),
						new Course("043932", "���� ������ 2", 2.0f, "���!!!",
								new Semester(2013, SemesterSeason.SPRING),
								Faculty.HUMANITIES, new GregorianCalendar(2014,
										2, 11), new GregorianCalendar(2014, 2,
										11), null, null, groups),
						new Course(
								"232932",
								"������ ������� ��������",
								5.0f,
								"������, ����� ��� ������, ����� �� ���� ����!!!",
								new Semester(2013, SemesterSeason.SPRING),
								Faculty.ARCHITECTURE, new GregorianCalendar(
										2014, 2, 11), new GregorianCalendar(
										2014, 2, 11), null, null, groups)));

		Course course = new Course("012932", "���� ������ �������", 5.0f,
				"������ ��� ���� ����!!!", new Semester(2013,
						SemesterSeason.WINTER), Faculty.ARCHITECTURE,
				new GregorianCalendar(2014, 2, 11), new GregorianCalendar(2014,
						2, 11), null, null, groups);
		course.setName("��� ���� ������ 30");
		allCourses.add(course);
		course = new Course(course);
		course.setName("����� �������");
		allCourses.add(course);
		course = new Course(course);
		course.setName("������ ��������");
		allCourses.add(course);
		course = new Course(course);
		course.setName("������ ����� �������");
		allCourses.add(course);
		course = new Course(course);
		course.setName("������ 6");
		allCourses.add(course);
		course = new Course(course);
		course.setName("������ 2");
		allCourses.add(course);
		course = new Course(course);
		course.setName("����� ���� ");
		allCourses.add(course);
	}

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
	// public List<String> getCoursesNames() {
	// return allCoursesNames;
	// }

}
