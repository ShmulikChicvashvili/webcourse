package com.technion.coolie.server.parkion;

/**
 * 
 * Created on 16/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public class ParkionFactory {

  public static IParkionAPI getParkionAPI() {
    return new ParkionAPI();
  }

}
