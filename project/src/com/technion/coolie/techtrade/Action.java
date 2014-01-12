package com.technion.coolie.techtrade;

public enum Action {
  MOVE("MOVE");

  private final String value;

  private Action(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
