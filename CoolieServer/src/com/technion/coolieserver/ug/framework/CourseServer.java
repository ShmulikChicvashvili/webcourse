package com.technion.coolieserver.ug.framework;

import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class CourseServer {
  @Id
  Long id;
  private Semester semester;
  private String courseNumber;
  private String name;
  private float points;
  private String description;
  private Faculty faculty;
  private long moedA;
  private long moedB;

  private List<Ref<GroupOfCoursesServer>> prerequisites;

  public CourseServer() {

  }

  public CourseServer(String courseNumber, String name, float points,
      String description, Semester semester, Faculty faculty, long moedA,
      long moedB, List<GroupOfCoursesServer> prerequisites) {
    this.courseNumber = courseNumber;
    this.name = name;
    this.points = points;
    this.description = description;
    this.semester = semester;
    this.faculty = faculty;
    this.moedA = moedA;
    this.moedB = moedB;
  }

  public List<GroupOfCoursesServer> getPrerequisitesList() {
    return Deref.deref(prerequisites);
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

  public List<Ref<GroupOfCoursesServer>> getPrerequisites() {
    return prerequisites;
  }

  public void setPrerequisites(List<Ref<GroupOfCoursesServer>> prerequisites) {
    this.prerequisites = prerequisites;
  }

  public CourseKey getCourseKey() {
    return new CourseKey(courseNumber, semester);
  }

  public void setBasicVars(CourseClient serverCourse) {
    this.courseNumber = serverCourse.getCourseNumber();
    this.name = serverCourse.getName();
    this.points = serverCourse.getPoints();
    this.description = serverCourse.getDescription();
    this.semester = serverCourse.getSemester();
    this.faculty = serverCourse.getFaculty();
    this.moedA = serverCourse.getMoedA();
    this.moedB = serverCourse.getMoedB();
  }

}
