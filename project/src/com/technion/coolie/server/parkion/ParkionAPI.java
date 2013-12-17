/**
 * 
 */
package com.technion.coolie.server.parkion;

import java.util.List;

import com.google.gson.Gson;
import com.technion.coolie.server.Communicator;

/**
 * 
 * Created on 9/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public class ParkionAPI implements IParkionAPI {

  Gson gson = new Gson();
  String FUNC = "function";

  @Override
  public ReturnCode addParkingLot(ParkingLot park) {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.ADD_PARKING_LOT.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public ReturnCode removeParkingLot(ParkingLot park) {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.REMOVE_PARKING_LOT.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public ParkingLot getParkingLot(ParkingLot park) {
    return gson.fromJson(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.GET_PARKING_LOT.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)),
        ParkingLot.class);
  }

  @Override
  public ReturnCode setOccupancy(ParkingLot park) {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.SET_OCCUPANCY.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public List<String> getIds() {
    return gson.fromJson(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.GET_IDS.toString()), List.class);

  }

  @Override
  public ReturnCode userParkedInLot(ParkingLot park) {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.USER_PARKED_IN_LOT.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public ReturnCode userReportParkingLotBusy(ParkingLot park) {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.USER_REPORT_PARKING_LOT_BUSY.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public ReturnCode userLeftParkingLot(ParkingLot park) {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.USER_LEFT_PARKING_LOT.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public List<String> getFreeParkingLots() {
    return gson.fromJson(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.GET_FREE_PARKING_LOTS.toString()), List.class);
  }

}
