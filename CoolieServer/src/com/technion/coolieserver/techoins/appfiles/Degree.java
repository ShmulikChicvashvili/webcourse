package com.technion.coolieserver.techoins.appfiles;

public enum Degree {
  ADMIN("ADMIN"), USER("USER");

  private final String value;

  private Degree(String s) {
    value = s;
  }

  public String value() {
    return value;
  }

}
