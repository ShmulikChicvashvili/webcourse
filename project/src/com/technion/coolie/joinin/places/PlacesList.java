package com.technion.coolie.joinin.places;

import java.util.List;

import com.google.gson.Gson;

/**
 * A list of places as returned from the Google Places server.
 * 
 * @author Kama
 * 
 */
public class PlacesList {
  private String status;
  private List<Place> results;
  
  /**
   * For Gson only
   */
  @Deprecated public PlacesList() {
  }
  
  /**
   * 
   * @return the status of the request
   */
  public String getStatus() {
    return status;
  }
  
  public void setStatus(final String status) {
    this.status = status;
  }
  
  /**
   * 
   * @return the list of results
   */
  public List<Place> getResults() {
    return results;
  }
  
  /**
   * Sets a list of results
   * 
   * @param results
   *          a list of results
   */
  public void setResults(final List<Place> results) {
    this.results = results;
  }
  
  /**
   * 
   * @param s
   *          a json representation of a Places List
   * @return the corresponding object
   */
  public static PlacesList fromJson(final String s) {
    return new Gson().fromJson(s, PlacesList.class);
  }
}