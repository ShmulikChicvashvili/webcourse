package com.technion.coolie.server.ug.api;

import java.util.List;

import com.technion.coolie.server.ug.framework.CourseKey;
import com.technion.coolie.server.ug.framework.CourseServer;
import com.technion.coolie.server.ug.framework.Semester;
import com.technion.coolie.server.ug.framework.Student;

/**
 * Created on 7.12.2013
 * 
 * @author DANIEL
 * 
 */
public interface IUgCourse {

  /**
   * 
   * @param semester
   *          the semester
   * @return list of the courses from DB thats matching to the semester
   */
  public List<CourseServer> getAllCourses(Semester semester);

  /**
   * 
   * @param courseKeys
   *          list of courses key
   * @return list of the courses from DB thats matching to the courses keys
   */
  public List<CourseServer> getCourses(List<CourseKey> courseKeys);

  /**
   * 
   * @param student
   *          the student
   * @return list of the student's courses
   */
  public List<CourseServer> getStudentCurrentCourses(Student student,
      Semester semester);
}
