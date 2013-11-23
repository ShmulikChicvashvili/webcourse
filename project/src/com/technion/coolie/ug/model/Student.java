package com.technion.coolie.ug.model;

import java.util.Date;
import java.util.List;

public class Student {
	private String id;
	private String name;
	private double avg;
	private double points;
	private Date registrationDate;

	private List<Payment> payments;
	private List<AccomplishedCourse> accomplishedCourses;
	private List<Exam> exams;
	private List<Exam> tests;
	private List<CourseKey> registeredCourses;

	private List<CourseKey> basket; // local
	private List<CourseKey> trackList; // local
}
