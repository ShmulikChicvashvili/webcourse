package com.technion.coolie.server.teletech;

/**
 * Created on 6.12.2013
 * 
 * @author DANIEL
 * 
 */
public enum ReturnCodeTeletech {
  NO_SUCH_FUNCTION("NO_SUCH_FUNCTION"), NO_OAUTH("NO_OAUTH"), SUCCESS("SUCCESS"), CONTACT_DOES_NOT_EXIST(
      "CONTACT_DOES_NOT_EXIST"), ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT");

  private final String value;

  private ReturnCodeTeletech(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
