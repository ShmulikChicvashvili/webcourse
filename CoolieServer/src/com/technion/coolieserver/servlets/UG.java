package com.technion.coolieserver.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.technion.coolieserver.framework.Authentication;
import com.technion.coolieserver.teletech.ReturnCodeTeletech;
import com.technion.coolieserver.ug.courses.UgFunctions;
import com.technion.coolieserver.ug.courses.UgManager;
import com.technion.coolieserver.ug.framework.CourseClient;

public class UG extends HttpServlet {
  /**
   * 
   */
  private static final long serialVersionUID = -4628760673704447399L;
  private static final Logger log = Logger.getLogger(UG.class.getName());

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    if (!Authentication.checkAuth(req))
      resp.getWriter().println(ReturnCodeTeletech.NO_OAUTH);
    Gson gson = new Gson();
    String $ = "";

    String function = req.getParameter("function");

    try {
      switch (UgFunctions.valueOf(function)) {
      case ADD_COURSE:
        CourseClient courseClient = gson.fromJson(req.getParameter("course"),
            CourseClient.class);
        $ = UgManager.addCourse(courseClient).value();
        break;
      case FIND_COURSE_BY_NUMBER:
        $ = gson.toJson(UgManager.findCourseByNumber(req
            .getParameter("courseNumber")));
        log.severe($);
        break;
      case FIND_COURSE_BY_PREFIX_NAME:
        // TODO:
        break;
      }
      resp.getWriter().println($);
    } catch (IllegalArgumentException e) {
      resp.getWriter().println(ReturnCodeTeletech.NO_SUCH_FUNCTION);
    }
  }
}