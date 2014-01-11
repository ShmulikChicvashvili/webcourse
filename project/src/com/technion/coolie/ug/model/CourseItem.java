package com.technion.coolie.ug.model;

import java.io.Serializable;
import java.util.List;

public class CourseItem implements Serializable {

	private final String coursName;
	private final String courseId;
	private final String points;
	private final String groupNumber;
	private final Semester semester;
	
	public String getGroupNumber() {
		return groupNumber;
	}

	public Semester getSemester() {
		return semester;
	}

	private final List<ExamItem> exams;

	public List<ExamItem> getExams() {
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

	public CourseItem(final String courseName, final String coursrId,
			final String points, final List<ExamItem> exams, final String groupNumber, final Semester semester) {
		coursName = courseName;
		courseId = coursrId;
		this.points = points;
		this.exams = exams;
		this.groupNumber = groupNumber;
		this.semester = semester;
	}

	private static final long serialVersionUID = -1335993469127597592L;
}
