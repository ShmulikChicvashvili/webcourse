package com.technion.coolie.server.ug.api;

import com.technion.coolie.server.ug.courses.UgCourse;
import com.technion.coolie.server.ug.event.UgEvent;
import com.technion.coolie.server.ug.exam.UgExam;
import com.technion.coolie.server.ug.gradesheet.UgGradeSheet;
import com.technion.coolie.server.ug.payments.UgPayments;
import com.technion.coolie.server.ug.tracking.UgTracking;

/**
 * Created on 7.12.2013
 * 
 * @author DANIEL
 * 
 */
public class UgFactory {
  /**
   * 
   * @return instance of UgGradeSheet thats implements IUgGradeSheet
   */
  public static IUgGradeSheet getUgGradeSheet() {
    return new UgGradeSheet();
  }

  /**
   * 
   * @return instance of UgExam thats implements IUgExam
   */
  public static IUgExam getUgExam() {
    return new UgExam();
  }

  /**
   * 
   * @return instance of UgPayments thats implements IUgPayments
   */
  public static IUgPayments getUgPayments() {
    return new UgPayments();
  }

  /**
   * 
   * @return instance of UgEvent thats implements IUgEvent
   */
  public static IUgEvent getUgEvent() {
    return new UgEvent();
  }

  /**
   * 
   * @return instance of UgCourse thats implements IUgCourse
   */
  public static IUgCourse getUgCourse() {
    return new UgCourse();
  }

  /**
   * 
   * @return instance of UgTracking thats implements IUgTracking
   */
  public static IUgTracking getUgTracking() {
    return new UgTracking();
  }
}
