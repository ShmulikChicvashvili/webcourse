package com.technion.coolie.tecmind.server;
/**
 * 
 * Created on 8/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public enum TecUserTitle {
  ATUDAI("ATUDAI"), NERD("NERD"), KNIGHT_NERD("KNIGHT NERD"), SUPER_NERD(
      "SUPER NERD");

  private final String value;

  private TecUserTitle(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
