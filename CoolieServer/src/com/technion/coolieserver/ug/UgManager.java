package com.technion.coolieserver.ug;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

public class UgManager {

  public static ReturnCodes addCourse(Course c) {
    ofy().save().entity(c).now();
    return ReturnCodes.SUCCESS;
  }

  public static List<Course> findCourseByNumber(String courseNumber) {
    List<Course> courses = ofy().load().type(Course.class)
        .filter("courseNumber", courseNumber).list();
    return courses;

  }

  private static boolean chekcCourseNumber(String courseNumber) {
    int courseID = Integer.parseInt(courseNumber);
    return courseID < 1000000 && courseID >= 0 || true;
  }
}
