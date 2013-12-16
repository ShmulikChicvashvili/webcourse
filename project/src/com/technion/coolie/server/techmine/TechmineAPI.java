/**
 * 
 */
package com.technion.coolie.server.techmine;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.Communicator;

/**
 * 
 * Created on 8/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public class TechmineAPI implements ITechmineAPI {

  Gson gson = new Gson();

  @Override
  public ReturnCode addUser(TecUser user) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.ADD_USER.toString(), TechmineEnum.TEC_USER.value(),
        gson.toJson(user)));
  }

  @Override
  public ReturnCode removeUser(TecUser user) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.REMOVE_USER.toString(), TechmineEnum.TEC_USER.value(),
        gson.toJson(user)));
  }

  @Override
  public TecUser getUser(TecUser user) {
    return gson.fromJson(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.GET_TEC_USER.toString(), TechmineEnum.TEC_USER.value(),
        gson.toJson(user)), TecUser.class);
  }

  @Override
  public ReturnCode addTopBestPost(TecTopBestPost topBestPost) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.ADD_TOP_BEST_POST.toString(),
        TechmineEnum.TOP_BEST_POST.value(), gson.toJson(topBestPost)));
  }

  @Override
  public ReturnCode removeTopBestPost(TecTopBestPost topBestPost) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.REMOVE_TOP_BEST_POST.toString(),
        TechmineEnum.TOP_BEST_POST.value(), gson.toJson(topBestPost)));
  }

  @Override
  public TecTopBestPost getTopBestPost(TecTopBestPost topBestPost) {
    return gson.fromJson(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.GET_TOP_BEST_POST.toString(),
        TechmineEnum.TOP_BEST_POST.value(), gson.toJson(topBestPost)),
        TecTopBestPost.class);
  }

  @Override
  public ReturnCode addTopBestComment(TecTopBestComment topBestComment) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.ADD_TOP_BEST_COMMENT.toString(),
        TechmineEnum.TOP_BEST_COMMENT.value(), gson.toJson(topBestComment)));
  }

  @Override
  public ReturnCode removeTopBestComment(TecTopBestComment topBestComment) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.REMOVE_TOP_BEST_COMMENT.toString(),
        TechmineEnum.TOP_BEST_COMMENT.value(), gson.toJson(topBestComment)));
  }

  @Override
  public TecTopBestComment getTopBestComment(TecTopBestComment topBestComment) {
    return gson.fromJson(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.GET_TOP_BEST_COMMENT.toString(),
        TechmineEnum.TOP_BEST_COMMENT.value(), gson.toJson(topBestComment)),
        TecTopBestComment.class);
  }

  @Override
  public ReturnCode addTecPost(TecPost tecPost) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.ADD_TEC_POST.toString(), TechmineEnum.TEC_POST.value(),
        gson.toJson(tecPost)));
  }

  @Override
  public ReturnCode removeTecPost(TecPost tecPost) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.REMOVE_TEC_POST.toString(), TechmineEnum.TEC_POST.value(),
        gson.toJson(tecPost)));
  }

  @Override
  public TecPost getTecPost(TecPost tecPost) {
    return gson.fromJson(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.GET_TEC_POST.toString(), TechmineEnum.TEC_POST.value(),
        gson.toJson(tecPost)), TecPost.class);
  }

  @Override
  public ReturnCode addTecComment(TecComment tecComment) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.ADD_TEC_COMMENT.toString(),
        TechmineEnum.TEC_COMMENT.value(), gson.toJson(tecComment)));
  }

  @Override
  public ReturnCode removeTecComment(TecComment tecComment) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.REMOVE_TEC_COMMENT.toString(),
        TechmineEnum.TEC_COMMENT.value(), gson.toJson(tecComment)));
  }

  @Override
  public TecComment getTecComment(TecComment tecComment) {
    return gson.fromJson(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.GET_TEC_COMMENT.toString(),
        TechmineEnum.TEC_COMMENT.value(), gson.toJson(tecComment)),
        TecComment.class);
  }

  @Override
  public ReturnCode addTecLike(TecLike tecLike) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.ADD_TEC_LIKE.toString(), TechmineEnum.TEC_LIKE.value(),
        gson.toJson(tecLike)));
  }

  @Override
  public ReturnCode removeTecLike(TecLike tecLike) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.REMOVE_TEC_LIKE.toString(),
        TechmineEnum.TEC_COMMENT.value(), gson.toJson(tecLike)));
  }

  @Override
  public TecLike getTecLike(TecLike tecLike) {
    return gson.fromJson(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.GET_TEC_LIKE.toString(), TechmineEnum.TEC_COMMENT.value(),
        gson.toJson(tecLike)), TecLike.class);
  }

  @Override
  public List<TecPost> getAllUserPosts(TecUser user) {
    return gson.fromJson(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.GET_ALL_USER_POSTS.toString(),
        TechmineEnum.TEC_USER.value(), gson.toJson(user)),
        new TypeToken<List<TecPost>>() {
          // default usage
        }.getType());
  }

}
