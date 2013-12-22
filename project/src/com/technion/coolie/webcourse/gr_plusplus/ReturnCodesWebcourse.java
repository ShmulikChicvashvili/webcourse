package com.technion.coolie.webcourse.gr_plusplus;

/**
 * Created on 15.12.2013
 * 
 * @author DANIEL
 * 
 */
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
