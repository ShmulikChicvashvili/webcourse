package com.technion.coolieserver.ug.framework;

import java.io.Serializable;
import java.util.Date;

public class Meeting implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 3880934967869395104L;

  private String id;
  private String lecturerName;
  private DayOfWeek day;
  private Date startingHour;
  private Date endingHour;
  private String place;

  public Meeting(String id, String lecturerName, DayOfWeek day,
      Date startingHour, Date endingHour, String place) {
    super();
    this.id = id;
    this.lecturerName = lecturerName;
    this.day = day;
    this.startingHour = startingHour;
    this.endingHour = endingHour;
    this.place = place;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLecturerName() {
    return lecturerName;
  }

  public void setLecturerName(String lecturerName) {
    this.lecturerName = lecturerName;
  }

  public DayOfWeek getDay() {
    return day;
  }

  public void setDay(DayOfWeek day) {
    this.day = day;
  }

  public Date getStartingHour() {
    return startingHour;
  }

  public void setStartingHour(Date startingHour) {
    this.startingHour = startingHour;
  }

  public Date getEndingHour() {
    return endingHour;
  }

  public void setEndingHour(Date endingHour) {
    this.endingHour = endingHour;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

}
