package com.technion.coolie.ug.model;


public class AccomplishedCourse implements Comparable<AccomplishedCourse>  
{
	public AccomplishedCourse(String courseNumber, String name, String points, Semester semester, String grade) {
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
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public Semester getSemester() {
		return semester;
	}
	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	private String courseNumber;
	private String name;
	private String points;
	private Semester semester;
	private String grade;
	@Override
	public int compareTo(AccomplishedCourse another) {
		if (another==null || this.semester== null || another.semester == null) 
			return 0;
		return this.semester.compareTo(another.semester);
	}

}
