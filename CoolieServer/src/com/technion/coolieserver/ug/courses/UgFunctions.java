package com.technion.coolieserver.ug.courses;

public enum UgFunctions {
  ADD_COURSE("ADD_COURSE"), FIND_COURSE_BY_NUMBER("FIND_COURSE_BY_NUMBER"), FIND_COURSE_BY_PREFIX_NAME(
      "FIND_COURSE_BY_PREFIX_NAME");

  private final String value;

  private UgFunctions(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
