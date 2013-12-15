package com.technion.coolie.server.ug.api;

import java.util.List;

import com.technion.coolie.server.ug.Student;
import com.technion.coolie.server.ug.framework.Exam;

/**
 * Created on 7.12.2013
 * 
 * @author DANIEL
 * 
 */
public interface IUgExam {
  /**
   * 
   * @param student
   *          the student
   * @return list of the student's exams
   */
  public List<Exam> getStudentExams(Student student);
}
