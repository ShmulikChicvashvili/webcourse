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

	private static Student currentStudent = null;
	private static List<RegistrationGroup> groups = new ArrayList<RegistrationGroup>(
			Arrays.asList(
					new RegistrationGroup("1", Arrays.asList(new GroupInfo("2",
							"�� ����� ���", DayOfWeek.SUNDAY, new Date(), null,
							"�����")), null, 1),
					new RegistrationGroup("1", Arrays.asList(new GroupInfo("2",
							"���� ���� �����", DayOfWeek.SUNDAY, new Date(),
							null, "���� 5")), null, 20)));

	private static List<Course> allCourses = Arrays.asList(
			new Course("213245", "���� ����� ��������", "2.0", "��� ����",
					new Semester(2013, SemesterSeason.WINTER), Faculty.CS,
					new Date(112000), new Date(112005), new ArrayList<String>(
							Arrays.asList("21345,23111")), null), new Course(
					"012932", "���� ������ �������", "5.0",
					"������ ��� ���� ����!!!", new Semester(2011,
							SemesterSeason.SUMMER), Faculty.ARCHITECTURE,
					new Date(12030), new Date(2313132), new ArrayList<String>(
							Arrays.asList("21345,23111")), groups));

	public Course getCourseById(String id) {
		// TODO
		return null;
	}

}
