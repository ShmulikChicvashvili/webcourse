package com.technion.coolie.joinin.calander;


import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.TimeZone;

import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.subactivities.EventAttendFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.CalendarContract.Events;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

/**
 * Abstract implementation of calendar actions based on uri operations. Leaves
 * API issues to inheriting classes.
 * 
 * @author Alon Brifman
 * 
 */
public abstract class UriBasedActions implements CalendarActions {
  protected static final String[] EVENT_PROJECTION = new String[] { BaseColumns._ID, "name" };
  protected static final int PROJECTION_ID_INDEX = 0;
  protected static final int PROJECTION_NAME_INDEX = 1;
  protected static final String[] calendarTypes = { "com.google" };
  protected String BASE_CONTENT_URI;
  protected static final long HOUR_IN_MILLI = 60 * 60 * 1000;
  protected static final String CALENDAR_CONTENT = "/calendars";
  protected static final String EVENT_CONTENT = "/events";
  
  /**
   * @param c
   *          - the context of the call
   * @return a cursor to all the calendars.
   */
  protected Cursor getCalendars(final Context c) {
    return c.getContentResolver().query(Uri.parse(BASE_CONTENT_URI + CALENDAR_CONTENT), EVENT_PROJECTION, null, null, null);
  }
  
  @Override public void getCalendars(final Context c, final List<String> names, final List<Long> ids) {
    final Cursor cur = getCalendars(c);
    try {
      while (cur.moveToNext()) {
        if (names != null)
          names.add(cur.getString(PROJECTION_NAME_INDEX));
        if (ids != null)
          ids.add(Long.valueOf(cur.getLong(PROJECTION_ID_INDEX)));
      }
    } finally {
      cur.close();
    }
  }
  
  @Override public void setCalID(final String[] names, final List<Long> ids, final CalendarHandler ch,
      final CalendarHandler.Listener l, final FragmentActivity a) {
    new DialogFragment() {
      @Override public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setTitle("Please, choose a calendar for the event:");
        builder.setItems(names, new DialogInterface.OnClickListener() {
          @Override public void onClick(final DialogInterface dialog, final int item) {
            ch.setCalID(ids.get(item).longValue());
            l.onDone();
          }
        });
        return builder.create();
      }
      
      @Override public void onCancel(final DialogInterface dialog) {
        // If the user presses back set that no calendar was chosen.
        super.onCancel(dialog);
        ch.setCalID(CalendarHandler.NO_CAL);
        l.onDone();
      }
    }.show(a.getSupportFragmentManager(), "calChooser");
  }
  
  @Override public long addEvent(final Context c, final ClientEvent e, final long calID) {
    return Long.parseLong(c.getContentResolver().insert(Uri.parse(BASE_CONTENT_URI + EVENT_CONTENT), setEventValues(e, calID))
        .getLastPathSegment());
  }
  
  @Override public int updateEvent(final Context c, final ClientEvent e, final long calendarEventID) {
    return c.getContentResolver().update(ContentUris.withAppendedId(Uri.parse(BASE_CONTENT_URI + EVENT_CONTENT), calendarEventID),
        modifiedEventValues(e), null, null);
  }
  
  @Override public void deleteEvent(final Context c, final long calendarEventID) {
    c.getContentResolver().delete(ContentUris.withAppendedId(Uri.parse(BASE_CONTENT_URI + EVENT_CONTENT), calendarEventID), null,
        null);
  }
  
  @Override public boolean checkForCalendars(final Context c) {
    final Cursor cur = getCalendars(c);
    try {
      return cur.moveToNext();
    } finally {
      cur.close();
    }
  }
  
  /**
   * Whether this is the UriBasedActions class to use
   * 
   * @param c
   *          - the context of the call
   * @return whether the user is using the right subclass
   */
  public boolean isRightUri(final Context c) {
    return c.getContentResolver().query(Uri.parse(BASE_CONTENT_URI + CALENDAR_CONTENT), null, null, null, null) != null;
  }
  
  /**
   * Sets column values from the event including the calendar id.
   * 
   * @param e
   *          - the event
   * @return the values of the event in the calendar database.
   */
  @SuppressLint("InlinedApi") protected static ContentValues setEventValues(final ClientEvent e, final long calID) {
    final ContentValues $ = modifiedEventValues(e);
    $.put(Events.CALENDAR_ID, Long.valueOf(calID));
    return $;
  }
  
  /**
   * Sets column values from the event, excluding the calendar id.
   * 
   * @param e
   *          - the event
   * @return the values of the event in the calendar database.
   */
  @SuppressLint("InlinedApi") protected static ContentValues modifiedEventValues(final ClientEvent e) {
    final ContentValues $ = new ContentValues();
    $.put(Events.DTSTART, Long.valueOf(e.getWhen().toDate().getTime()));
    $.put(Events.DTEND, Long.valueOf(e.getWhen().toDate().getTime() + HOUR_IN_MILLI));
    $.put(Events.TITLE, e.getName());
    String url = "\n";
    try {
      url += EventAttendFragment.createShareUrl(e.getOwner(), e.getId());
    } catch (final UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }
    $.put(Events.DESCRIPTION, e.getDescription() + url);
    $.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
    $.put(Events.EVENT_LOCATION, e.getAddress());
    return $;
  }
}
