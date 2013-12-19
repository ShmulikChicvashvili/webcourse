package com.technion.coolie.joinin.facebook;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;

/**
 * Class for parsing Facebook date string into long.
 * 
 * @author Ido
 * 
 */
@SuppressLint("SimpleDateFormat")
public class FacebookTimeConversion {
  private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
  private static DateFormat dateAndTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
  
  /**
   * Converts Facebook string date into long.
   * 
   * @param time
   *          - Facebook date and time representation
   * @return - long representing the given string date.
   */
  public static Long timeConversion(final String time) {
    long $ = 0;
    try {
      $ = dateAndTime.parse(time).getTime();
    } catch (final ParseException e) {
      try {
        $ = date.parse(time).getTime();
      } catch (final ParseException e1) {
        $ = 0;
      }
    }
    return Long.valueOf($);
  }
}
