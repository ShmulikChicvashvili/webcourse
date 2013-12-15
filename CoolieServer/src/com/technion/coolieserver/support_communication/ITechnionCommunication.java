package com.technion.coolieserver.support_communication;

import java.io.IOException;

import com.technion.coolieserver.ug.framework.CourseClient;

public interface ITechnionCommunication {

  public String getGradesSheet(String id, String password) throws IOException;

  public CourseClient getCourse(String courseNumber);
}
