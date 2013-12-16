package com.technion.coolie.server.ug.courses;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.Communicator;
import com.technion.coolie.server.ug.api.IUgCourse;
import com.technion.coolie.server.ug.framework.CourseKey;
import com.technion.coolie.server.ug.framework.CourseServer;
import com.technion.coolie.server.ug.framework.Semester;
import com.technion.coolie.server.ug.framework.Student;

/**
 * Created on 8.12.2013
 * 
 * @author DANIEL
 * 
 */
public class UgCourse implements IUgCourse {

  private static final String servletName = "UGCourses";
  private static final String FUNCTION = "function";

  @Override
  public List<CourseServer> getAllCourses(Semester semester) {
    String $ = Communicator
        .execute(servletName, FUNCTION,
            UgCourseFunctions.GET_ALL_COURSES.value(), "semester",
            toJson(semester));

    return convertJsonToCoursesList($);
  }

  @Override
  public List<CourseServer> getCourses(List<CourseKey> courseKeys) {
    String $ = Communicator
        .execute(servletName, FUNCTION, UgCourseFunctions.GET_COURSES.value(),
            "courseKeys", toJson(courseKeys));

    return convertJsonToCoursesList($);
  }

  @Override
  public List<CourseServer> getStudentCurrentCourses(Student student,
      Semester semester) {
    String $ = Communicator.execute(servletName, FUNCTION,
        UgCourseFunctions.GET_STUDENT_COURSES.value(), "student",
        toJson(student), "semester", toJson(semester));

    return convertJsonToCoursesList($);
  }

  /**
   * 
   * @param jsonElement
   *          an object
   * @return json of the object
   */
  private String toJson(Object jsonElement) {
    return new Gson().toJson(jsonElement);
  }

  /**
   * Convert json to list
   * 
   * @param json
   *          the json string
   * @return list of courses
   */
  private List<CourseServer> convertJsonToCoursesList(String json) {
    return new Gson().fromJson(json, new TypeToken<List<CourseServer>>() {
    }.getType());
  }
}
