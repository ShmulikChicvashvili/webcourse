package com.technion.coolieserver.techmine.appfiles;

public enum ReturnCode {
  NO_SUCH_FUNCTION("NO_SUCH_FUNCTION"), NO_OAUTH("NO_OAUTH"), ENTITY_ALREADY_EXISTS(
      "ENTITY_ALREADY_EXISTS"), SUCCESS("SUCCESS"), ENTITY_NOT_EXISTS(
      "ENTITY_NOT_EXISTS");

  private final String value;

  private ReturnCode(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
