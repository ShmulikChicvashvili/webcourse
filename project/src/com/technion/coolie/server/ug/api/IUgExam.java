package com.technion.coolie.server.ug.api;

import java.util.List;

import com.technion.coolie.ug.model.Exam;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;

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
  public List<Exam> getStudentExams(Student student, Semester semester);
}
