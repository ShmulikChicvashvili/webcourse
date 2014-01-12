package com.technion.coolie.server.techmine;

/**
 * 
 * Created on 8/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public enum TechmineEnum {
  ADD_USERS("addUsers"), REMOVE_USER("removeUser"), GET_TEC_USER("getUser"), GET_TOP_BEST_POSTS(
      "getTopBestPosts"), GET_TOP_BEST_COMMENTS("getTopBestComments"), ADD_TEC_POSTS(
      "addTecPosts"), REMOVE_TEC_POST("removeTecPost"), GET_TEC_POST(
      "getTecPost"), ADD_TEC_COMMENTS("addTecComments"), REMOVE_TEC_COMMENT(
      "removeTecComment"), GET_TEC_COMMENT("getTecComment"), ADD_TEC_LIKES(
      "addTecLikes"), REMOVE_TEC_LIKE("removeTecLike"), GET_TEC_LIKE(
      "getTecLike"), TECHMINE_SERVLET("Techmine"), TEC_USER("tecUser"), TEC_POST(
      "tecPost"), TEC_COMMENT("tecComment"), TEC_LIKE("tecLike"), GET_ALL_USER_POSTS(
      "getAllUserPosts"), GET_TOP_BEST_MINERS("getTopBestMiners"), UPDATE_USERS(
      "updateUsers"), GET_TOP_BEST_MINERS_OF_THE_WEEK(
      "getTopBestMinersOfTheWeek");

  private final String value;

  private TechmineEnum(String s) {
    value = s;
  }

  public String value() {
    return value;
  }

}
