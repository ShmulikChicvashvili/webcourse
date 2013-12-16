package com.technion.coolie.server.webcourse.manager;

public enum WebcourseFunctions {
  GET_STAFF_INFO("GET_STAFF_INFO");

  private final String value;

  private WebcourseFunctions(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
