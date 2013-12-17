package com.technion.coolie.tecmind.server;
/**
 * 
 * Created on 16/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public class TechmineFactory {

  public static ITechmineAPI getTechmineAPI() {
    return new TechmineAPI();
  }

}
