package com.technion.coolie.server.techmine.manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.technion.coolie.server.techmine.IGetters;

public class TecManager implements IGetters {
  Long mId;
  String mPassword;
  int mPostValue = 0;
  int mCommentValue = 0;
  int mLikeValue = 0;

  List<TecGroup> mGroups = new ArrayList<TecGroup>();

  private static TecManager manager = null;

  private TecManager(String sPassword) {
    mPassword = sPassword;
  }

  public TecManager getInstance(String pass) {
    if (manager == null) {
      manager = new TecManager(pass);
    }
    return manager;
  }

  public int getPostValue() {
    return mPostValue;
  }

  public void setPostValue(int sPostValue) {
    mPostValue = sPostValue;
  }

  public int getCommentValue() {
    return mCommentValue;
  }

  public void setCommentValue(int sCommentValue) {
    mCommentValue = sCommentValue;
  }

  public int getLikeValue() {
    return mPostValue;
  }

  public void setLikeValue(int sLikeValue) {
    mLikeValue = sLikeValue;
  }

  public List<TecGroup> getGroups() {
    return mGroups;
  }

  public void setGroups(LinkedList<TecGroup> sGroups) {
    mGroups.addAll(sGroups);
  }

  @Override
  public String getId() {
    return mId.toString();
  }
}
