/**
 * 
 */
package com.technion.coolieserver.teletech.appfiles;

import java.util.Date;

import com.googlecode.objectify.annotation.Embed;

/**
 * @author Argaman
 * 
 */
@Embed
public class OfficeHour {
  private Date day;
  private Date from;
  private Date to;

  public OfficeHour() {
    // this is so that this C'tor would compile.
    day = null;
    from = null;
    to = null;
  }

  /**
   * @param dayOfTheWeek
   *          MUST be in the format EEEE so that it would represent the full
   *          name of the day.
   * @param fromHour
   *          the hour that the reception hour starts.
   * @param toHour
   *          the end of the reception hour.
   */
  public OfficeHour(Date dayOfTheWeek, Date fromHour, Date toHour) {
    day = dayOfTheWeek;
    from = fromHour;
    to = toHour;
  }

  @Override
  public String toString() {
    if (day == null || from == null || to == null)
      return "TD";
    return day.toString() + from.toString() + "-" + to.toString();
  }

}
