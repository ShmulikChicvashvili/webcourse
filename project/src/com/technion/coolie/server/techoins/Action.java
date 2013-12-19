package com.technion.coolie.server.techoins;

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
