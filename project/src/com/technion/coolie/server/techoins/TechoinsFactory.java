package com.technion.coolie.server.techoins;

/**
 * 
 * Created on 16/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public class TechoinsFactory {

  public static ITechoinsAPI getTechoinsAPI() {
    return new TechoinsAPI();
  }

}
