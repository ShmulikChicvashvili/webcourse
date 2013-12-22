package com.technion.coolie.server.techmine;

public class TecTopBestComment implements IGetters {

  String id;
  String userId;

  /**
   * @param id1
   * @param userId1
   */
  public TecTopBestComment(String id1, String userId1) {
    this.id = id1;
    this.userId = userId1;
  }

  TecTopBestComment() {
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
