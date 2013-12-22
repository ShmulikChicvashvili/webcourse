package com.technion.coolie.teletech.manager;

/**
 * Created on 6.12.2013
 * 
 * @author DANIEL
 * 
 */
public enum TeletechFunctions {
  GET_ALL_CONTACTS("GET_ALL_CONTACTS");

  private final String value;

  private TeletechFunctions(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
