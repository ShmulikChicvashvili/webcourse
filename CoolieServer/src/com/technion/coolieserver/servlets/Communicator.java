package com.technion.coolieserver.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * Created on 07/11/2013
 * 
 * @author Daniel Abitbul <abitbul6@gmail.com>
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */

public class Communicator {

  private static final Logger log = Logger.getLogger(Communicator.class
      .getName());

  @SuppressWarnings("null")
  public static String execute(String... strs) {

    String $ = "";

    // try {
    // URL url = new URL("https://android.googleapis.com/gcm/send");
    // String param = "data.score=4x8&data.time=15:16.2342&registration_id="
    // + strs[2];
    // HttpURLConnection con = (HttpURLConnection) url.openConnection();
    // con.setDoOutput(true);
    // con.setRequestMethod("POST");
    // con.setFixedLengthStreamingMode(param.getBytes().length);
    // con.setRequestProperty("Content-Type",
    // "application/x-www-form-urlencoded;charset=UTF-8");
    // con.setRequestProperty("Authorization",
    // "key=AIzaSyCRCrex9v8F2lNB2fiUJg60giwn8qh32HM");
    // PrintWriter out = new PrintWriter(con.getOutputStream());
    // out.print(param);
    // out.close();
    // Scanner inStream = new Scanner(con.getInputStream());
    // while (inStream.hasNextLine())
    // $ += (inStream.nextLine());
    // inStream.close();
    // } catch (Exception e) {
    // e.printStackTrace();
    // log.severe("catch: " + e.toString());
    // }

    // Sending message to GCM for devices

    try {
      URL url = new URL("https://android.googleapis.com/gcm/send");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setDoOutput(true);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type",
          "application/json ; charset=utf-8");
      connection.setRequestProperty("Authorization",
          "key=AIzaSyCoK8xKlOlE7VC73Rih6UiytrWHpdFNslY");

      DatastoreService datastore = DatastoreServiceFactory
          .getDatastoreService();

      JSONObject obj = new JSONObject();
      JSONArray registrationsIDList = new JSONArray();

      Query newsQuery = new Query("device");
      List<Entity> results = datastore.prepare(newsQuery).asList(
          FetchOptions.Builder.withDefaults());
      if (results != null) {
        for (int j = 0; j < results.size(); j++) {
          Entity entity = results.get(j);
          if (entity != null) {
            String ID = (String) entity.getProperty("regID");
            if (ID != null) {
              registrationsIDList.put(ID);
            }
          }
        }
      }

      LinkedHashMap data = new LinkedHashMap();
      data.put("Subject", "masheu");

      try {
        obj.put("registration_ids", registrationsIDList);
        obj.put("data", data);
      } catch (JSONException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }

      OutputStream writer = connection.getOutputStream();
      Entity tmp = new Entity("JSON");
      tmp.setProperty("Message", obj.toString());
      datastore.put(tmp);
      writer.write(obj.toString().getBytes());
      writer.close();

      Entity device = new Entity("Messages Results");
      device.setProperty("Response Code", connection.getResponseCode());
      device.setProperty("Response Message", connection.getResponseMessage());
      datastore.put(device);
      $ = device.toString();
      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

      } else {
        // Server returned HTTP error code.
      }
    } catch (MalformedURLException ex) {
      // ...
    } catch (IOException ex) {
      // ...
    }

    // String json = "data.score=4x8&data.time=15:16.2342&registration_id="
    // + strs[2];
    // // log.severe(json);
    // HTTPResponse $ = null;
    // URL url;
    // try {
    // url = new URL("https://android.googleapis.com/gcm/send");
    // HTTPRequest request = new HTTPRequest(url, HTTPMethod.POST);
    // // request.addHeader(new HTTPHeader("Content-Type", "application/json"));
    // request.addHeader(new HTTPHeader("Authorization",
    // "key=AIzaSyCRCrex9v8F2lNB2fiUJg60giwn8qh32HM"));
    // request.setPayload(json.getBytes("UTF-8"));
    // $ = URLFetchServiceFactory.getURLFetchService().fetch(request);
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // log.severe("catch: " + e.toString());
    // }
    return $.toString();
  }

  private static String encodeParams(String[] strs)
      throws UnsupportedEncodingException {
    String $ = "";
    int len = strs.length;
    for (int i = 1; i < len; i += 2) {
      $ += strs[i] + "=" + URLEncoder.encode(strs[i + 1], "UTF-8");
      $ = len > i + 2 ? $ + "&" : $;
    }
    return $;
  }

}
