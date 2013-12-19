package com.technion.coolie.server.ug.framework;

import java.io.Serializable;

public enum SemesterSeason implements Serializable {
  WINTER("WINTER"), AUTUMN("AUTUMN"), SUMMER("SUMMER");

  private final String value;

  private SemesterSeason(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
