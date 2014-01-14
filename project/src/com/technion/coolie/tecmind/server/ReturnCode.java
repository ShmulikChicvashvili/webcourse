package com.technion.coolie.tecmind.server;
/**
 * 
 * Created on 8/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public enum ReturnCode {
  NO_SUCH_FUNCTION("NO_SUCH_FUNCTION"), NO_OAUTH("NO_OAUTH"), ENTITY_ALREADY_EXISTS(
      "ENTITY_ALREADY_EXISTS"), SUCCESS("SUCCESS"), ENTITY_NOT_EXISTS(
      "ENTITY_NOT_EXISTS"), BAD_PARAM("BAD_PARAM");

  private final String value;

  private ReturnCode(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
