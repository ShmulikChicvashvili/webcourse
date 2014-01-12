package com.technion.coolie.server.ug.api;

import java.io.IOException;
import java.util.List;

import com.technion.coolie.ug.model.AcademicCalendarEvent;

/**
 * Created on 7.12.2013
 * 
 * @author DANIEL
 * 
 */
public interface IUgEvent {
  /**
   * 
   * @return list of academic calendar event
   * @throws IOException
   */
  public List<AcademicCalendarEvent> getAllAcademicEvents() throws IOException;
}
