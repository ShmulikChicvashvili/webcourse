package com.technion.coolie.joinin.calander;


import java.util.List;

import com.technion.coolie.joinin.data.ClientEvent;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

/**
 * An interface for actions that are performed on calendars.
 * 
 * @author Alon Brifman
 * 
 */
public interface CalendarActions {
  /**
   * Set a list of all the available calendars
   * 
   * @param c
   *          - the context of the call
   * @param names
   *          - a list to which the names of the calendars will be added
   * @param ids
   *          - a list to which the ids of the calendars will be added
   *          respectively.
   */
  public void getCalendars(final Context c, List<String> names, List<Long> ids);
  
  /**
   * sets the calID field in CalendarHandler
   * 
   * @param names
   *          - the names of the optional calendars
   * @param ids
   *          - the corresponding ids of the calendars
   * @param ch
   *          - a CalendarHandler whose calID is to be set
   * @param l
   *          - a listener to perform after a calendar is chosen.
   * @param a
   *          - the calling activity.
   */
  public void setCalID(final String[] names, final List<Long> ids, final CalendarHandler ch, final CalendarHandler.Listener l,
      final FragmentActivity a);
  
  /**
   * Adds an event to a calendar
   * 
   * @param c
   *          - the context of the call
   * @param e
   *          - The event to add
   * @param calID
   *          - The id of the calendar to which the event should be added
   * @return an identifier to the added event in the calendar
   */
  public long addEvent(final Context c, final ClientEvent e, final long calID);
  
  /**
   * Updates an event in the calendar
   * 
   * @param c
   *          - the context of the call
   * @param e
   *          - the new event data
   * @param calendarEventID
   *          - the identifier given when the original event was inserted
   * @return the number of changes made to events
   */
  public int updateEvent(final Context c, final ClientEvent e, final long calendarEventID);
  
  /**
   * Deletes an event in the calendar
   * 
   * @param c
   *          - the context of the call
   * @param calendarEventID
   *          - the identifier given when the original event was inserted
   */
  public void deleteEvent(final Context c, final long calendarEventID);
  
  /**
   * Check whether calendars exist
   * 
   * @param c
   *          - the context of the call
   * @return whether calendars exist.
   */
  public boolean checkForCalendars(Context c);
}
