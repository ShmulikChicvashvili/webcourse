package com.technion.coolie.server.ug.courses;

/**
 * Created on 8.12.2013
 * 
 * @author DANIEL
 * 
 */
public enum UgCourseFunctions {
  GET_STUDENT_COURSES("GET_STUDENT_COURSES"), GET_COURSES_BY_SEMESTER(
      "GET_COURSES_BY_SEMESTER"), GET_COURSES_BY_KEY("GET_COURSES_BY_KEY");

  private final String value;

  private UgCourseFunctions(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
