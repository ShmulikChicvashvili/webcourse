package com.technion.coolie.server.webcourse.api;

import java.util.List;

import com.technion.coolie.server.webcourse.framework.CourseData;
import com.technion.coolie.server.webcourse.framework.StaffData;

public interface IWebcourseManager {
  public List<StaffData> getStaffInf(CourseData courseData);
}
