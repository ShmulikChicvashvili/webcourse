package com.technion.coolie.server.ug.tracking;

/**
 * Created on 8.12.2013
 * 
 * @author DANIEL
 * 
 */
public enum UgTrackingFunctions {
  ADD_TRACKING_STUDENT("ADD_TRACKING_STUDENT"), REMOVE_TRACKING_STUDENT_FROM_COURSE(
      "REMOVE_TRACKING_STUDENT_FROM_COURSE");

  private final String value;

  private UgTrackingFunctions(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
