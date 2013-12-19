package com.technion.coolie.joinin.directions;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * 
 * This class was taken from different locations on the web, and edited by Ido
 * Gonen
 * 
 * http://androidpallikoodam
 * .wordpress.com/2012/04/04/draw-google-driving-directions-in-mapview-overlay/
 * 
 * http://stackoverflow
 * .com/questions/14877878/drawing-a-route-between-2-locations
 * -google-maps-api-android-v2
 * 
 */
public class DrawDirections {
  /**
   * Gets the coordinates of 2 locations and the requested travel way to get
   * from one location to another, and returns an arrayList of GeoPoints which
   * represents the route between this 2 locations.
   * 
   * @param lat1
   *          the latitude of the first location
   * @param lon1
   *          the longitude of the first location
   * @param lat2
   *          the latitude of the second location
   * @param lon2
   *          the longitude of the second location
   * @param mode
   *          the requested travelWay (driving, walking etc).
   * @return ArrayList of GeoPoints which represents a route between these two
   *         locations.
   */
  public static ArrayList<LatLng> getDirections(final double lat1, final double lon1, final double lat2, final double lon2,
      final String mode) {
    final String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lon1 + "&destination=" + lat2
        + "," + lon2 + "&sensor=false&units=metric&mode=" + mode;
    Log.d("Directions URL", url);
    ArrayList<LatLng> $ = new ArrayList<LatLng>();
    final String json = JSONParser.getJSONFromUrl(url);
    try {
      final JSONObject jsonOb = new JSONObject(json);
      final JSONArray routeArray = jsonOb.getJSONArray("routes");
      final JSONObject routes = routeArray.getJSONObject(0);
      final JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
      final String encodedString = overviewPolylines.getString("points");
      $ = decodePoly(encodedString);
    } catch (final JSONException e) {
      e.printStackTrace();
    }
    return $;
  }
  
  /**
   * Returns an array list of GeoPoints representing the route returned by
   * google directions API in the given string "encoded".
   * 
   * @param encoded
   *          - String representing google directions answer.
   * @return array list of LatLng representing the route given in the encoded
   *         string
   */
  private static ArrayList<LatLng> decodePoly(final String encoded) {
    final ArrayList<LatLng> $ = new ArrayList<LatLng>();
    int index = 0;
    int lat = 0, lng = 0;
    while (index < encoded.length()) {
      int b, shift = 0, result = 0;
      do {
        b = encoded.charAt(index++) - 63;
        result |= (b & 0x1f) << shift;
        shift += 5;
      } while (b >= 0x20);
      lat += (result & 1) != 0 ? ~(result >> 1) : result >> 1;
      shift = 0;
      result = 0;
      do {
        b = encoded.charAt(index++) - 63;
        result |= (b & 0x1f) << shift;
        shift += 5;
      } while (b >= 0x20);
      lng += (result & 1) != 0 ? ~(result >> 1) : result >> 1;
      final LatLng p = new LatLng(lat / 1E5, lng / 1E5);
      $.add(p);
    }
    return $;
  }
}
