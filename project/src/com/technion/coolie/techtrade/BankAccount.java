package com.technion.coolie.techtrade;

public class BankAccount {

  String studentID;
  double credit;
  static Degree degree;

  /**
   * @param studentID_
   * @param credit_
   * @param degree_
   */
  public BankAccount(String studentID_, double credit_, Degree degree_) {
    studentID = studentID_;
    credit = credit_;
    degree = degree_;
  }

  BankAccount() {

  }

  /**
   * @return the studentID
   */
  public String studentID() {
    return studentID;
  }

  /**
   * @param studentID
   *          the studentID to set
   */
  public void setId(String studentID_) {
    studentID = studentID_;
  }

  /**
   * @return the credit
   */
  public double credit() {
    return credit;
  }

  /**
   * @param credit_
   *          the credit to set
   */
  public void setCredit(double credit_) {
    credit = credit_;
  }

  /**
   * @return the degree
   */
  public static Degree degree() {
    return degree;
  }

  /**
   * @param degree_
   *          the degree to set
   */
  public static void setDegree(Degree degree_) {
    degree = degree_;
  }

}