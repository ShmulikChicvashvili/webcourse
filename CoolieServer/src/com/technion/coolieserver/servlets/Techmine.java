package com.technion.coolieserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.technion.coolieserver.framework.Authentication;
import com.technion.coolieserver.techmine.MiningManager;
import com.technion.coolieserver.techmine.appfiles.ReturnCode;
import com.technion.coolieserver.techmine.appfiles.TecComment;
import com.technion.coolieserver.techmine.appfiles.TecLike;
import com.technion.coolieserver.techmine.appfiles.TecPost;
import com.technion.coolieserver.techmine.appfiles.TechmineClasses;
import com.technion.coolieserver.techmine.appfiles.TechmineFunctions;
import com.technion.coolieserver.techmine.appfiles.TopBestComment;
import com.technion.coolieserver.techmine.appfiles.TopBestPost;
import com.technion.coolieserver.techmine.appfiles.User;

public class Techmine extends HttpServlet {

  private static final long serialVersionUID = 4346011202605945653L;

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    if (!Authentication.checkAuth(req))
      resp.getWriter().println(ReturnCode.NO_OAUTH);

    String function = req.getParameter("function");
    Gson gson = new Gson();
    switch (TechmineFunctions.valueOf(function)) {
    case ADD_USER:
      resp.getWriter().println(
          MiningManager.addEntity(gson.fromJson(
              req.getParameter(TechmineClasses.USER.value()), User.class)));
      break;

    case REMOVE_USER:
      resp.getWriter().println(
          MiningManager.removeEntity(gson.fromJson(
              req.getParameter(TechmineClasses.USER.value()), User.class)));
      break;

    case GET_USER:
      resp.getWriter().println(
          new Gson().toJson(MiningManager.getEntity(gson.fromJson(
              req.getParameter(TechmineClasses.USER.value()), User.class))));
      break;

    case ADD_TOP_BEST_POST:
      resp.getWriter().println(
          MiningManager.addEntity(gson.fromJson(
              req.getParameter(TechmineClasses.TOP_BEST_POST.value()),
              TopBestPost.class)));
      break;

    case REMOVE_TOP_BEST_POST:
      resp.getWriter().println(
          MiningManager.removeEntity(gson.fromJson(
              req.getParameter(TechmineClasses.TOP_BEST_POST.value()),
              TopBestPost.class)));
      break;

    case GET_TOP_BEST_POST:
      resp.getWriter().println(
          new Gson().toJson(MiningManager.getEntity(gson.fromJson(
              req.getParameter(TechmineClasses.TOP_BEST_POST.value()),
              TopBestPost.class))));
      break;

    case ADD_TOP_BEST_COMMENT:
      resp.getWriter().println(
          MiningManager.addEntity(gson.fromJson(
              req.getParameter(TechmineClasses.TOP_BEST_COMMENT.value()),
              TopBestComment.class)));
      break;

    case REMOVE_TOP_BEST_COMMENT:
      resp.getWriter().println(
          MiningManager.removeEntity(gson.fromJson(
              req.getParameter(TechmineClasses.TOP_BEST_COMMENT.value()),
              TopBestComment.class)));
      break;

    case GET_TOP_BEST_COMMENT:
      resp.getWriter().println(
          new Gson().toJson(MiningManager.getEntity(gson.fromJson(
              req.getParameter(TechmineClasses.TOP_BEST_COMMENT.value()),
              TopBestComment.class))));
      break;

    case ADD_TEC_POST:
      resp.getWriter()
          .println(
              MiningManager.addEntity(gson.fromJson(
                  req.getParameter(TechmineClasses.TEC_POST.value()),
                  TecPost.class)));
      break;

    case REMOVE_TEC_POST:
      resp.getWriter()
          .println(
              MiningManager.removeEntity(gson.fromJson(
                  req.getParameter(TechmineClasses.TEC_POST.value()),
                  TecPost.class)));
      break;

    case GET_TEC_POST:
      resp.getWriter()
          .println(
              new Gson().toJson(MiningManager.getEntity(gson.fromJson(
                  req.getParameter(TechmineClasses.TEC_POST.value()),
                  TecPost.class))));
      break;

    case ADD_TEC_COMMENT:
      resp.getWriter().println(
          MiningManager.addEntity(gson.fromJson(
              req.getParameter(TechmineClasses.TEC_COMMENT.value()),
              TecComment.class)));
      break;

    case REMOVE_TEC_COMMENT:
      resp.getWriter().println(
          MiningManager.removeEntity(gson.fromJson(
              req.getParameter(TechmineClasses.TEC_COMMENT.value()),
              TecComment.class)));
      break;

    case GET_TEC_COMMENT:
      resp.getWriter().println(
          new Gson().toJson(MiningManager.getEntity(gson.fromJson(
              req.getParameter(TechmineClasses.TEC_COMMENT.value()),
              TecComment.class))));
      break;

    case ADD_TEC_LIKE:
      resp.getWriter()
          .println(
              MiningManager.addEntity(gson.fromJson(
                  req.getParameter(TechmineClasses.TEC_LIKE.value()),
                  TecLike.class)));
      break;

    case REMOVE_TEC_LIKE:
      resp.getWriter()
          .println(
              MiningManager.removeEntity(gson.fromJson(
                  req.getParameter(TechmineClasses.TEC_LIKE.value()),
                  TecLike.class)));
      break;

    case GET_TEC_LIKE:
      resp.getWriter()
          .println(
              new Gson().toJson(MiningManager.getEntity(gson.fromJson(
                  req.getParameter(TechmineClasses.TEC_LIKE.value()),
                  TecLike.class))));
      break;

    default:
      resp.getWriter().println(ReturnCode.NO_SUCH_FUNCTION);
      break;

    }
  }
}
