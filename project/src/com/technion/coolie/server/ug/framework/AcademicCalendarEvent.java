package com.technion.coolie.server.ug.framework;

import java.util.Calendar;

public class AcademicCalendarEvent implements Comparable<AcademicCalendarEvent> {
  private DayOfWeek dayOfWeek;
  private Calendar startingDay;
  private String event;

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(DayOfWeek dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public Calendar getStartingDay() {
    return startingDay;
  }

  public void setStartingDay(Calendar startingDay) {
    this.startingDay = startingDay;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public AcademicCalendarEvent(DayOfWeek dayOfWeek, Calendar startingDay,
      String event) {
    this.dayOfWeek = dayOfWeek;
    this.startingDay = startingDay;
    this.event = event;
  }

  @Override
  public int compareTo(AcademicCalendarEvent another) {
    if (another == null || another.startingDay == null
        || this.startingDay == null)
      return 0;
    return this.startingDay.compareTo(another.startingDay);
  }

}
