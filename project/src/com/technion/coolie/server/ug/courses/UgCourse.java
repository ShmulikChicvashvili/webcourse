package com.technion.coolie.server.ug.courses;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.Communicator;
import com.technion.coolie.server.ug.api.IUgCourse;
import com.technion.coolie.ug.Server.ServerCourse;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;

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
  public List<ServerCourse> getAllCourses(Semester semester) throws IOException {
    String $ = Communicator.execute(servletName, FUNCTION,
        UgCourseFunctions.GET_COURSES_BY_SEMESTER.value(), "semester",
        toJson(semester));

    return convertJsonToCoursesList($);
  }

  @Override
  public List<ServerCourse> getCourses(List<CourseKey> courseKeys)
      throws IOException {
    String $ = Communicator.execute(servletName, FUNCTION,
        UgCourseFunctions.GET_COURSES_BY_KEY.value(), "courseKeys",
        toJson(courseKeys));

    return convertJsonToCoursesList($);
  }

  @Override
  public List<ServerCourse> getStudentCurrentCourses(Student student,
      Semester semester) throws IOException {
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
  private List<ServerCourse> convertJsonToCoursesList(String json) {
    return new Gson().fromJson(json, new TypeToken<List<ServerCourse>>() {
    }.getType());
  }
}
