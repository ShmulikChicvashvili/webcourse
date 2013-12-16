package com.technion.coolie.ug.model;

import java.util.Calendar;
import java.util.List;

public class Student {

	public Student(final String id, final String name, final double avg,
			final double points, final Calendar registrationDate,
			final List<Payment> payments,
			final List<AccomplishedCourse> accomplishedCourses,
			final List<Exam> exams, final List<Exam> tests,
			final List<CourseKey> registeredCourses,
			final List<CourseKey> basket, final List<CourseKey> trackList) {
		super();
		this.id = id;
		this.name = name;
		this.avg = avg;
		this.points = points;
		this.registrationDate = registrationDate;
		this.payments = payments;
		this.accomplishedCourses = accomplishedCourses;
		this.exams = exams;
		this.tests = tests;
		this.registeredCourses = registeredCourses;
		this.basket = basket;
		this.trackList = trackList;
	}

	private String id;
	private String name;
	private double avg;
	private double points;
	private Calendar registrationDate;

	private List<Payment> payments;
	private List<AccomplishedCourse> accomplishedCourses;
	private List<Exam> exams;
	private List<Exam> tests;
	private List<CourseKey> registeredCourses;

	private List<CourseKey> basket; // local
	private List<CourseKey> trackList; // local

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(final double avg) {
		this.avg = avg;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(final double points) {
		this.points = points;
	}

	public Calendar getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(final Calendar registrationDate) {
		this.registrationDate = registrationDate;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(final List<Payment> payments) {
		this.payments = payments;
	}

	public List<AccomplishedCourse> getAccomplishedCourses() {
		return accomplishedCourses;
	}

	public void setAccomplishedCourses(
			final List<AccomplishedCourse> accomplishedCourses) {
		this.accomplishedCourses = accomplishedCourses;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(final List<Exam> exams) {
		this.exams = exams;
	}

	public List<Exam> getTests() {
		return tests;
	}

	public void setTests(final List<Exam> tests) {
		this.tests = tests;
	}

	public List<CourseKey> getRegisteredCourses() {
		return registeredCourses;
	}

	public void setRegisteredCourses(final List<CourseKey> registeredCourses) {
		this.registeredCourses = registeredCourses;
	}

	public List<CourseKey> getBasket() {
		return basket;
	}

	public void setBasket(final List<CourseKey> basket) {
		this.basket = basket;
	}

	public List<CourseKey> getTrackList() {
		return trackList;
	}

	public void setTrackList(final List<CourseKey> trackList) {
		this.trackList = trackList;
	}

}
