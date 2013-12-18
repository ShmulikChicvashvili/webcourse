package com.technion.coolieserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.technion.coolieserver.framework.Authentication;
import com.technion.coolieserver.teletech.ReturnCodeTeletech;
import com.technion.coolieserver.teletech.TeletechFunctions;
import com.technion.coolieserver.teletech.TeletechManager;

public class Teletech extends HttpServlet {
  /**
   * 
   */
  private static final long serialVersionUID = 5487793867572491524L;

  // private final TeletechManager teletech = new TeletechManager();

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    if (!Authentication.checkAuth(req))
      resp.getWriter().println(ReturnCodeTeletech.NO_OAUTH);
    Gson gson = new Gson();
    String $ = "";
    String function = req.getParameter("function");
    try {
      switch (TeletechFunctions.valueOf(function)) {
      case GET_ALL_CONTACTS:
        $ = gson.toJson(TeletechManager.getAllContacts());
        break;
      }
      resp.getWriter().print($);
    } catch (IllegalArgumentException e) {
      resp.getWriter().println(ReturnCodeTeletech.NO_SUCH_FUNCTION);
    }

  }
}