package com.technion.coolieserver.ug.framework;

import java.util.List;

public class GroupOfCoursesClient {

  List<String> courses;

  GroupOfCoursesClient() {

  }

  public GroupOfCoursesClient(List<String> courses) {
    this.courses = courses;
  }

  public List<String> getCourses() {
    return courses;
  }

  public void setCourses(List<String> courses) {
    this.courses = courses;
  }

}