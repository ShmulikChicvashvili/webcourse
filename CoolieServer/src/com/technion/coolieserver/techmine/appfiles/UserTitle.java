package com.technion.coolieserver.techmine.appfiles;

public enum UserTitle {
  ATUDAI("ATUDAI"), PERSON("PERSON");

  private final String value;

  private UserTitle(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
