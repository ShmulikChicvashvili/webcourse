package com.technion.coolie.teletech.api;

import com.technion.coolie.teletech.manager.Teletech;

/**
 * Created on 6.12.2013
 * 
 * @author DANIEL
 * 
 */
public class TeletechFactory {
  /**
   * 
   * @return instance of Teletech that implements ITeletech
   */
  public static ITeletech getTeletechAPI() {
    return new Teletech();
  }
}
