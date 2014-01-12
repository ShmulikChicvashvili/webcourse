package com.technion.coolie.techtrade;

import java.util.Date;

public class TechoinsTransfer {
  Long id;
  String fromID;
  String toID;
  Action action;
  double amount;
  Date date;

  /**
   * @param fromID_
   * @param toID_
   * @param action_
   * @param amount_
   * @param date_
   */
  public TechoinsTransfer(String fromID_, String toID_, Action action_,
      double amount_, Date date_) {
    fromID = fromID_;
    toID = toID_;
    action = action_;
    amount = amount_;
    date = date_;
  }

  TechoinsTransfer() {

  }

  /**
   * @return the fromID
   */
  public String fromID() {
    return fromID;
  }

  /**
   * @param fromID_
   *          the fromID to set
   */
  public void setFromID(String fromID_) {
    fromID = fromID_;
  }

  /**
   * @return the toID
   */
  public String toID() {
    return toID;
  }

  /**
   * @param toID_
   *          the toID to set
   */
  public void setToID(String toID_) {
    toID = toID_;
  }

  /**
   * @return the action
   */
  public Action action() {
    return action;
  }

  /**
   * @param action_
   *          the action to set
   */
  public void setAction(Action action_) {
    action = action_;
  }

  /**
   * @return the amount
   */
  public double amount() {
    return amount;
  }

  /**
   * @param amount_
   *          the amount to set
   */
  public void setAmount(double amount_) {
    amount = amount_;
  }

  /**
   * @return the date
   */
  public Date date() {
    return date;
  }

  /**
   * @param date_
   *          the date to set
   */
  public void setDate(Date date_) {
    date = date_;
  }
}
