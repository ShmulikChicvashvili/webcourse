package com.technion.coolie.ug.model;

import com.technion.coolie.ug.gradessheet.Item;

public class AccomplishedCourse implements Comparable<AccomplishedCourse>, Item {
	public AccomplishedCourse(final String courseNumber, final String name,
			final String points, final Semester semester, final String grade) {
		super();
		this.courseNumber = courseNumber;
		this.name = name;
		this.points = points;
		this.semester = semester;
		this.grade = grade;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(final String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(final String points) {
		this.points = points;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(final Semester semester) {
		this.semester = semester;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(final String grade) {
		this.grade = grade;
	}

	private String courseNumber;
	private String name;
	private String points;
	private Semester semester;
	private String grade;

	@Override
	public int compareTo(final AccomplishedCourse another) {
		if (another == null || semester == null || another.semester == null)
			return 0;
		return semester.compareTo(another.semester);
	}

	@Override
	public boolean isSection() {
		return false;
	}

	@Override
	public boolean isFooter() {
		return false;
	}

}
