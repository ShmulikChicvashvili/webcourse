package com.technion.coolie.joinin.directions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

/**
 * This class was taken from the web, and edited by Ido Gonen
 * http://stackoverflow
 * .com/questions/14877878/drawing-a-route-between-2-locations
 * -google-maps-api-android-v2
 */
public class JSONParser {
  static InputStream is = null;
  static JSONObject jObj = null;
  static String json = "";
  
  /**
   * C'tor
   */
  public JSONParser() {
  }
  
  /**
   * Returns the answer from google directions according to the query specified
   * in the given url address.
   * 
   * @param url
   *          - url of the request made to google directions API
   * @return String representing google directions answer.
   */
  public static String getJSONFromUrl(final String url) {
    // Making HTTP request
    try {
      // defaultHttpClient
      is = new DefaultHttpClient().execute(new HttpPost(url)).getEntity().getContent();
    } catch (final Exception e) {
      e.printStackTrace();
    }
    try {
      final BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
      final StringBuilder sb = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null)
        sb.append(line + "\n");
      json = sb.toString();
      is.close();
    } catch (final Exception e) {
      Log.e("Buffer Error", "Error converting result " + e.toString());
    }
    return json;
  }
}