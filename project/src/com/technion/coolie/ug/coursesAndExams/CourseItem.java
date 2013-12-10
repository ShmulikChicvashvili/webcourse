package com.technion.coolie.ug.coursesAndExams;

public class CourseItem {

	private final String coursName;
	private final String courseId;
	private final String points;

	public String getCoursName() {
		return coursName;
	}

	public String getCourseId() {
		return courseId;
	}

	public String getPoints() {
		return points;
	}

	public CourseItem(String courseName, String coursrId, String points) {
		this.coursName = courseName;
		this.courseId = coursrId;
		this.points = points;
	}
}
