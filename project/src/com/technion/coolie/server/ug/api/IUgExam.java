package com.technion.coolie.server.ug.api;

import java.io.IOException;
import java.util.List;

import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.UGLoginObject;

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
   * @throws IOException
   */
  public List<CourseItem> getStudentExams(UGLoginObject student,
      Semester semester) throws IOException;
}
