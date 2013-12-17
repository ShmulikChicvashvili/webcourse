package com.technion.coolie.server.ug.framework;

import java.util.Date;

public class Exam {

  private String courseNumber;
  private SemesterSeason ss;
  private int moed;
  private Date examDate;
  private String place;

  Exam() {

  }

  public Exam(String courseNumber, SemesterSeason ss, int moed, Date examDate,
      String place) {
    this.courseNumber = courseNumber;
    this.ss = ss;
    this.moed = moed;
    this.examDate = examDate;
    this.place = place;
  }

  public String getCourseNumber() {
    return courseNumber;
  }

  public void setCourseNumber(String courseNumber) {
    this.courseNumber = courseNumber;
  }

  public SemesterSeason getSs() {
    return ss;
  }

  public void setSs(SemesterSeason ss) {
    this.ss = ss;
  }

  public int getMoed() {
    return moed;
  }

  public void setMoed(int moed) {
    this.moed = moed;
  }

  public Date getExamDate() {
    return examDate;
  }

  public void setExamDate(Date examDate) {
    this.examDate = examDate;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }
}
