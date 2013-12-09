package com.technion.coolieserver.ug;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Embed;

@Embed
public class Meeting implements Serializable {
  private String lecturerName;
  private DayOfWeek day; // just a string
  private Date startingHour;
  private Date endingHour;
  private String place;

  /**
   * @param lecturerName_
   * @param day_
   * @param startingHour_
   * @param endingHour_
   * @param place_
   */
  public Meeting(String lecturerName_, DayOfWeek day_, Date startingHour_,
      Date endingHour_, String place_) {
    lecturerName = lecturerName_;
    day = day_;
    startingHour = startingHour_;
    endingHour = endingHour_;
    place = place_;
  }

  Meeting() {

  }

  /**
   * @return the lecturerName
   */
  public String lecturerName() {
    return lecturerName;
  }

  /**
   * @param lecturerName_
   *          the lecturerName to set
   */
  public void setLecturerName(String lecturerName_) {
    lecturerName = lecturerName_;
  }

  /**
   * @return the day
   */
  public DayOfWeek day() {
    return day;
  }

  /**
   * @param day_
   *          the day to set
   */
  public void setDay(DayOfWeek day_) {
    day = day_;
  }

  /**
   * @return the startingHour
   */
  public Date startingHour() {
    return startingHour;
  }

  /**
   * @param startingHour_
   *          the startingHour to set
   */
  public void setStartingHour(Date startingHour_) {
    startingHour = startingHour_;
  }

  /**
   * @return the endingHour
   */
  public Date endingHour() {
    return endingHour;
  }

  /**
   * @param endingHour_
   *          the endingHour to set
   */
  public void setEndingHour(Date endingHour_) {
    endingHour = endingHour_;
  }

  /**
   * @return the place
   */
  public String place() {
    return place;
  }

  /**
   * @param place_
   *          the place to set
   */
  public void setPlace(String place_) {
    place = place_;
  }

}
