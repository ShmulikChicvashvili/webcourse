package com.technion.coolie.techtrade;

public enum ReturnCode {
  NO_SUCH_FUNCTION("NO_SUCH_FUNCTION"), NO_OAUTH("NO_OAUTH"), STUDENT_ALREADY_EXISTS(
      "STUDENT_ALREADY_EXISTS"), SUCCESS("SUCCESS"), STUDENT_NOT_EXISTS(
      "STUDENT_NOT_EXISTS"), PRODUCT_ALREADY_EXISTS("PRODUCT_ALREADY_EXISTS"), PRODUCT_NOT_EXISTS(
      "PRODUCT_NOT_EXISTS");

  private final String value;

  private ReturnCode(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
