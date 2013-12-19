package com.technion.coolie.server.techoins;

public enum ReturnCode {
  NO_SUCH_FUNCTION("NO_SUCH_FUNCTION"), NO_OAUTH("NO_OAUTH"), STUDENT_ALREADY_EXISTS(
      "STUDENT_ALREADY_EXISTS"), SUCCESS("SUCCESS"), STUDENT_NOT_EXISTS(
      "STUDENT_NOT_EXISTS"), PRODUCT_ALREADY_EXISTS("PRODUCT_ALREADY_EXISTS"), PRODUCT_NOT_EXISTS(
      "PRODUCT_NOT_EXISTS"), BAD_PARAM("BAD_PARAM");

  private final String value;

  private ReturnCode(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
