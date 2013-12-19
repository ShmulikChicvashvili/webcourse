package com.technion.coolie.joinin.calander;

/**
 * A class for uri based calendar operations for high API
 * 
 * @author Alon Brifman
 * 
 */
public class HighAPI extends UriBasedActions {
  public HighAPI() {
    super();
    BASE_CONTENT_URI = "content://com.android.calendar";
  }
}
