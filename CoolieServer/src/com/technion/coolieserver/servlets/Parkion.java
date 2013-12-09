package com.technion.coolieserver.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.technion.coolieserver.framework.Authentication;
import com.technion.coolieserver.parkion.ParkingLotManager;
import com.technion.coolieserver.parkion.appfiles.ParkingLot;
import com.technion.coolieserver.parkion.appfiles.ParkingLotEnum;
import com.technion.coolieserver.parkion.appfiles.ReturnCode;

public class Parkion extends HttpServlet {

  private static final long serialVersionUID = 4494734685371179420L;

  private static final Logger log = Logger.getLogger(Parkion.class.getName());

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    if (!Authentication.checkAuth(req))
      resp.getWriter().println(ReturnCode.NO_OAUTH);

    String function = req.getParameter("function");
    Gson gson = new Gson();
    log.severe(function);
    switch (ParkingLotEnum.valueOf(function)) {
    case ADD_PARKING_LOT:
      resp.getWriter().println(
          ParkingLotManager.addParkingLot(gson.fromJson(
              req.getParameter(ParkingLotEnum.PARKING_LOT.value()),
              ParkingLot.class)));
      break;

    case REMOVE_PARKING_LOT:
      resp.getWriter().println(
          ParkingLotManager.removeParkingLot(gson.fromJson(
              req.getParameter(ParkingLotEnum.PARKING_LOT.value()),
              ParkingLot.class)));
      break;

    case GET_PARKING_LOT:
      resp.getWriter().println(
          new Gson().toJson(ParkingLotManager.getParkingLot(gson.fromJson(
              req.getParameter(ParkingLotEnum.PARKING_LOT.value()),
              ParkingLot.class))));
      break;
    case PARKING_LOT:
    case PARK_SERVLET:

    default:
      resp.getWriter().println(ReturnCode.NO_SUCH_FUNCTION);
      break;

    }
  }
}