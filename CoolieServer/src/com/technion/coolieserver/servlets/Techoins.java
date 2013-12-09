package com.technion.coolieserver.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.technion.coolieserver.framework.Authentication;
import com.technion.coolieserver.techoins.BankManager;
import com.technion.coolieserver.techoins.appfiles.Action;
import com.technion.coolieserver.techoins.appfiles.Degree;
import com.technion.coolieserver.techoins.appfiles.ReturnCode;
import com.technion.coolieserver.techoins.appfiles.TechoinsFunctions;

public class Techoins extends HttpServlet {

  private static final long serialVersionUID = -3021844109675896805L;

  private static final Logger log = Logger.getLogger(Techoins.class.getName());

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    if (!Authentication.checkAuth(req))
      resp.getWriter().println(ReturnCode.NO_OAUTH);

    String function = req.getParameter("function");
    switch (TechoinsFunctions.valueOf(function)) {
    case ADD_ACCOUNT:
      resp.getWriter().println(
          BankManager.addStudent(req.getParameter("id"),
              req.getParameter("password"),
              Degree.valueOf(req.getParameter("degree"))));
      break;

    case REMOVE_ACCOUNT:
      resp.getWriter().println(
          BankManager.removeStudent(req.getParameter("id")));
      break;

    case GET_ACCOUNT:
      resp.getWriter().println(
          new Gson().toJson(BankManager.getAccount(req.getParameter("id"))));
      break;

    case MOVE_MONEY:
      resp.getWriter().println(
          BankManager.moveMoney(req.getParameter("fromID"),
              req.getParameter("toID"),
              Action.valueOf(req.getParameter("action")),
              Double.valueOf(req.getParameter("amount")).doubleValue(),
              new Gson().fromJson(req.getParameter("date"), Date.class)));
      break;

    case GET_HISTORY:
      resp.getWriter().println(
          new Gson().toJson(BankManager.getHistory(req.getParameter("id"))));
      break;

    default:
      resp.getWriter().println(ReturnCode.NO_SUCH_FUNCTION);
      break;

    }
  }
}
