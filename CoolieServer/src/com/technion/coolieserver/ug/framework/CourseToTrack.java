package com.technion.coolieserver.ug.framework;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class CourseToTrack {
  @Id
  Long Id;
  String courseId;
  Semester semester;
  List<String> trackingStudents;
  int freePlaces;

  public CourseToTrack() {
  }

  public CourseToTrack(String courseId, Semester semester,
      List<String> trackingStudents, int freePlaces) {
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

  public void addTrackingStudent(String studentId) {
    trackingStudents.add(studentId);
  }

  public void removeTrackingStudent(String studentId) {
    trackingStudents.remove(studentId);
  }

}
