package com.technion.coolie.server.ug.courses;

/**
 * Created on 8.12.2013
 * 
 * @author DANIEL
 * 
 */
public enum UgCourseFunctions {
  GET_COURSES("GET_COURSES"), GET_ALL_COURSES("GET_ALL_COURSES"), GET_STUDENT_COURSES(
      "GET_STUDENT_COURSES");

  private final String value;

  private UgCourseFunctions(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
