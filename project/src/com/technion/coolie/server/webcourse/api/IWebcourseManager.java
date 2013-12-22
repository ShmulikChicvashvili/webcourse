package com.technion.coolie.server.webcourse.api;

import java.util.List;

import com.technion.coolie.webcourse.gr_plusplus.AnnouncementsData;
import com.technion.coolie.webcourse.gr_plusplus.AssignmentData;
import com.technion.coolie.webcourse.gr_plusplus.CourseData;
import com.technion.coolie.webcourse.gr_plusplus.StaffData;

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
   */
  public List<StaffData> getStaffInfo(CourseData courseData);

  public List<AnnouncementsData> getAnnouncement(CourseData course);

  public List<AssignmentData> getAssignment(CourseData course);
}
