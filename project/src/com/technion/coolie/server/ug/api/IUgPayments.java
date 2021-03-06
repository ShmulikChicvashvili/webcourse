package com.technion.coolie.server.ug.api;

import java.util.List;

import com.technion.coolie.ug.model.Payment;
import com.technion.coolie.ug.model.Student;

/**
 * Created on 7.12.2013
 * 
 * @author DANIEL
 * 
 */
public interface IUgPayments {
  /**
   * 
   * @param student
   *          the student
   * @return list of the student's payments
   */
  public List<Payment> getStudentPayments(Student student);
}
