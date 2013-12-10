package com.technion.coolieserver.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.technion.coolieserver.framework.Authentication;
import com.technion.coolieserver.teletech.ReturnCodeTeletech;
import com.technion.coolieserver.ug.framework.CourseKey;
import com.technion.coolieserver.ug.tracking.UgTrackingFunctions;
import com.technion.coolieserver.ug.tracking.UgTrackingManager;

public class UGTrackingCourses extends HttpServlet {

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
      switch (UgTrackingFunctions.valueOf(function)) {
      case ADD_TRACKING_STUDENT:
        $ = UgTrackingManager.addTrackingStudent(req.getParameter("studentId"),
            gson.fromJson(req.getParameter("courseKey"), CourseKey.class))
            .value();
        break;
      case GET_ALL_STUDENT_TRACKING_AFTER_COURSE:
        $ = gson.toJson(UgTrackingManager.getAllStudentTrackingAfterCourse(gson
            .fromJson(req.getParameter("courseKey"), CourseKey.class)));
        break;
      case REMOVE_TRACKING_STUDENT_FROM_COURSE:
        $ = UgTrackingManager.removeTrackingStudentFromCourse(
            req.getParameter("studentId"),
            gson.fromJson(req.getParameter("courseKey"), CourseKey.class))
            .value();
        break;
      }
      resp.getWriter().println($);
    } catch (IllegalArgumentException e) {
      resp.getWriter().println(ReturnCodeTeletech.NO_SUCH_FUNCTION);
    }
  }
}
