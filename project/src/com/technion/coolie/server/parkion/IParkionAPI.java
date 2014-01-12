package com.technion.coolie.server.parkion;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonSyntaxException;

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
   * @throws IOException
   */
  public ReturnCode addParkingLot(ParkingLot park) throws IOException;

  /**
   * 
   * @param park_
   *          - the object to remove with id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode removeParkingLot(ParkingLot park) throws IOException;

  /**
   * 
   * @param park
   *          - the object to get with the id field initialized correctly
   * @return - the requested object
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public ParkingLot getParkingLot(ParkingLot park) throws JsonSyntaxException,
      IOException;

  /**
   * 
   * @param park_
   *          - the object to set its occupancy with id field initialized
   *          correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode setOccupancy(ParkingLot park) throws IOException;

  /**
   * 
   * @return - List<String> as Json string of the ids of all the parking lots
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public List<String> getIds() throws JsonSyntaxException, IOException;

  /**
   * 
   * @return - List<String> as Json string of the ids of all the parking lots
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public Map<String, Integer> getPrecent() throws JsonSyntaxException,
      IOException;

  /**
   * @param park
   *          - the object to update with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode userParkedInLot(ParkingLot park) throws IOException;

  /**
   * @param park
   *          - the object to update with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode userReportParkingLotBusy(ParkingLot park)
      throws IOException;

  /**
   * @param park
   *          - the object to update with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode userLeftParkingLot(ParkingLot park) throws IOException;

  /**
   * @return - List<String> as Json string of the ids of the free parking lots
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public List<String> getFreeParkingLots() throws JsonSyntaxException,
      IOException;

}
