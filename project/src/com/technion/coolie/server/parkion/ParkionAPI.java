/**
 * 
 */
package com.technion.coolie.server.parkion;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
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
  public ReturnCode addParkingLot(ParkingLot park) throws IOException {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.ADD_PARKING_LOT.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public ReturnCode removeParkingLot(ParkingLot park) throws IOException {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.REMOVE_PARKING_LOT.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public ParkingLot getParkingLot(ParkingLot park) throws JsonSyntaxException,
      IOException {
    return gson.fromJson(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.GET_PARKING_LOT.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)),
        ParkingLot.class);
  }

  @Override
  public ReturnCode setOccupancy(ParkingLot park) throws IOException {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.SET_OCCUPANCY.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public List<String> getIds() throws JsonSyntaxException, IOException {
    return gson.fromJson(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.GET_IDS.toString()), new TypeToken<List<String>>() {
      // default usage
    }.getType());

  }

  @Override
  public ReturnCode userParkedInLot(ParkingLot park) throws IOException {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.USER_PARKED_IN_LOT.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public ReturnCode userReportParkingLotBusy(ParkingLot park)
      throws IOException {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.USER_REPORT_PARKING_LOT_BUSY.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public ReturnCode userLeftParkingLot(ParkingLot park) throws IOException {
    return ReturnCode.valueOf(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.USER_LEFT_PARKING_LOT.toString(),
        ParkingLotEnum.PARKING_LOT.value(), gson.toJson(park)));
  }

  @Override
  public List<String> getFreeParkingLots() throws JsonSyntaxException,
      IOException {
    return gson.fromJson(Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.GET_FREE_PARKING_LOTS.toString()),
        new TypeToken<List<String>>() {
          // default usage
        }.getType());
  }

  @Override
  public Map<String, Integer> getPrecent() throws JsonSyntaxException,
      IOException {
    return gson.fromJson((Communicator.execute(
        ParkingLotEnum.PARK_SERVLET.value(), "function",
        ParkingLotEnum.GET_PRECENT.toString())),
        new TypeToken<Map<String, Integer>>() {
        }.getType());
  }

}
