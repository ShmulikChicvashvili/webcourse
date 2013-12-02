package com.technion.coolie.ug.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.technion.coolie.ug.Enums.DayOfWeek;
import com.technion.coolie.ug.Enums.Faculty;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.GroupInfo;
import com.technion.coolie.ug.model.RegistrationGroup;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;

public enum UGDatabase {
	INSTANCE;

	private UGDatabase() {
		// initialize lists and student

		allCoursesNames = new ArrayList<String>(Arrays.asList(
				"������ ������ 20134 ", "���� ����� ������ 26666",
				"������ ������� �������� 23013", "���� ���� 20313",
				"���� ������ 32322", "���� ������ 21233", "���� ������ 21233",
				"���� ������ 21233", "���� ������ 21233", "���� ������ 21233",
				"���� ������ 21233"));

		groups = new ArrayList<RegistrationGroup>(Arrays.asList(
				new RegistrationGroup("1", Arrays.asList(new GroupInfo("2",
						"�� ����� ���", DayOfWeek.SUNDAY, new Date(), null,
						"�����")), null, 1),
				new RegistrationGroup("1", Arrays.asList(new GroupInfo("2",
						"���� ���� �����", DayOfWeek.SUNDAY, new Date(), null,
						"���� 5")), null, 20)));

	}

	private Student currentStudent = null;
	private List<RegistrationGroup> groups;

	private List<Course> allCourses = new ArrayList<Course>(Arrays.asList(
			new Course("213245", "���� ����� �������� ������� ������� ����� 4",
					"2.0", "��� ����",
					new Semester(2013, SemesterSeason.WINTER),
					Faculty.HUMANITIES, new Date(112000), new Date(112005),
					new ArrayList<String>(Arrays.asList("21345,23111")), null),
			new Course("012932", "���� ������ �������", "5.0",
					"������ ��� ���� ����!!!", new Semester(2011,
							SemesterSeason.SUMMER), Faculty.ARCHITECTURE,
					new Date(12030), new Date(2313132), new ArrayList<String>(
							Arrays.asList("21345,23111")), groups)));

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
