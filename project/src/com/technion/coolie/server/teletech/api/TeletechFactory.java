package com.technion.coolie.server.teletech.api;

import com.technion.coolie.server.teletech.manager.Teletech;

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
  public static ITeletech getTeletech() {
    return new Teletech();
  }
}
