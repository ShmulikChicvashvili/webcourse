package com.technion.coolie.server.ug.framework;

import java.io.Serializable;
import java.util.List;

public class RegistrationGroup implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -6751987554460660402L;

  private int groupId;
  private List<Meeting> lectures;
  private List<Meeting> tutorials;
  private int freePlaces;

  public RegistrationGroup(int groupId, List<Meeting> lectures,
      List<Meeting> tutorials, int freePlaces) {
    super();
    this.groupId = groupId;
    this.lectures = lectures;
    this.tutorials = tutorials;
    this.freePlaces = freePlaces;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public List<Meeting> getLectures() {
    return lectures;
  }

  public void setLectures(List<Meeting> lectures) {
    this.lectures = lectures;
  }

  public List<Meeting> getTutorials() {
    return tutorials;
  }

  public void setTutorials(List<Meeting> tutorials) {
    this.tutorials = tutorials;
  }

  public int getFreePlaces() {
    return freePlaces;
  }

  public void setFreePlaces(int freePlaces) {
    this.freePlaces = freePlaces;
  }

  public String print() {
    String $ = "";
    $ += ("RegistrationGroup") + "\n";
    $ += ("   groupId=" + groupId) + "\n";
    $ += ("   freePlaces=" + freePlaces) + "\n";
    $ += ("lectures:") + "\n";
    for (Meeting m : lectures)
      $ += m.print();
    $ += ("tutorials:") + "\n";
    for (Meeting m : tutorials)
      $ += m.print();
    return $;
  }
}
