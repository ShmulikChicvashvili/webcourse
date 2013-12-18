package com.technion.coolie.server.webcourse.api;

import com.technion.coolie.server.webcourse.manager.WebcourseManager;

public class WebcourseFactory {

  public static IWebcourseManager getWebcourseManager() {
    return new WebcourseManager();
  }
}
