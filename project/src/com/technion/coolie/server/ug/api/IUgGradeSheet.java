package com.technion.coolie.server.ug.api;

import java.util.List;

import com.technion.coolie.server.ug.framework.AccomplishedCourse;
import com.technion.coolie.server.ug.framework.Student;

/**
 * Created on 7.12.2013
 * 
 * @author DANIEL
 * 
 */
public interface IUgGradeSheet {
  /**
   * 
   * @param student
   *          the student
   * @return list of student's accomplished courses
   */
  public List<AccomplishedCourse> getMyGradesSheet(Student student);
}