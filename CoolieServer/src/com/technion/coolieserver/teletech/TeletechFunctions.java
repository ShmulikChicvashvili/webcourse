package com.technion.coolieserver.teletech;

public enum TeletechFunctions {
  GET_ALL_CONTACTS("GET_ALL_CONTACTS");

  private final String value;

  private TeletechFunctions(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
