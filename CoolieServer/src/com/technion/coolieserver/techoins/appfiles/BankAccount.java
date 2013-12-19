package com.technion.coolieserver.techoins.appfiles;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.ValueIf;

@Entity
public class BankAccount {

  @Id
  String studentID;
  double credit;
  @Index(IfADMIN.class)
  static Degree degree;

  public static class IfADMIN extends ValueIf<String> {
    @Override
    public boolean matchesValue(String arg0) {
      return degree == Degree.ADMIN;
    }
  }

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