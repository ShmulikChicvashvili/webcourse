package com.technion.coolie.server.ug.tracking;

import android.util.Log;

import com.google.gson.Gson;
import com.technion.coolie.server.Communicator;
import com.technion.coolie.server.ug.ReturnCodesUg;
import com.technion.coolie.server.ug.api.IUgTracking;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Student;

/**
 * Created on 8.12.2013
 * 
 * @author DANIEL
 * 
 */
public class UgTracking implements IUgTracking {

  private static final String servletName = "UGTrackingCourses";
  private static final String FUNCTION = "function";

  @Override
  public ReturnCodesUg addTrackingStudent(Student student, CourseKey courseKey) {
    String serverResult = Communicator.execute(servletName, FUNCTION,
        UgTrackingFunctions.ADD_TRACKING_STUDENT.value(), "student",
        toJson(student), "courseKey", toJson(courseKey));
    return ReturnCodesUg.valueOf(serverResult);
  }

  @Override
  public ReturnCodesUg removeTrackingStudentFromCourse(Student student,
      CourseKey courseKey) {
    Log.v("tagg", "fdsfdsfds");
    String serverResult = Communicator.execute(servletName, FUNCTION,
        UgTrackingFunctions.REMOVE_TRACKING_STUDENT_FROM_COURSE.value(),
        "student", toJson(student), "courseKey", toJson(courseKey));
    return ReturnCodesUg.valueOf(serverResult);
  }

  /**
   * 
   * @param jsonElement
   *          an object
   * @return json of the object
   */
  private String toJson(Object JsonElement) {
    return new Gson().toJson(JsonElement);
  }
}
