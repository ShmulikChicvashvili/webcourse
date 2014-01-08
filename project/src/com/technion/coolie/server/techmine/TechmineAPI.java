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
  public ReturnCode addUsers(List<TecUser> users) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.ADD_USERS.toString(), TechmineEnum.TEC_USER.value(),
        gson.toJson(users)));
  }

  @Override
  public ReturnCode updateUsers(List<TecUser> users) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.UPDATE_USERS.toString(), TechmineEnum.TEC_USER.value(),
        gson.toJson(users)));
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
  public List<TecPost> getTopBestPosts() {
    return gson.fromJson(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.GET_TOP_BEST_POSTS.toString()),
        new TypeToken<List<TecPost>>() {
          // default usage
        }.getType());
  }

  @Override
  public List<TecComment> getTopBestComments() {
    return gson.fromJson(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.GET_TOP_BEST_COMMENTS.toString()),
        new TypeToken<List<TecComment>>() {
          // default usage
        }.getType());
  }

  @Override
  public List<TecComment> getTopBestMiners() {
    return gson.fromJson(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.GET_TOP_BEST_MINERS.toString()),
        new TypeToken<List<TecUser>>() {
          // default usage
        }.getType());
  }

  @Override
  public ReturnCode addTecPosts(List<TecPost> tecPosts) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.ADD_TEC_POSTS.toString(), TechmineEnum.TEC_POST.value(),
        gson.toJson(tecPosts)));
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
  public ReturnCode addTecComments(List<TecComment> tecComments) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.ADD_TEC_COMMENTS.toString(),
        TechmineEnum.TEC_COMMENT.value(), gson.toJson(tecComments)));
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
  public ReturnCode addTecLikes(List<TecLike> tecLikes) {
    return ReturnCode.valueOf(Communicator.execute(
        TechmineEnum.TECHMINE_SERVLET.value(), "function",
        TechmineEnum.ADD_TEC_LIKES.toString(), TechmineEnum.TEC_LIKE.value(),
        gson.toJson(tecLikes)));
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
