package com.technion.coolie.joinin.calander;

/**
 * A class for uri based calendar operations for low API.
 * 
 * @author Alon Brifman
 * 
 */
public class LowAPI extends UriBasedActions {
  public LowAPI() {
    super();
    BASE_CONTENT_URI = "content://calendar";
  }
}
