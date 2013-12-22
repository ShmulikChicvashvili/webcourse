package com.technion.coolie.joinin.map;

import android.app.Activity;
import android.graphics.drawable.Drawable;

/**
 * Interface for the enum EventType to override.
 * 
 * @author Ido Gonen
 * 
 */
public interface GetDrawable {
  /**
   * Returns the drawable matches to the enum that overrides this function
   * 
   * @param activity
   *          the activity related
   * @return the matching drawable
   */
  public Drawable getDrawable(final Activity activity);
}
