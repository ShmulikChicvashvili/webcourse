package com.technion.coolie.server.joinin;

/**
 * 
 * Created on 9/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public enum JoininEnum {
  JOININ_SERVLET("Joinin"), EVENT("event"), ADD_EVENT("addEvent"), REMOVE_EVENT(
      "removeEvent"), GET_EVENT("getEvent"), GET_ALL_EVENTS("getAllEvents"), JOIN_TO_EVENT(
      "joinToEvent"), LEAVE_EVENT("leaveEvent"), FB_USER("FacebookUser"), REMOVE_FROM_EVENT(
      "removeFromEvent");

  private final String value;

  private JoininEnum(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
