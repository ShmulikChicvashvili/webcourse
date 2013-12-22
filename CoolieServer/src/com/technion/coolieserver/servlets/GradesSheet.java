package com.technion.coolieserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.technion.coolieserver.framework.Authentication;
import com.technion.coolieserver.support_communication.TechnionCommunication;

public class GradesSheet extends HttpServlet {
  /**
   * 
   */
  private static final long serialVersionUID = 1768033561088734339L;

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    if (!Authentication.checkAuth(req))
      resp.getWriter().println("ERROR");
    resp.getWriter().println(
        new TechnionCommunication().getGradesSheet(req.getParameter("id"),
            req.getParameter("password")));
  }
}