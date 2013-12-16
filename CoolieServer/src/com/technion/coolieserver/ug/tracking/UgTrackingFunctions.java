package com.technion.coolieserver.ug.tracking;

public enum UgTrackingFunctions {
  ADD_TRACKING_STUDENT("ADD_TRACKING_STUDENT"), REMOVE_TRACKING_STUDENT_FROM_COURSE(
      "REMOVE_TRACKING_STUDENT_FROM_COURSE"), GET_ALL_STUDENT_TRACKING_AFTER_COURSE(
      "GET_ALL_STUDENT_TRACKING_AFTER_COURSE");

  private final String value;

  private UgTrackingFunctions(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
