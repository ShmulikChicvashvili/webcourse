package com.technion.coolieserver.techoins.appfiles;

public enum TechoinsFunctions {
  ADD_ACCOUNT("addAccount"), REMOVE_ACCOUNT("removeAccount"), GET_ACCOUNT(
      "getAccount"), MOVE_MONEY("moveMoney"), GET_HISTORY("getHistory");

  private final String value;

  private TechoinsFunctions(String s) {
    value = s;
  }

  public String value() {
    return value;
  }

}
