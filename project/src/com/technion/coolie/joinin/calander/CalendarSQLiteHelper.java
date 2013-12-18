package com.technion.coolie.joinin.calander;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * A class for creating a database with columns: - COLUMN_SERVER_ID -
 * COLUMN_CALENDAR_ID
 * 
 * @author Alon Brifman
 * 
 */
public class CalendarSQLiteHelper extends SQLiteOpenHelper {
  protected String TABLE_EVENT_IDS = "eventIDs";
  public final String COLUMN_SERVER_ID = "s_id";
  public final String COLUMN_CALENDAR_ID = "c_id";
  private static final String DATABASE_NAME = "eventIDs.db";
  private static final int DATABASE_VERSION = 1;
  // Database creation sql statement
  protected String DATABASE_CREATE;
  
  public CalendarSQLiteHelper(final Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    DATABASE_CREATE = "create table " + TABLE_EVENT_IDS + "(" + COLUMN_SERVER_ID + " text not null, " + COLUMN_CALENDAR_ID
        + " text not null);";
  }
  
  protected CalendarSQLiteHelper(final Context context, final String database_name, final int database_version) {
    super(context, database_name, null, database_version);
  }
  
  @Override public void onCreate(final SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }
  
  @Override public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
    Log.w(CalendarSQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
        + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT_IDS);
    onCreate(db);
  }
  
  /**
   * Drop the database table
   * 
   * @param db
   *          - The database
   */
  public void clean(final SQLiteDatabase db) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT_IDS);
    onCreate(db);
  }
  
  /**
   * @return the name of the table
   */
  public String getTABLE_EVENT_IDS() {
    return TABLE_EVENT_IDS;
  }
}
