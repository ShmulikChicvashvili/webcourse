package com.technion.coolie.server.techoins;

/**
 * 
 * Created on 7/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public enum Degree {
  ADMIN("ADMIN"), USER("USER");

  private final String value;

  private Degree(String s) {
    value = s;
  }

  public String value() {
    return value;
  }

}
