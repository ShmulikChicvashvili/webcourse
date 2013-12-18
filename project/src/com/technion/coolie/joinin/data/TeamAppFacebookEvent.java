package com.technion.coolie.joinin.data;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

/**
 * This object connects between a teamapp ID and a facebook ID
 * 
 * @author Shimon Kama
 * 
 */
public class TeamAppFacebookEvent {
  private long teamappId;
  private long facebookId;
  private List<String> attendees;
  
  /**
   * Only for Gson
   */
  @Deprecated public TeamAppFacebookEvent() {
  }
  
  /**
   * Creates a new TeamApp-Facebook event
   * 
   * @param teamappId
   *          the id in TeamApp database
   * @param facebbokId
   *          the id in Facebook database
   */
  public TeamAppFacebookEvent(final long teamappId, final long facebbokId) {
    this.teamappId = teamappId;
    facebookId = facebbokId;
    attendees = new LinkedList<String>();
  }
  
  /**
   * @return the teamapp event id
   */
  public long getTeamappId() {
    return teamappId;
  }
  
  /**
   * @param teamappId
   *          the teamapp event id to set
   */
  public void setTeamappId(final long teamappId) {
    this.teamappId = teamappId;
  }
  
  /**
   * @return the facebook event id
   */
  public long getFacebookId() {
    return facebookId;
  }
  
  /**
   * @param facebookId
   *          the facebook event id to set
   */
  public void setFacebookId(final long facebookId) {
    this.facebookId = facebookId;
  }
  
  /**
   * @return the attendees
   */
  public List<String> getAttendees() {
    return attendees;
  }
  
  /**
   * @param attendees
   *          the attendees to set
   */
  public void setAttendees(final List<String> attendees) {
    this.attendees = attendees;
  }
  
  /**
   * @return the attendees as JSON string
   */
  public List<String> getAttendeesString() {
    return attendees;
  }
  
  /**
   * 
   * @return a JSON representing string of this object
   */
  public String toJson() {
    return new Gson().toJson(this);
  }
  
  /**
   * 
   * @param json
   *          a JSON representing string of this object
   * @return the corresponding FacebookEvent object
   */
  public static TeamAppFacebookEvent fromJson(final String json) {
    return new Gson().fromJson(json, TeamAppFacebookEvent.class);
  }
}
