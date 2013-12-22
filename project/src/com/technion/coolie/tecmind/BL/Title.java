package com.technion.coolie.tecmind.BL;


public enum Title {
  ATUDAI("ATUDAI"), NERD("NERD"), KNIGHT_NERD("KNIGHT NERD"), SUPER_NERD("SUPER NERD");

  private final String value;

  private Title(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
