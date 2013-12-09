package com.technion.coolieserver.ug;

public enum Faculty {
  CS("CS"), SS("SS");

  private final String value;

  private Faculty(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
