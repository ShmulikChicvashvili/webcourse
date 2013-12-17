package com.technion.coolieserver.parkion;

import static com.technion.coolieserver.framework.OfyService.ofy;

import com.technion.coolieserver.parkion.appfiles.ParkingLot;
import com.technion.coolieserver.techmine.appfiles.ReturnCode;

public class ParkingLotManager {

  public static ParkingLot getParkingLot(ParkingLot park) {
    return ofy().load().type(park.getClass()).id(park.getId()).now();
  }

  public static ReturnCode addParkingLot(ParkingLot park) {

    if (getParkingLot(park) != null)
      return ReturnCode.ENTITY_ALREADY_EXISTS;

    ofy().save().entity(park).now();
    return ReturnCode.SUCCESS;

  }

  public static ReturnCode removeParkingLot(ParkingLot park_) {

    ParkingLot park = getParkingLot(park_);
    if (park == null)
      return ReturnCode.ENTITY_NOT_EXISTS;

    ofy().delete().entity(park);
    return ReturnCode.SUCCESS;
  }

}
