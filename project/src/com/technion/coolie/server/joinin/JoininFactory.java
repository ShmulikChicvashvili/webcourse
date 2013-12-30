package com.technion.coolie.server.joinin;

/**
 * 
 * Created on 25/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public class JoininFactory {

  public static IJoininAPI getJoininAPI() {
    return new JoininAPI();
  }

}
