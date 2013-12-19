package com.technion.coolieserver.framework;

import javax.servlet.http.HttpServletRequest;

public class Authentication {

  private static final String Auth = "123";

  public static boolean checkAuth(HttpServletRequest req) {
    String auth = req.getHeader("Auth");
    if (auth != null && auth.equals(Auth))
      return true;
    return false;
  }
}
