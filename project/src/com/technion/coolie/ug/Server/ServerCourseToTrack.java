package com.technion.coolie.ug.Server;

import java.util.List;

import com.technion.coolie.ug.model.Semester;

public class ServerCourseToTrack 
{
	int id;
	String courseId;
	Semester semester;
	List<String> trackingStudents;
	int freePlaces;
	
	public ServerCourseToTrack(String courseId, Semester semester, List<String> trackingStudents, int freePlaces) {
		super();
		this.courseId = courseId;
		this.semester = semester;
		this.trackingStudents = trackingStudents;
		this.freePlaces = freePlaces;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public Semester getSemester() {
		return semester;
	}
	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	public List<String> getTrackingStudents() {
		return trackingStudents;
	}
	public void setTrackingStudents(List<String> trackingStudents) {
		this.trackingStudents = trackingStudents;
	}
	public int getFreePlaces() {
		return freePlaces;
	}
	public void setFreePlaces(int freePlaces) {
		this.freePlaces = freePlaces;
	}
	
}
