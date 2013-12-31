package com.technion.coolie.ug.model;

import java.io.Serializable;



public class AccomplishedCourse implements Comparable<AccomplishedCourse>,Serializable {
	



	public AccomplishedCourse(final String courseNumber, final String name,
			final String points, final String semester, final String grade, final String avg, final boolean isSection) {
		super();
		this.courseNumber = courseNumber;
		this.name = name;
		this.points = points;
		this.semester = semester;
		this.grade = grade;
		this.avg = avg;
		this.isSection = isSection;
	}
	
	public AccomplishedCourse() {
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

	public String getSemester() {
		return semester;
	}

	public void setSemester(final String semester) {
		this.semester = semester;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(final String grade) {
		this.grade = grade;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}
	
	public void setSection(boolean isSection) {
		this.isSection = isSection;
	}
	
	public boolean getSection(){
		return isSection;
	}

	private String courseNumber;
	private String name;
	private String points;
	private String semester;
	private String grade;
	private String avg;
	private boolean isSection;
		


	@Override
	public String toString() {
		return (courseNumber + "  " + name + "  " + points + "  " + grade + "  " + semester + "  " + avg);
	}

	@Override
	public int compareTo(final AccomplishedCourse another) {
		if (another == null || semester == null || another.semester == null)
			return 0;
		return semester.compareTo(another.semester);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1808964461246208374L;
}
