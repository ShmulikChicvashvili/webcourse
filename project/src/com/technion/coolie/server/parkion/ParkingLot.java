package com.technion.coolie.server.parkion;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ParkingLot {
  private String id;
  private String name; // name of parking lot
  private int currentOccupancy = 0; // currentOccupency counter of used lots
  private int lotSize; // size of the parking lot
  private double busyThreshold; // busyThreshold - is an int number between 0
                                // and lotSize, which indicate in what state
                                // parking lot is busy
  private int[][] averageOccupancy; // map (day,hour) to average occupancy of
  // that time

  private double latitude;
  private double longitude;
  private double radius;

  /**
   * Constructs the ParkingLot
   * 
   * @param id1
   *          ID of parking lot.
   * 
   * @param name1
   *          name of parking lot.
   * 
   * @param latitude1
   *          The latitude coordinate of the parking lot.
   * 
   * @param longitude1
   *          The longitude coordinate of the parking lot.
   * 
   * @param radius1
   *          The radius in meters of the parking lot.
   * 
   * @param lotSize1
   *          The size of the parking lot.
   * 
   * 
   * @param averageOccupancy1
   *          A map of the pair <day,hours> as integers to an integers,
   *          indicating the average occupancy of the parking lot at that day
   *          and hour.
   */

  public ParkingLot(String id1, String name1, double latitude1,
      double longitude1, double radius1, int lotSize1, int[][] averageOccupancy1) {
    this.id = id1;
    this.name = name1;
    this.latitude = latitude1;
    this.longitude = longitude1;
    this.radius = radius1;
    this.lotSize = lotSize1;
    busyThreshold = Math.max(lotSize1
        - ParkingLotConstants.BUSY_THRESHOLD_MIN_SPOTS, lotSize1
        * ParkingLotConstants.BUSY_THRESHOLD_PERCENT);
    this.averageOccupancy = new int[7][24];
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j <= 23; j++) {
        this.averageOccupancy[i][j] = averageOccupancy1[i][j];
      }
    }

    Calendar calendar = new GregorianCalendar();
    TimeZone timeZone = TimeZone.getTimeZone("Israel");
    calendar.setTimeZone(timeZone);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

    double temp = averageOccupancy1[day][hour];

    // Preventing out of scope update to currentOccupancy
    if (temp < 0) {
      temp = 0;
    }
    if (temp > this.lotSize) {
      temp = this.lotSize;
    }
    currentOccupancy = (int) temp;

  }

  ParkingLot() {
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public double getRadius() {
    return radius;
  }

  /**
   * getName()
   * 
   * @return String the name of parking lot.
   * 
   */

  public String getName() {
    return name;
  }

  /**
   * userParkedInLot()
   * 
   * incrementing currentOccupency by 1/ParkingLotConstants.USAGE_PERCENT
   * 
   * @param this parking lot object.
   * 
   */

  public void userParkedInLot() {
    currentOccupancy = (int) Math.min(currentOccupancy + 1
        / ParkingLotConstants.USAGE_PERCENT, this.lotSize);
  }

  /**
   * userLeftParkingLot()
   * 
   * decrementing current occupancy by 1/ParkingLotConstants.USAGE_PERCENT
   * 
   * @param this parking lot object.
   * 
   */

  public void userLeftParkingLot() {
    currentOccupancy = (int) Math.max(currentOccupancy - 1
        / ParkingLotConstants.USAGE_PERCENT, 0);
  }

  /**
   * getStatus() return the status of the parking lot.
   * 
   * @return Free if current occupancy is bellow busy threshold, else Busy
   * 
   */

  public ParkingLotStatus getStatus() {
    if (currentOccupancy < busyThreshold) {
      return ParkingLotStatus.Free;
    }
    return ParkingLotStatus.Busy;
  }

  /**
   * getCurrentOccupancy() return the status of the parking lot.
   * 
   * @return Free if current occupancy is bellow busy threshold, else Busy
   * 
   */

  public int getCurrentOccupancy() {
    return currentOccupancy;
  }

  /**
   * userReportParkingLotBusy()
   * 
   * incrementing currentOccupency by lot Size * REPORT_BUSY_INCREMENT_FACTOR.
   * s*
   * 
   * @param this parking lot object.
   * 
   */
  public void userReportParkingLotBusy() {
    currentOccupancy = (int) Math.min(currentOccupancy + this.lotSize
        * ParkingLotConstants.REPORT_BUSY_INCREMENT_FACTOR, this.lotSize);
  }

  // apply a Decay function

  /**
   * decay()
   * 
   * the current occupancy will decay to the average occupancy of that parking
   * lot (at that hour and day) at the rate of DECAY_RATE each time the method
   * is call
   * 
   * 
   * @param this parking lot object.
   * 
   */

  public void decay() {
    // getting time and day at israel time zone
    Calendar calendar = new GregorianCalendar();
    TimeZone timeZone = TimeZone.getTimeZone("Israel");
    calendar.setTimeZone(timeZone);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    // Calendar.DAY_OF_WEEK return 1-7, since we are using an array we need to
    // reduce it by 1
    int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

    double temp = currentOccupancy * (1 - ParkingLotConstants.DECAY_RATE)
        + averageOccupancy[day][hour] * ParkingLotConstants.DECAY_RATE;

    // Preventing out of scope update to currentOccupancy
    if (temp < 0) {
      temp = 0;
    }
    if (temp > this.lotSize) {
      temp = this.lotSize;
    }
    currentOccupancy = (int) temp;
  }

  public String getId() {
    return id;
  }

  public void setOccupancy(int occupancy_) {
    currentOccupancy = occupancy_;
  }

  public int getParkingLotSize() {
    return lotSize;
  }

  public int getPrecent() {
    return (int) ((currentOccupancy * 100.0f) / lotSize);
  }

}
