package com.technion.coolie.server.gcm;

/**
 * 
 * Created on 26/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public class GcmFactory {

  public static IGcmAPI getGcmAPI() {
    return new GcmAPI();
  }

}
