package com.technion.coolie.server.ug.api;

import java.io.IOException;

import com.technion.coolie.server.ug.ReturnCodesUg;
import com.technion.coolie.ug.UGLoginObject;
import com.technion.coolie.ug.model.CourseKey;

/**
 * Created on 7.12.2013
 * 
 * @author DANIEL
 * 
 */
public interface IUgTracking {
  /**
   * Add the student to the tracking list of the course.
   * 
   * @param student
   *          the student
   * @param courseKey
   *          the course key
   * @return COURSE_DOES_NOT_EXSIST if the course doesn't exist,
   *         STUDENT_ALREADY_TRACKING if the student is tracking the course
   *         already, SUCCESS if the student was added, otherwise ERROR.
   * @throws IOException
   */
  public ReturnCodesUg addTrackingStudent(UGLoginObject student,
      CourseKey courseKey) throws IOException;

  /**
   * Remove the student from the tracking list of the course.
   * 
   * @param studentId
   *          the student's id
   * @param courseKey
   *          the course key (number + semester)
   * @return COURSE_DOES_NOT_EXSIST if the course doesn't exist,
   *         COURSE_DOES_NOT_ON_TRACK if the course isn't on tracking right now,
   *         STUDENT_NOT_TRACKING if the student isn't tracking on the course
   *         ,SUCCESS otherwise.
   * @throws IOException
   */
  public ReturnCodesUg removeTrackingStudentFromCourse(UGLoginObject student,
      CourseKey courseKey) throws IOException;
}
