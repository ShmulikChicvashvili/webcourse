package com.technion.coolie.tecmind.server;

import java.net.URL;
import java.util.Date;

public class TecPost implements IGetters {
  String id;
  Date date;
  int technionValue;
  String userID;
  int likesCount;
  int commentCount;
  String content;
  URL url;
  String group;
  int spamCount;
  String spamType;

  /**
   * @param id1
   * @param date1
   * @param technionValue1
   * @param userID1
   * @param likesCount1
   * @param commentCount1
   * @param url1
   * @param group1
   * @param spamCount1
   * @param content1
   * @param spamType1
   */
  public TecPost(String id1, Date date1, int technionValue1, String userID1,
      int likesCount1, int commentCount1, URL url1, String group1,
      String content1, int spamCount1, String spamType1) {
    this.id = id1;
    this.date = date1;
    this.technionValue = technionValue1;
    this.userID = userID1;
    this.likesCount = likesCount1;
    this.commentCount = commentCount1;
    this.url = url1;
    this.group = group1;
    this.spamCount = spamCount1;
    this.spamType = spamType1;
    this.content = content1;
  }

  TecPost() {
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
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * @param date1
   *          the date to set
   */
  public void setDate(Date date1) {
    this.date = date1;
  }

  /**
   * @return the technionValue
   */
  public int getTechnionValue() {
    return technionValue;
  }

  /**
   * @param technionValue1
   *          the technionValue to set
   */
  public void setTechnionValue(int technionValue1) {
    this.technionValue = technionValue1;
  }

  /**
   * @return the userID
   */
  public String getUserID() {
    return userID;
  }

  /**
   * @param userID1
   *          the userID to set
   */
  public void setUserID(String userID1) {
    this.userID = userID1;
  }

  /**
   * @return the likesCount
   */
  public int getLikesCount() {
    return likesCount;
  }

  /**
   * @param likesCount1
   *          the likesCount to set
   */
  public void setLikesCount(int likesCount1) {
    this.likesCount = likesCount1;
  }

  /**
   * @return the commentCount
   */
  public int getCommentCount() {
    return commentCount;
  }

  /**
   * @param commentCount1
   *          the commentCount to set
   */
  public void setCommentCount(int commentCount1) {
    this.commentCount = commentCount1;
  }

  /**
   * @return the post url
   */
  public URL getUrl() {
    return url;
  }

  /**
   * @param url1
   *          the post url
   */
  public void setUrl(URL url1) {
    this.url = url1;
  }

  /**
   * @return the post group
   */
  public String getGroup() {
    return group;
  }

  /**
   * @param group1
   *          the post group
   */
  public void setGroup(String group1) {
    this.group = group1;
  }

  /**
   * @return the post spamCount
   */
  public int getSpamCount() {
    return spamCount;
  }

  /**
   * @return increases the post spam counter by 1 and returns the result
   */
  public int increaseSpamCount() {
    this.spamCount = this.spamCount + 1;
    return this.spamCount;
  }

  /**
   * @return the post spamType
   */
  public String getSpamType() {
    return spamType;
  }

  /**
   * @param spamType1
   *          the post spamType
   */
  public void setSpamType(String spamType1) {
    this.spamType = spamType1;
  }

  /**
   * @return the post content
   */
  public String getContent() {
    return content;
  }

  /**
   * @param spamType1
   *          the post spamType
   */
  public void setContent(String content1) {
    this.content = content1;
  }

}
