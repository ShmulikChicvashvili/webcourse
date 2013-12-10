package com.technion.coolieserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.technion.coolieserver.framework.Authentication;

public class Teletech extends HttpServlet {
  /**
   * 
   */
  private static final long serialVersionUID = 5487793867572491524L;

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    if (!Authentication.checkAuth(req))
      resp.getWriter().println("ERROR");
    // Sender sender = new Sender("AIzaSyCoK8xKlOlE7VC73Rih6UiytrWHpdFNslY");
    // Message message = new Message.Builder().addData("myKey",
    // "myValue").build();
    // Result result = null;
    // try {
    // result = sender.send(message, req.getParameter("regID"), 5);
    // } catch (Exception e) {
    // resp.getWriter().println(e.toString());
    // }
    // if (result != null)
    // Log.debug("*****ERROR: " + result.getErrorCodeName());
    String regID = req.getParameter("regID");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity device = new Entity("device");
    device.setProperty("regID", regID);
    datastore.put(device);
    resp.getWriter()
        .println(Communicator.execute("", "registration_id", regID));

  }
}