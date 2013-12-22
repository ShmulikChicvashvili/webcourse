package com.technion.coolie.joinin.directions;

/**
 * Enum for the requested travel way
 * 
 * @author Ido Gonen
 * 
 */
public enum TravelWay implements GetTravelWay {
  DRIVING {
    @Override public String getTravelWay() {
      return "driving";
    }
  },
  WALKING {
    @Override public String getTravelWay() {
      return "walking";
    }
  },
  NO_TRAVEL {
    @Override public String getTravelWay() {
      return "no travel";
    }
  }
}