package com.technion.coolie.server.ug.api;

import java.io.IOException;
import java.util.List;

import com.technion.coolie.ug.Server.ServerCourse;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;

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
   * @throws IOException
   */
  public List<ServerCourse> getAllCourses(Semester semester) throws IOException;

  /**
   * 
   * @param courseKeys
   *          list of courses key
   * @return list of the courses from DB thats matching to the courses keys
   * @throws IOException
   */
  public List<ServerCourse> getCourses(List<CourseKey> courseKeys)
      throws IOException;

  /**
   * 
   * @param student
   *          the student
   * @return list of the student's courses
   * @throws IOException
   */
  public List<ServerCourse> getStudentCurrentCourses(Student student,
      Semester semester) throws IOException;
}
