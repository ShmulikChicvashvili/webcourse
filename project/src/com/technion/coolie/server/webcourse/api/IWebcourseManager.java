package com.technion.coolie.server.webcourse.api;

import java.io.IOException;
import java.util.List;

import com.technion.coolie.webcourse.AnnouncementsData;
import com.technion.coolie.webcourse.AssignmentData;
import com.technion.coolie.webcourse.CourseData;
import com.technion.coolie.webcourse.StaffData;

/**
 * Created on 15.12.2013
 * 
 * @author DANIEL
 * 
 */
public interface IWebcourseManager {
  /**
   * 
   * @param courseData
   *          the course's data
   * @return list of staff's data. All members in the list are teaching the
   *         course.
   * @throws IOException
   */
  public List<StaffData> getStaffInfo(CourseData courseData) throws IOException;

  public List<AnnouncementsData> getAnnouncement(CourseData course)
      throws IOException;

  public List<AssignmentData> getAssignment(CourseData course)
      throws IOException;
}
