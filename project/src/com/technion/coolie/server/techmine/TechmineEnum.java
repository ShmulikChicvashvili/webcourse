package com.technion.coolie.server.techmine;

/**
 * 
 * Created on 8/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public enum TechmineEnum {
  ADD_USER("addUser"), REMOVE_USER("removeUser"), GET_TEC_USER("getUser"), GET_TOP_BEST_POSTS(
      "getTopBestPosts"), GET_TOP_BEST_COMMENTS("getTopBestComments"), ADD_TEC_POST(
      "addTecPost"), REMOVE_TEC_POST("removeTecPost"), GET_TEC_POST(
      "getTecPost"), ADD_TEC_COMMENT("addTecComment"), REMOVE_TEC_COMMENT(
      "removeTecComment"), GET_TEC_COMMENT("getTecComment"), ADD_TEC_LIKE(
      "addTecLike"), REMOVE_TEC_LIKE("removeTecLike"), GET_TEC_LIKE(
      "getTecLike"), TECHMINE_SERVLET("Techmine"), TEC_USER("tecUser"), TOP_BEST_POST(
      "tecTopBestPost"), TOP_BEST_COMMENT("tecTopBestComment"), TEC_POST(
      "tecPost"), TEC_COMMENT("tecComment"), TEC_LIKE("tecLike"), GET_ALL_USER_POSTS(
      "getAllUserPosts"), ADD_TEC_POST_LIST("addTecPostList"), ADD_TEC_COMMENT_LIST(
      "addTecCommentList"), GET_TOP_BEST_MINERS("getTopBestMiners");

  private final String value;

  private TechmineEnum(String s) {
    value = s;
  }

  public String value() {
    return value;
  }

}
