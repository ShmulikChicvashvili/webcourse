package com.technion.coolie.server.ug;

/**
 * Created on 8.12.2013
 * 
 * @author DANIEL
 * 
 */
public enum ReturnCodesUg {
  ERROR("ERROR"), NO_OAUTH("NO_OAUTH"), SUCCESS("SUCCESS"), COURSE_DOES_NOT_EXSIST(
      "COURSE_DOES_NOT_EXSIST"), STUDENT_ALREADY_TRACKING(
      "STUDENT_ALREADY_TRACKING"), COURSE_DOES_NOT_ON_TRACK(
      "COURSE_DOES_NOT_ON_TRACK"), STUDENT_NOT_TRACKING("STUDENT_NOT_TRACKING"), COURSE_ALREADY_EXSIST(
      "COURSE_ALREADY_EXSIST"), ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT");
  ;

  private final String value;

  private ReturnCodesUg(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
