package com.technion.coolie.server.ug.event;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.Communicator;
import com.technion.coolie.server.ug.api.IUgEvent;
import com.technion.coolie.server.ug.framework.AcademicCalendarEvent;

/**
 * Created on 8.12.2013
 * 
 * @author DANIEL
 * 
 */
public class UgEvent implements IUgEvent {
  private static final String servletName = "UGEvent";

  @Override
  public List<AcademicCalendarEvent> getAllAcademicEvents() {
    String $ = Communicator.execute(servletName);
    return new Gson().fromJson($, new TypeToken<List<AcademicCalendarEvent>>() {
    }.getType());
  }
}
