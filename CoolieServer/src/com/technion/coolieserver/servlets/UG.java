package com.technion.coolieserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.technion.coolieserver.framework.Authentication;
import com.technion.coolieserver.framework.OfyService;
import com.technion.coolieserver.ug.Course;
import com.technion.coolieserver.ug.UgManager;

public class UG extends HttpServlet {
  /**
   * 
   */
  private static final long serialVersionUID = -4628760673704447399L;

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String $ = null;
    if (!Authentication.checkAuth(req)) {
      resp.getWriter().print("ERROR");
      return;
    }
    Gson gson = new Gson();
    OfyService.ofy();
    String function = req.getParameter("function");
    if (function.equals("addCourse"))
      $ = UgManager.addCourse(
          gson.fromJson(req.getParameter("course"), Course.class)).value();

    else if (function.equals("findCourseByNumber"))
      $ = gson.toJson(UgManager.findCourseByNumber(req
          .getParameter("courseNumber")));
    resp.getWriter().print($);
  }
}