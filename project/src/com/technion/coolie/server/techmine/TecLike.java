package com.technion.coolie.server.techmine;

import java.util.Date;

public class TecLike implements IGetters {

  String id;
  Date date;
  int technionValue;
  int userID;

  /**
   * @param id1
   * @param date1
   * @param technionValue1
   * @param userID1
   */
  public TecLike(String id1, Date date1, int technionValue1, int userID1) {
    this.id = id1;
    this.date = date1;
    this.technionValue = technionValue1;
    this.userID = userID1;
  }

  TecLike() {
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
  public int getUserID() {
    return userID;
  }

  /**
   * @param userID1
   *          the userID to set
   */
  public void setUserID(int userID1) {
    this.userID = userID1;
  }

}
