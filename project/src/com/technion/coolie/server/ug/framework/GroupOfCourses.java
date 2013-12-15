package com.technion.coolie.server.ug.framework;

import java.io.Serializable;
import java.util.List;

public class GroupOfCourses implements Serializable {

  List<String> courses;

  GroupOfCourses() {

  }

  public GroupOfCourses(List<String> courses) {
    this.courses = courses;
  }

  public List<String> getCourses() {
    return courses;
  }

  public void setCourses(List<String> courses) {
    this.courses = courses;
  }

}