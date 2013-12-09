package com.technion.coolieserver.parkion.appfiles;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class CampusParking {

  HashMap<String, ParkingLot> campusParkingLot;

  private String getValue(String type, Element firstElement) {
    NodeList firstNameList = firstElement.getElementsByTagName(type);
    Element firstNameElement = (Element) firstNameList.item(0);
    NodeList textFNList = firstNameElement.getChildNodes();
    return textFNList.item(0).getNodeValue().trim().toString();
  }

  // todo figure out input
  public CampusParking(String filepath) {
    try {
      HashMap<String, ParkingLot> campusParkingLot = new HashMap<String, ParkingLot>();
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
          .newInstance();
      DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
      Document doc = docBuilder.parse(new File(filepath));

      // normalize text representation
      doc.getDocumentElement().normalize();

      NodeList listOfParkingLots = doc.getElementsByTagName("parkinglot");
      int totalParkingLots = listOfParkingLots.getLength();
      System.out.println("Total no of ParkingLots : " + totalParkingLots);

      for (int i = 0; i < listOfParkingLots.getLength(); i++) {

        Node firstParkingLot = listOfParkingLots.item(i);
        if (firstParkingLot.getNodeType() == Node.ELEMENT_NODE) {

          Element firstElement = (Element) firstParkingLot;

          String id = firstElement.getAttribute("id");
          /*
           * Debug zone System.out.println("name : " +
           * getValue("name",firstElement)); System.out.println("lot_size : " +
           * getValue("lot_size",firstElement));
           * System.out.println("lot_average_work_hours : " +
           * getValue("lot_average_work_hours",firstElement));
           * System.out.println("lot_average_weekend_days : " +
           * getValue("lot_average_weekend_days",firstElement));
           * System.out.println("lot_average_off_hours : " +
           * getValue("lot_average_off_hours",firstElement));
           * System.out.println("latitude : " +
           * getValue("latitude",firstElement));
           * System.out.println("longitude : " +
           * getValue("longitude",firstElement)); System.out.println("radius : "
           * + getValue("radius",firstElement));
           */

          int[] occupancy = new int[7];

          // setting average occupancy per hour to parking lot

          for (int j = 0; j < 7; j++) {
            for (int k = 0; k < 24; k++) {
              if (j < 6) {
                if ((k >= 8) && (k < 17)) {
                  occupancy[j] = Integer.parseInt(getValue(
                      "lot_average_work_hours", firstElement));
                } else {
                  occupancy[j] = Integer.parseInt(getValue(
                      "lot_average_off_hours", firstElement));
                }
              } else {
                occupancy[j] = Integer.parseInt(getValue(
                    "lot_average_weekend_days", firstElement));
              }
            }
          }

          ParkingLot pl = new ParkingLot(firstElement.getAttribute("id"),
              getValue("lot_size", firstElement), Double.parseDouble(getValue(
                  "latitude", firstElement)), Double.parseDouble(getValue(
                  "longitude", firstElement)), Double.parseDouble(getValue(
                  "radius", firstElement)), Integer.parseInt(getValue(
                  "lot_size", firstElement)), occupancy);
          campusParkingLot.put(id, pl);

        }

      }// end of for loop with

    } catch (SAXParseException err) {
      System.out.println("** Parsing error" + ", line " + err.getLineNumber()
          + ", uri " + err.getSystemId());
      System.out.println(" " + err.getMessage());

    } catch (SAXException e) {
      Exception x = e.getException();
      ((x == null) ? e : x).printStackTrace();

    } catch (Throwable t) {
      t.printStackTrace();
    }

  }

  /**
   * decay()
   * 
   * the current occupancy will decay to the average occupancy all parking lots
   * 
   * @param this parking lot object.
   * 
   */
  // should run every 10 minutes
  public void decayCampus() {
    Iterator<Entry<String, ParkingLot>> it = campusParkingLot.entrySet()
        .iterator();
    while (it.hasNext()) {
      Map.Entry pairs = it.next();
      ParkingLot pl = (ParkingLot) pairs.getValue();
      pl.decay();

    }
  }

  public Set<String> getParkingLotsStatus() {
    HashSet<String> results = new HashSet<String>();
    Iterator<Entry<String, ParkingLot>> it = campusParkingLot.entrySet()
        .iterator();
    while (it.hasNext()) {
      Map.Entry pairs = it.next();
      if (((ParkingLot) pairs.getValue()).getStatus() == ParkingLotStatus.Free) {
        results.add((pairs.getKey().toString() + ":" + "Free"));
      } else {
        results.add((pairs.getKey().toString() + ":" + "Busy"));
      }
    }
    return results;
  }

  public void userParkedInLot(String id) {
    if (campusParkingLot.containsKey(id)) {
      campusParkingLot.get(id).userParkedInLot();
    }
  }

  public void userReportParkingLotBusy(String id) {
    if (campusParkingLot.containsKey(id)) {
      campusParkingLot.get(id).userReportParkingLotBusy();
    }
  }

  public void userLeftParkingLot(String id) {
    if (campusParkingLot.containsKey(id)) {
      campusParkingLot.get(id).userLeftParkingLot();
    }
  }

}
