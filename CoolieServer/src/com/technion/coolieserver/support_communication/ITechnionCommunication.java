package com.technion.coolieserver.support_communication;

import java.io.IOException;

import com.technion.coolieserver.ug.Course;

public interface ITechnionCommunication {

  public String getGradesSheet(String id, String password) throws IOException;

  public Course getCourse(String courseNumber);
}
