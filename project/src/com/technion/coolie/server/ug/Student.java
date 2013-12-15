package com.technion.coolie.server.ug;

public class Student {

  String studentId;

  public Student() {
  }

  /**
   * @param studentId
   *          the studentId to set
   */
  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  /**
   * @return the studentId
   */
  public String getStudentId() {
    return studentId;
  }

  /**
   * @param studentId
   */
  public Student(String studentId) {
    super();
    this.studentId = studentId;
  }

}
