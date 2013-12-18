package com.technion.coolie.server.webcourse.api;

import com.technion.coolie.server.webcourse.manager.WebcourseManager;

/**
 * Created on 15.12.2013
 * 
 * @author DANIEL
 * 
 */
public class WebcourseFactory {
  /**
   * 
   * @return an instance of WebcourseManager thats implements IWebcourseManager
   */
  public static IWebcourseManager getWebcourseManager() {
    return new WebcourseManager();
  }
}
