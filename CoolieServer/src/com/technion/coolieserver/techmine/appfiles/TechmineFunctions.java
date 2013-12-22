package com.technion.coolieserver.techmine.appfiles;

public enum TechmineFunctions {
  ADD_USER("addUser"), REMOVE_USER("removeUser"), GET_USER("getUser"), ADD_TOP_BEST_POST(
      "addTopBestPost"), REMOVE_TOP_BEST_POST("removeTopBestPost"), GET_TOP_BEST_POST(
      "getTopBestPost"), ADD_TOP_BEST_COMMENT("addTopBestComment"), REMOVE_TOP_BEST_COMMENT(
      "removeTopBestComment"), GET_TOP_BEST_COMMENT("getTopBestComment"), ADD_TEC_POST(
      "addTecPost"), REMOVE_TEC_POST("removeTecPost"), GET_TEC_POST(
      "getTecPost"), ADD_TEC_COMMENT("addTecComment"), REMOVE_TEC_COMMENT(
      "removeTecComment"), GET_TEC_COMMENT("getTecComment"), ADD_TEC_LIKE(
      "addTecLike"), REMOVE_TEC_LIKE("removeTecLike"), GET_TEC_LIKE(
      "getTecLike");

  private final String value;

  private TechmineFunctions(String s) {
    value = s;
  }

  public String value() {
    return value;
  }

}
