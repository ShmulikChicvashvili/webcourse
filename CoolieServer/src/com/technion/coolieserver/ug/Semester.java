package com.technion.coolieserver.ug;

public enum Semester {
  SPRING("SPRING"), WINTER("WINTER");

  private final String value;

  private Semester(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
