package com.technion.coolie.server.techmine;

/**
 * 
 * Created on 8/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public enum TecUserTitle {
  ATUDAI("ATUDAI"), PERSON("PERSON");

  private final String value;

  private TecUserTitle(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
