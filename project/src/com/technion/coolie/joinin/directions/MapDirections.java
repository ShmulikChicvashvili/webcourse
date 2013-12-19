package com.technion.coolie.joinin.directions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.technion.coolie.joinin.data.ClientEvent;

/**
 * 
 * @author Shimon Kama
 * 
 */
public class MapDirections {
  GoogleMap map;
  final HashMap<ClientEvent, DirectionDescription> activeRoutes;
  
  public MapDirections(final GoogleMap map) {
    this.map = map;
    activeRoutes = new HashMap<ClientEvent, DirectionDescription>();
  }
  
  /**
   * Draws all active routes
   * 
   * @param a
   *          the activity to draw on
   * @param source
   *          the source point
   */
  public void drawAllActiveRoutes(final Activity a, final LatLng source) {
    for (final ClientEvent e : activeRoutes.keySet())
      drawPolylines(a, e, activeRoutes.get(e).latLngs);
  }
  
  /**
   * Draws a route to the given event
   * 
   * @param a
   *          the activity to draw on
   * @param e
   *          an event
   * @param source
   *          the source point
   */
  public void drawDirections(final Activity a, final ClientEvent e, final LatLng source) {
    new AsyncTask<Void, Void, List<LatLng>>() {
      @Override protected List<LatLng> doInBackground(final Void... params) {
        try {
          return activeRoutes.containsKey(e) || e.getTravelWay().equals(TravelWay.NO_TRAVEL) ? null : DrawDirections.getDirections(
              source.latitude, source.longitude, e.getLatitude() / 1E6, e.getLongitude() / 1E6, e.getTravelWay().getTravelWay());
        } catch (final Exception exp) {
          exp.printStackTrace();
        }
        return null;
      }
      
      @Override protected void onPostExecute(final List<LatLng> directions) {
        if (directions != null)
          drawPolylines(a, e, directions);
      }
    }.execute();
  }
  
  /**
   * Removes direction to a certain event
   * 
   * @param e
   *          an event
   */
  public void removeDirections(final ClientEvent e) {
    if (!activeRoutes.containsKey(e))
      return;
    final List<Polyline> toRemove = activeRoutes.remove(e).polylines;
    for (final Polyline p : toRemove)
      p.remove();
  }
  
  /**
   * Removes directions to a certain event. Should be called before clearMap.
   */
  public void removeDirections() {
    for (final ClientEvent e : activeRoutes.keySet()) {
      final List<Polyline> toRemove = activeRoutes.remove(e).polylines;
      for (final Polyline p : toRemove)
        p.remove();
    }
  }
  
  /**
   * Draws polylines between each point
   * 
   * @param a
   *          the activity to draw on
   * @param e
   *          the event which the direcations are refering to
   * @param directions
   *          a list of points
   */
  void drawPolylines(final Activity a, final ClientEvent e, final List<LatLng> directions) {
    removeDirections(e);
    final List<Polyline> $ = new ArrayList<Polyline>(directions.size());
    a.runOnUiThread(new Runnable() {
      @Override public void run() {
        for (int i = 0; i < directions.size() - 1; i++)
          $.add(map.addPolyline(new PolylineOptions().add(directions.get(i), directions.get(i + 1)).width(5)
              .color(Color.parseColor("#aa0000ff")).geodesic(true)));
        activeRoutes.put(e, new DirectionDescription($, directions));
      }
    });
  }
  
  private static class DirectionDescription {
    public List<Polyline> polylines;
    public List<LatLng> latLngs;
    
    public DirectionDescription(final List<Polyline> polylines, final List<LatLng> latLngs) {
      this.polylines = polylines;
      this.latLngs = latLngs;
    }
  }
}
