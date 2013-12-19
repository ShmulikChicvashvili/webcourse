package com.technion.coolie.server.webcourse.framework;

public class Lession {

  public enum Days {
    Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday,
  }

  Lession() {

  }

  private Days mDay;
  private LessionTime mStartTime;
  private LessionTime mEndTime;
  private String mPlace;

  public Lession(Days day, LessionTime startTime, LessionTime endTime,
      String place) {
    mDay = day;
    mStartTime = startTime;
    mEndTime = endTime;
    mPlace = place;
  }

  public Days GetDay() {
    return mDay;
  }

  public LessionTime GetStartTime() {
    return mStartTime;
  }

  public LessionTime GetEndTime() {
    return mEndTime;
  }

  public String GetPlace() {
    return mPlace;
  }

  @Override
  public String toString() {
    return (mDay.toString() + " " + mStartTime.toString() + "-"
        + mEndTime.toString() + " ");
  }
}
