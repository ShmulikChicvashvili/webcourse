package com.technion.coolieserver.ug.framework;

import java.util.List;

public class CourseClient {

  private Semester semester;
  private String courseNumber;
  private String name;
  private float points;
  private String description;
  private Faculty faculty;
  private long moedA;
  private long moedB;

  private List<GroupOfCoursesClient> prerequisites;

  CourseClient() {

  }

  public CourseClient(String courseNumber, String name, float points,
      String description, Semester semester, Faculty faculty, long moedA,
      long moedB, List<GroupOfCoursesClient> prerequisites) {
    this.courseNumber = courseNumber;
    this.name = name;
    this.points = points;
    this.description = description;
    this.semester = semester;
    this.faculty = faculty;
    this.moedA = moedA;
    this.moedB = moedB;
    this.prerequisites = prerequisites;
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

  public float getPoints() {
    return points;
  }

  public void setPoints(float points) {
    this.points = points;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Faculty getFaculty() {
    return faculty;
  }

  public void setFaculty(Faculty faculty) {
    this.faculty = faculty;
  }

  public long getMoedA() {
    return moedA;
  }

  public void setMoedA(long moedA) {
    this.moedA = moedA;
  }

  public long getMoedB() {
    return moedB;
  }

  public void setMoedB(long moedB) {
    this.moedB = moedB;
  }

  public List<GroupOfCoursesClient> getPrerequisites() {
    return prerequisites;
  }

  public void setPrerequisites(List<GroupOfCoursesClient> prerequisites) {
    this.prerequisites = prerequisites;
  }
}
