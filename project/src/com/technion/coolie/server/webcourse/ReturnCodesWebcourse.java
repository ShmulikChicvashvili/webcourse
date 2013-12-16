package com.technion.coolie.server.webcourse;

public enum ReturnCodesWebcourse {
  ERROR("ERROR"), NO_OAUTH("NO_OAUTH"), SUCCESS("SUCCESS");

  private final String value;

  private ReturnCodesWebcourse(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
