package com.technion.coolie.server.techmine;


public class TecWeeklyValue implements IGetters {

  Long id;
  String userId;
  int weeklyVal;

  /**
   * @param id
   * @param userId
   * @param weeklyVal
   */
  public TecWeeklyValue(Long id, String userId, int weeklyVal) {
    this.userId = userId;
    this.weeklyVal = 0;
  }

  /**
   * @return the id
   */
  @Override
  public String getId() {
    return id.toString();
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @param userId
   *          the userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * @return the weeklyVal
   */
  public int getWeeklyVal() {
    return weeklyVal;
  }

  /**
   * @param weeklyVal
   *          the weeklyVal to set
   */
  public void setWeeklyVal(int weeklyVal) {
    this.weeklyVal = weeklyVal;
  }

}
