package com.technion.coolie.joinin.calander;


import java.util.ArrayList;

import com.technion.coolie.joinin.calander.CalendarEventDatabase.NotFoundException;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.subactivities.SettingsActivity;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

/**
 * 
 * @author Alon Brifman
 * 
 *         handles calls to the calendar
 * 
 */
public class CalendarHandler {
  protected CalendarEventDatabase database;
  protected static CalendarActions ACTION_PERFORMER = null;
  protected static Object performerLock = new Object();
  protected long calID;
  protected static final long INVALID_ID = -1;
  public static final long NO_CAL = -2;
  
  /**
   * A constructor. On first call initializes uninitialized static fields.
   * 
   * @param c
   *          - The context of the caller
   */
  public CalendarHandler(final Context c) {
    super();
    database = new CalendarEventDatabase(c);
    calID = INVALID_ID;
    if (ACTION_PERFORMER == null)
      synchronized (performerLock) {
        if (ACTION_PERFORMER == null) // Double check locking
          setACTION_PERFORMER(c);
      }
  }
  
  protected CalendarHandler() {
    super();
  }
  
  /**
   * Set the chosen calendar's id
   * 
   * @param id
   *          - the chosen calendar's id
   */
  public void setCalID(final long id) {
    calID = id;
  }
  
  /**
   * Sets the ACTION_PERFORMER to the right API. (Depends on API level)
   * 
   * @param c
   *          - the context
   */
  private static void setACTION_PERFORMER(final Context c) {
    final UriBasedActions[] base_options = { new LowAPI(), new HighAPI() };
    for (final UriBasedActions opt : base_options)
      if (opt.isRightUri(c)) {
        ACTION_PERFORMER = opt;
        break;
      }
  }
  
  /**
   * Checks whether there are calendars available
   * 
   * @param c
   *          - the caller context
   * @return whether there are calendars available
   */
  public static boolean checkForCalendars(final Context c) {
    return ACTION_PERFORMER.checkForCalendars(c);
  }
  
  /**
   * Sets an event in the calendar. The user may need to choose a calendar and
   * hence a listener should be supplied to be called after operation is done
   * 
   * 
   * @param a
   *          - The callers context
   * @param e
   *          - The event to set
   * @param l
   *          - what to do after the event is set.
   */
  public void setNewEvent(final FragmentActivity a, final ClientEvent e, final Listener l) {
    chooseCalID(a, new Listener() {
      @Override public void onDone() {
        setNewEventAux(a, e);
        calID = INVALID_ID;
        l.onDone();
      }
    });
  }
  
  /**
   * Sets the calendar ID. Presents the user with a choice list of calendars.
   * 
   * @param a
   *          - the current context
   * @param l
   *          - a listener to perform after the user has chosen the calendar
   */
  private void chooseCalID(final FragmentActivity a, final Listener l) {
    final ArrayList<String> cals = new ArrayList<String>();
    final ArrayList<Long> ids = new ArrayList<Long>();
    ACTION_PERFORMER.getCalendars(a, cals, ids);
    if (!settingsEnable(a) || cals.isEmpty()) {
      calID = NO_CAL;
      l.onDone();
      return;
    }
    cals.add("Don't use a calendar"); // Add an option for no calendar
    ids.add(Long.valueOf(NO_CAL));
    final String[] opt = new String[cals.size()];
    cals.toArray(opt); // Set opt to be an array of the calendars
    ACTION_PERFORMER.setCalID(opt, ids, this, l, a);
  }
  
  @SuppressWarnings("static-method")// To be overridden
  protected boolean settingsEnable(final Context c) {
    return SettingsActivity.shouldUseCalendar(c);
  }
  
  /**
   * Updates an event in the calendar
   * 
   * @param c
   *          - The callers context
   * @param e
   *          - The event to set
   * @throws NotFoundException
   *           when the event isn't found in the database
   */
  public void updateEvent(final Context c, final ClientEvent e) throws NotFoundException {
    database.open();
    try {
      final long id = database.getCalanderEventID(e.getId());
      if (ACTION_PERFORMER.updateEvent(c, e, id) == 0) {
        database.deleteEventByCalendarID(id);
        throw new CalendarEventDatabase.NotFoundException("an event with server id: " + e.getId() + " was not found");
      }
    } finally {
      database.close();
    }
  }
  
  /**
   * Sets a new event in the calendar. Assumes that the calendar id was already
   * set. That is, the user has already chosen which calendar to use
   * 
   * @param a
   *          - the current context
   * @param e
   *          - the event to insert
   */
  void setNewEventAux(final FragmentActivity a, final ClientEvent e) {
    if (calID == NO_CAL)
      return;
    database.open();
    database.addEventID(e.getId(), ACTION_PERFORMER.addEvent(a, e, calID));
    database.close();
  }
  
  /**
   * delete an event from the calendar.
   * 
   * @param c
   *          - the current context
   * @param e
   *          - the event being deleted.
   */
  public void deleteEvent(final Context c, final ClientEvent e) {
    database.open();
    try {
      final long id = database.getCalanderEventID(e.getId());
      ACTION_PERFORMER.deleteEvent(c, id);
      database.deleteEventByCalendarID(id);
    } catch (final NotFoundException e1) {
      // The event was already deleted. Do nothing
    } finally {
      database.close();
    }
  }
  
  /**
   * 
   * @author Alon Brifman
   * 
   *         An interface for a callback
   * 
   */
  public interface Listener {
    public void onDone();
  }
}
