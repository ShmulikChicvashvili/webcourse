package com.technion.coolieserver.ug;

public enum ReturnCodes {
  ERROR("ERROR"), SUCCESS("SUCCESS");

  private final String value;

  private ReturnCodes(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
