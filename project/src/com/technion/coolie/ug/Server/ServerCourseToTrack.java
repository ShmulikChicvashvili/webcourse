package com.technion.coolie.ug.Server;

import java.util.List;

import com.technion.coolie.ug.model.Semester;

public class ServerCourseToTrack {
	int id;
	String courseId;
	Semester semester;
	List<String> trackingStudents;
	int freePlaces;

	public ServerCourseToTrack(final String courseId, final Semester semester,
			final List<String> trackingStudents, final int freePlaces) {
		super();
		this.courseId = courseId;
		this.semester = semester;
		this.trackingStudents = trackingStudents;
		this.freePlaces = freePlaces;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(final String courseId) {
		this.courseId = courseId;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(final Semester semester) {
		this.semester = semester;
	}

	public List<String> getTrackingStudents() {
		return trackingStudents;
	}

	public void setTrackingStudents(final List<String> trackingStudents) {
		this.trackingStudents = trackingStudents;
	}

	public int getFreePlaces() {
		return freePlaces;
	}

	public void setFreePlaces(final int freePlaces) {
		this.freePlaces = freePlaces;
	}

}
