package com.technion.coolie.server.joinin;

import java.util.Set;

/**
 * 
 * Created on 25/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public class Event {

  Long id;
  String desc;
  long time;
  Set<FacebookUser> users;

  public Long getId() {
    return id;
  }

  public Set<FacebookUser> getUsers() {
    return users;
  }

  public long getTime() {
    return time;
  }

}
