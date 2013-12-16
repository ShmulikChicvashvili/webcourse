package com.technion.coolieserver.ug;

public enum ReturnCodesUg {
  ERROR("ERROR"), SUCCESS("SUCCESS"), COURSE_DOES_NOT_EXSIST(
      "COURSE_DOES_NOT_EXSIST"), STUDENT_ALREADY_TRACKING(
      "STUDENT_ALREADY_TRACKING"), COURSE_DOES_NOT_ON_TRACK(
      "COURSE_DOES_NOT_ON_TRACK"), STUDENT_NOT_TRACKING("STUDENT_NOT_TRACKING"), COURSE_ALREADY_EXSIST(
      "COURSE_ALREADY_EXSIST");
  ;

  private final String value;

  private ReturnCodesUg(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
