package com.technion.coolieserver.ug;

public enum DayOfWeek {
  SUNDAY("SUNDAY"), MONDAY("MONDAY");

  private final String value;

  private DayOfWeek(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
