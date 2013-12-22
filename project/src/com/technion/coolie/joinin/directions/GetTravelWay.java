package com.technion.coolie.joinin.directions;

/**
 * Interface for the enum TravelWay to Override
 * 
 * @author Ido Gonen
 * 
 */
public interface GetTravelWay {
  /**
   * Returns a string which represents the travel way matches to the enum that
   * overrides this function
   * 
   * @return string that represents the requested travel way.
   */
  public String getTravelWay();
}