package com.technion.coolieserver.parkion.appfiles;

public enum ParkingLotEnum {
  PARK_SERVLET("Parking"), PARKING_LOT("parkingLot"), ADD_PARKING_LOT(
      "addParkingLot"), REMOVE_PARKING_LOT("removeParkingLot"), GET_PARKING_LOT(
      "getParkingLot");

  private final String value;

  private ParkingLotEnum(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
