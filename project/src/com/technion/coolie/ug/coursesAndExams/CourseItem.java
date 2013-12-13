package com.technion.coolie.ug.coursesAndExams;

import java.util.List;

public class CourseItem {

	private final String coursName;
	private final String courseId;
	private final String points;
	private final List<String> exams;

	public List<String> getExams() {
		return exams;
	}

	public String getCoursName() {
		return coursName;
	}

	public String getCourseId() {
		return courseId;
	}

	public String getPoints() {
		return points;
	}

	public CourseItem(String courseName, String coursrId, String points,
			List<String> exams) {
		this.coursName = courseName;
		this.courseId = coursrId;
		this.points = points;
		this.exams = exams;
	}
}
