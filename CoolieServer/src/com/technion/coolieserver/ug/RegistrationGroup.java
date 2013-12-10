package com.technion.coolieserver.ug;

import java.io.Serializable;
import java.util.List;

public class RegistrationGroup implements Serializable {
  private String groupId;
  private List<Meeting> lectures;
  private List<Meeting> tutorials;
  private int freePlaces;

  /**
   * @param groupId
   * @param lectures
   * @param tutorials
   * @param freePlaces
   */
  public RegistrationGroup(String groupId_, List<Meeting> lectures_,
      List<Meeting> tutorials_, int freePlaces_) {
    groupId = groupId_;
    lectures = lectures_;
    tutorials = tutorials_;
    freePlaces = freePlaces_;
  }

  RegistrationGroup() {

  }

  /**
   * @return the groupId
   */
  public String groupId() {
    return groupId;
  }

  /**
   * @param groupId
   *          the groupId to set
   */
  public void setGroupId(String groupId_) {
    groupId = groupId_;
  }

  /**
   * @return the lectures
   */
  public List<Meeting> lectures() {
    return lectures;
  }

  /**
   * @param lectures
   *          the lectures to set
   */
  public void setLectures(List<Meeting> lectures_) {
    lectures = lectures_;
  }

  /**
   * @return the tutorials
   */
  public List<Meeting> tutorials() {
    return tutorials;
  }

  /**
   * @param tutorials
   *          the tutorials to set
   */
  public void setTutorials(List<Meeting> tutorials_) {
    tutorials = tutorials_;
  }

  /**
   * @return the freePlaces
   */
  public int freePlaces() {
    return freePlaces;
  }

  /**
   * @param freePlaces
   *          the freePlaces to set
   */
  public void setFreePlaces(int freePlaces_) {
    freePlaces = freePlaces_;
  }
}
