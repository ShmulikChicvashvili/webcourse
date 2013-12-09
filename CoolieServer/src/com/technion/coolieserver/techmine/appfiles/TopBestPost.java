package com.technion.coolieserver.techmine.appfiles;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class TopBestPost implements IGetters {
  @Id
  String id;
  String userId;

  /**
   * @param id1
   * @param userId1
   */
  public TopBestPost(String id1, String userId1) {
    this.id = id1;
    this.userId = userId1;
  }

  TopBestPost() {
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
