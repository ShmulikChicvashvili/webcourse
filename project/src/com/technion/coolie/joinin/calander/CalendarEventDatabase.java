package com.technion.coolie.joinin.calander;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * A class maintaining a database on the device, mapping from ids given to
 * events by the server to ids given to events by the calendar
 * 
 * @author Alon Brifman
 * 
 */
public class CalendarEventDatabase {
  protected SQLiteDatabase database;
  protected CalendarSQLiteHelper dbHelper;
  
  public CalendarEventDatabase(final Context context) {
    dbHelper = new CalendarSQLiteHelper(context);
  }
  
  protected CalendarEventDatabase() {
    // Do nothing
  }
  
  /**
   * Open database for operations
   * 
   * @throws SQLException
   */
  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }
  
  /**
   * Close database
   */
  public void close() {
    dbHelper.close();
  }
  
  /**
   * Add a row to the database
   * 
   * @param serverID
   *          - the id given to the event by the server
   * @param calanderID
   *          - the id given to the event by the calendar
   */
  public void addEventID(final long serverID, final long calanderID) {
    final ContentValues values = new ContentValues();
    values.put(dbHelper.COLUMN_SERVER_ID, "" + serverID);
    values.put(dbHelper.COLUMN_CALENDAR_ID, "" + calanderID);
    database.insert(dbHelper.getTABLE_EVENT_IDS(), null, values);
  }
  
  /**
   * Get the id given by the calendar that corresponds to the given id from the
   * server
   * 
   * @param serverID
   *          - the id given by the server
   * @return the corresponding id given by the calendar
   * @throws NotFoundException
   */
  public long getCalanderEventID(final long serverID) throws NotFoundException {
    final Cursor c = findEvent(serverID);
    try {
      if (!c.moveToNext())
        throw new NotFoundException("an event with server id: " + serverID + " was not found");
      return c.getLong(0);
    } finally {
      c.close();
    }
  }
  
  /**
   * Returns all the rows where the given server id appears. (On a consistent DB
   * there should be only one row)
   * 
   * @param serverID
   *          - an id given by the server
   * @return a cursor to all rows where the given id is the server id.
   */
  private Cursor findEvent(final long serverID) {
    final String[] selectArgs = { "" + serverID };
    final String[] cols = { dbHelper.COLUMN_CALENDAR_ID };
    return database.query(dbHelper.getTABLE_EVENT_IDS(), cols, "(" + dbHelper.COLUMN_SERVER_ID + " = ?)", selectArgs, null, null,
        null);
  }
  
  /**
   * Updates the calendar id
   * 
   * @param serverID
   *          - The id given by the server
   * @param updatedCalanderID
   *          - The modefied calendar id
   */
  public void updateEventID(final long serverID, final long updatedCalanderID) {
    database.delete(dbHelper.getTABLE_EVENT_IDS(), dbHelper.COLUMN_SERVER_ID + " = " + serverID, null);
    addEventID(serverID, updatedCalanderID);
  }
  
  /**
   * Checks whether an entry with the given server id exists
   * 
   * @param serverID
   *          - the server id of the event
   * @return whether such an entry exists
   */
  public boolean checkForEvent(final long serverID) {
    final Cursor c = findEvent(serverID);
    try {
      return c.moveToNext();
    } finally {
      c.close();
    }
  }
  
  /**
   * Delete the rows with a given calendar id.
   * 
   * @param calendarID
   *          - The calendar id to delete from the DB
   */
  public void deleteEventByCalendarID(final long calendarID) {
    database.delete(dbHelper.getTABLE_EVENT_IDS(), dbHelper.COLUMN_CALENDAR_ID + " = " + calendarID, null);
  }
  
  /**
   * Drop the table
   */
  public void cleanDB() {
    dbHelper.clean(database);
  }
  
  /**
   * An exception to throw when something is not found in the database
   * 
   * @author Alon Brifman
   * 
   */
  public static class NotFoundException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public NotFoundException(final String s) {
      super(s);
    }
  }
}
