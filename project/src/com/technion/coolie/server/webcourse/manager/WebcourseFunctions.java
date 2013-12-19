package com.technion.coolie.server.webcourse.manager;

/**
 * Created on 15.12.2013
 * 
 * @author DANIEL
 * 
 */
public enum WebcourseFunctions {
  GET_STAFF_INFO("GET_STAFF_INFO"), GET_ANNOUNCEMENT("GET_ANNOUNCEMENT"), GET_ASSIGNMENT(
      "GET_ANNOUNCEMENT");

  private final String value;

  private WebcourseFunctions(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
