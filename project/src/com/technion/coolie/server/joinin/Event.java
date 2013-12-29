package com.technion.coolie.server.joinin;

import java.util.HashSet;
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

  public Event(Long id_, String desc_, long time_, Set<FacebookUser> users_) {
    id = id_;
    desc = desc_;
    time = time_;
    users = new HashSet<FacebookUser>();
    if (users_ != null)
      users.addAll(users_);
  }

  Event() {

  }

  public Long getId() {
    return id;
  }

  public Set<FacebookUser> getUsers() {
    return users;
  }

  public long getTime() {
    return time;
  }

  public String getDesc() {
    return desc;
  }

}
