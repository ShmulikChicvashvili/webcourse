package com.technion.coolie.joinin.data;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Shimon Kama
 * 
 */
public class EventDate implements Comparable<EventDate> {
  private final Calendar cal;
  
  /**
   * Default constructor. Set the date of today.
   */
  public EventDate() {
    cal = Calendar.getInstance();
  }
  
  /**
   * Creates a new date
   * 
   * @param year
   * @param month
   * @param day
   */
  public EventDate(final int year, final int month, final int day) {
    cal = Calendar.getInstance();
    cal.set(year, month - 1, day, 0, 0, 0);
  }
  
  /**
   * Creates a new date
   * 
   * @param year
   * @param month
   * @param day
   * @param hour
   *          24 hours format.
   * @param minute
   */
  public EventDate(final int year, final int month, final int day, final int hour, final int minute) {
    cal = Calendar.getInstance();
    cal.set(year, month - 1, day, hour, minute, 0);
  }
  
  /**
   * 
   * @param time
   *          the time as the number of milliseconds since Jan. 1, 1970.
   */
  public EventDate(final long time) {
    cal = Calendar.getInstance();
    cal.setTimeInMillis(time);
  }
  
  /**
   * 
   * @param date
   *          the date as java Date type
   */
  public EventDate(final Date date) {
    cal = Calendar.getInstance();
    cal.setTime(date);
  }
  
  /**
   * 
   * @return the year
   */
  public int getYear() {
    return cal.get(Calendar.YEAR);
  }
  
  /**
   * 
   * @return the month
   */
  public int getMonth() {
    return cal.get(Calendar.MONTH) + 1;
  }
  
  /**
   * 
   * @return the day
   */
  public int getDay() {
    return cal.get(Calendar.DAY_OF_MONTH);
  }
  
  /**
   * 
   * @return the hour (24 format)
   */
  public int getHour() {
    return cal.get(Calendar.HOUR_OF_DAY);
  }
  
  /**
   * 
   * @return the minute
   */
  public int getMinute() {
    return cal.get(Calendar.MINUTE);
  }
  
  /**
   * 
   * @return the time as the number of milliseconds since Jan. 1, 1970.
   */
  public long getTime() {
    return cal.getTimeInMillis();
  }
  
  /**
   * Sets the year of the date
   * 
   * @param year
   *          the year
   */
  public void setYear(final int year) {
    cal.set(Calendar.YEAR, year);
  }
  
  /**
   * Sets the month of the date
   * 
   * @param month
   *          the month
   */
  public void setMonth(final int month) {
    cal.set(Calendar.MONTH, month - 1);
  }
  
  /**
   * Sets the day of the date
   * 
   * @param day
   *          the day
   */
  public void setDay(final int day) {
    cal.set(Calendar.DAY_OF_MONTH, day);
  }
  
  /**
   * Sets the hour of the date
   * 
   * @param hour
   *          224-format hour
   */
  public void setHour(final int hour) {
    cal.set(Calendar.HOUR_OF_DAY, hour);
  }
  
  /**
   * Sets the minute of the date
   * 
   * @param minute
   *          the minute
   */
  public void setMinute(final int minute) {
    cal.set(Calendar.MINUTE, minute);
  }
  
  /**
   * 
   * @param time
   *          the time as the number of milliseconds since Jan. 1, 1970.
   */
  public void setTime(final long time) {
    cal.setTimeInMillis(time);
  }
  
  /**
   * 
   * @return the date as java Date format
   */
  public Date toDate() {
    return cal.getTime();
  }
  
  /**
   * returns the format dd/mm/yyyy
   */
  @SuppressWarnings("boxing") @Override public String toString() {
    return new StringBuilder().append(String.format("%02d", getDay())).append("/").append(String.format("%02d", getMonth()))
        .append("/").append(String.format("%04d", getYear())).toString();
  }
  
  /**
   * 
   * @return HH:MM
   */
  @SuppressWarnings("boxing") public String printTime() {
    return new StringBuilder().append(String.format("%02d", getHour())).append(':').append(String.format("%02d", getMinute()))
        .toString();
  }
  
  /**
   * 
   * @return DD/MM
   */
  public String printShortDate() {
    return new StringBuilder().append(String.format("%02d", Integer.valueOf(getDay()))).append("/")
        .append(String.format("%02d", Integer.valueOf(getMonth()))).toString();
  }
  
  @Override public boolean equals(final Object o) {
    if (o == null || o.getClass() != getClass())
      return false;
    if (o == this)
      return true;
    return cal.equals(((EventDate) o).cal);
  }
  
  @Override public int hashCode() {
    return cal.hashCode();
  }
  
  /**
   * Compares between two dates.
   * 
   * @param d
   *          The date to be compared.
   * @return the value 0 if the argument EventDate is equal to this EventDate; a
   *         value less than 0 if this EventDate is before the EventDate
   *         argument; and a value greater than 0 if this EventDate is after the
   *         EventDate argument
   */
  @Override public int compareTo(final EventDate d) {
    return cal.compareTo(d.cal);
  }
}
