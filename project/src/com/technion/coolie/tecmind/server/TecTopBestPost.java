package com.technion.coolie.tecmind.server;

public class TecTopBestPost implements IGetters {

  String id;
  String userId;

  /**
   * @param id1
   * @param userId1
   */
  public TecTopBestPost(String id1, String userId1) {
    this.id = id1;
    this.userId = userId1;
  }

  TecTopBestPost() {
  }

  /**
   * @return the id
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * @param id1
   *          the id to set
   */
  public void setId(String id1) {
    this.id = id1;
  }

  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @param userId1
   *          the userId to set
   */
  public void setUserId(String userId1) {
    this.userId = userId1;
  }

}
