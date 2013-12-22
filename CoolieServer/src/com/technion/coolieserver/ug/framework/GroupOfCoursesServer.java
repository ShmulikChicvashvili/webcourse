package com.technion.coolieserver.ug.framework;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class GroupOfCoursesServer {
  @Id
  Long id;
  List<String> courses;

  GroupOfCoursesServer() {

  }

  public GroupOfCoursesServer(List<String> courses) {
    this.courses = courses;
  }

  public List<String> getCourses() {
    return courses;
  }

  public void setCourses(List<String> courses) {
    this.courses = courses;
  }

}
