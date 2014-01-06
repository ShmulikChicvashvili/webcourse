package com.technion.coolie.server.parkion;

import java.util.List;
import java.util.Map;

/**
 * 
 * Created on 9/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public interface IParkionAPI {

  /**
   * 
   * @param park
   *          - the object to add with all fields initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addParkingLot(ParkingLot park);

  /**
   * 
   * @param park_
   *          - the object to remove with id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode removeParkingLot(ParkingLot park);

  /**
   * 
   * @param park
   *          - the object to get with the id field initialized correctly
   * @return - the requested object
   */
  public ParkingLot getParkingLot(ParkingLot park);

  /**
   * 
   * @param park_
   *          - the object to set its occupancy with id field initialized
   *          correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode setOccupancy(ParkingLot park);

  /**
   * 
   * @return - List<String> as Json string of the ids of all the parking lots
   */
  public List<String> getIds();

  /**
   * 
   * @return - List<String> as Json string of the ids of all the parking lots
   */
  public Map<String, Integer> getPrecent();

  /**
   * @param park
   *          - the object to update with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode userParkedInLot(ParkingLot park);

  /**
   * @param park
   *          - the object to update with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode userReportParkingLotBusy(ParkingLot park);

  /**
   * @param park
   *          - the object to update with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode userLeftParkingLot(ParkingLot park);

  /**
   * @return - List<String> as Json string of the ids of the free parking lots
   */
  public List<String> getFreeParkingLots();

}
