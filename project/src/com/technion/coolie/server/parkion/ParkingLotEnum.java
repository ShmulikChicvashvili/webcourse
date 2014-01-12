package com.technion.coolie.server.parkion;

/**
 * 
 * Created on 9/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public enum ParkingLotEnum {
  PARK_SERVLET("Parkion"), PARKING_LOT("parkingLot"), ADD_PARKING_LOT(
      "addParkingLot"), REMOVE_PARKING_LOT("removeParkingLot"), GET_PARKING_LOT(
      "getParkingLot"), SET_OCCUPANCY("setOccupancy"), GET_IDS("getIds"), USER_PARKED_IN_LOT(
      "userParkedInLot"), USER_REPORT_PARKING_LOT_BUSY(
      "userReportParkingLotBusy"), USER_LEFT_PARKING_LOT("userLeftParkingLot"), GET_FREE_PARKING_LOTS(
      "getFreeParkingLots"), GET_PRECENT("getPrecent");

  private final String value;

  private ParkingLotEnum(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
