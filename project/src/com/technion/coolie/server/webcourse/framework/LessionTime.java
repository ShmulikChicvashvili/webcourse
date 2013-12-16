package com.technion.coolie.server.webcourse.framework;

public class LessionTime {

  private int mHours;
  private int mMinutes;

  public LessionTime() {
    mHours = 0;
    mMinutes = 0;
  }

  public LessionTime(int hours, int minutes) {
    mHours = hours;
    mMinutes = minutes;
  }

  public int getHours() {
    return mHours;
  }

  public int getMinutes() {
    return mMinutes;
  }

  @Override
  public String toString() {
    return (String.valueOf(mHours) + ":" + String.valueOf(mMinutes));
  }
}
