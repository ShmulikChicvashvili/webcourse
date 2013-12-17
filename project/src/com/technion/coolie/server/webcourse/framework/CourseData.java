package com.technion.coolie.server.webcourse.framework;

public class CourseData {

  String CourseName;
  String CourseDescription;

  CourseData() {

  }

  public CourseData(String name, String desc) {
    CourseName = name;
    CourseDescription = desc;
  }

  public String getName() {
    return CourseName;
  }

  public String getDesc() {
    return CourseDescription;
  }

}
