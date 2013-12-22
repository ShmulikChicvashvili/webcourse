package com.technion.coolie.server.webcourse.manager;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.Communicator;
import com.technion.coolie.server.webcourse.api.IWebcourseManager;
import com.technion.coolie.server.webcourse.framework.AnnouncementsData;
import com.technion.coolie.server.webcourse.framework.AssignmentData;
import com.technion.coolie.server.webcourse.framework.CourseData;
import com.technion.coolie.server.webcourse.framework.StaffData;

/**
 * Created on 15.12.2013
 * 
 * @author DANIEL
 * 
 */
public class WebcourseManager implements IWebcourseManager {
  private static final String servletName = "Webcourse";
  private static final String FUNCTION = "function";

  @Override
  public List<StaffData> getStaffInfo(CourseData courseData) {
    String $ = Communicator.execute(servletName, FUNCTION,
        WebcourseFunctions.GET_STAFF_INFO.value(), "courseData",
        toJson(courseData));
    return new Gson().fromJson($, new TypeToken<List<StaffData>>() {
    }.getType());
  }

  @Override
  public List<AnnouncementsData> getAnnouncement(CourseData courseData) {
    String $ = Communicator.execute(servletName, FUNCTION,
        WebcourseFunctions.GET_ANNOUNCEMENT.value(), "courseData",
        toJson(courseData));
    return new Gson().fromJson($, new TypeToken<List<AnnouncementsData>>() {
    }.getType());
  }

  @Override
  public List<AssignmentData> getAssignment(CourseData courseData) {
    String $ = Communicator.execute(servletName, FUNCTION,
        WebcourseFunctions.GET_ASSIGNMENT.value(), "courseData",
        toJson(courseData));
    return new Gson().fromJson($, new TypeToken<List<AssignmentData>>() {
    }.getType());
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

}
