package com.technion.coolie.joinin.data;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.joinin.facebook.FacebookUser;
import com.technion.coolie.joinin.map.EventType;

/**
 * This class represents an event.
 * 
 * @author Yaniv Beaudoin
 * 
 */
public class Event {
  protected long mId;
  protected String mName;
  protected String mAddress;
  protected String mDescription;
  protected long mLatitude;
  protected long mLongitude;
  protected long mWhen;
  protected long mInvited;
  protected EventType mType;
  protected String mOwner;
  protected Set<FacebookUser> mSubscribed;
  
  /**
   * A no-args constructor. Must have one for Gson to work
   */
  @Deprecated public Event() {
  }
  
  /**
   * The event's contractor
   * 
   * @param eventId
   *          The event's id
   * @param name
   *          The event's name
   * @param address
   *          The event's address
   * @param description
   *          The event's description
   * @param latitude
   *          The event's latitude
   * @param longitude
   *          The event's longitude
   * @param when
   *          The event's time
   * @param invited
   *          The event's number of invited to that event.
   */
  public Event(final long eventId, final String name, final String address, final String description, final long latitude,
      final long longitude, final EventDate when, final long invited, final EventType type, final String owner) {
    mId = eventId;
    mName = name;
    mAddress = address;
    mDescription = description;
    mLatitude = latitude;
    mLongitude = longitude;
    mWhen = when.getTime();
    mInvited = invited;
    mType = type;
    mOwner = owner;
    mSubscribed = new HashSet<FacebookUser>();
  }
  
  public Event(final Event e) {
    mId = e.mId;
    mName = e.mName;
    mAddress = e.mAddress;
    mDescription = e.mDescription;
    mLatitude = e.mLatitude;
    mLongitude = e.mLongitude;
    mWhen = e.mWhen;
    mInvited = e.mInvited;
    mType = e.mType;
    mOwner = e.mOwner;
    mSubscribed = new HashSet<FacebookUser>();
    if (e.mSubscribed != null)
      mSubscribed.addAll(e.mSubscribed);
  }
  
  /**
   * Returns a String represents the event toString function of Event class
   * 
   * @return A String represents the event.
   */
  @Override public String toString() {
    return new Gson().toJson(this);
  }
  
  /**
   * Creating an event from a String that represent an event
   * 
   * @param s
   *          The event's string
   * @return Event instance that was represented be the given string
   */
  public static Event toEvent(final String s) {
    return new Gson().fromJson(s, Event.class);
  }
  
  /**
   * Creating a string representing an Events list
   * 
   * @param l
   *          Events list to be represented as a String
   * @return A string representation of l
   */
  public static String stringOf(final List<Event> l) {
    return new Gson().toJson(l, new TypeToken<List<Event>>() {
      // EMPTY BLOCK
    }.getType());
  }
  
  /**
   * Creating an Events list from a String representing an events list
   * 
   * @param s
   *          The Events list's String representation to be translated into an
   *          Events list.
   * @return The event list represented be s
   */
  public static List<Event> toEventList(final String s) {
    return new Gson().fromJson(s, new TypeToken<List<Event>>() {
      // EMPTY BLOCK
    }.getType());
  }
  
  // -------- Setters and Getters
  /**
   * Getter for the Event's id
   * 
   * @return The events id
   */
  public long getId() {
    return mId;
  }
  
  /**
   * Setter of the Event's id
   * 
   * @param mId
   *          The new id
   */
  public void setId(final long mId) {
    this.mId = mId;
  }
  
  /**
   * Getter for the Event's name
   * 
   * @return The event's name
   */
  public String getName() {
    return mName;
  }
  
  /**
   * Setter of the Event's name
   * 
   * @param name
   *          The new name
   */
  public void setName(final String mName) {
    this.mName = mName;
  }
  
  /**
   * Getter for the Event's address
   * 
   * @return The event's address
   */
  public String getAddress() {
    return mAddress;
  }
  
  /**
   * Setter of the Event's address
   * 
   * @param address
   *          The new address
   */
  public void setAddress(final String mAddress) {
    this.mAddress = mAddress;
  }
  
  /**
   * Getter for the Event's description
   * 
   * @return The event's description
   */
  public String getDescription() {
    return mDescription;
  }
  
  /**
   * Setter of the Event's description
   * 
   * @param description
   *          The new description
   */
  public void setDescription(final String mDescription) {
    this.mDescription = mDescription;
  }
  
  /**
   * Getter for the Event's latitude
   * 
   * @return The event's latitude
   */
  public long getLatitude() {
    return mLatitude;
  }
  
  /**
   * Setter of the Event's latitude
   * 
   * @param latitude
   *          The new latitude
   */
  public void setLatitude(final long mLatitude) {
    this.mLatitude = mLatitude;
  }
  
  /**
   * Getter for the Event's longitude
   * 
   * @return The event's longitude
   */
  public long getLongitude() {
    return mLongitude;
  }
  
  /**
   * Setter of the Event's longitude
   * 
   * @param longitude
   *          The new longitude
   */
  public void setLongitude(final long mLongitude) {
    this.mLongitude = mLongitude;
  }
  
  /**
   * Getter for the Event's time
   * 
   * @return The event's time
   */
  public EventDate getWhen() {
    return new EventDate(mWhen);
  }
  
  /**
   * Setter of the Event's time
   * 
   * @param time
   *          The new time
   */
  public void setWhen(final EventDate mWhen) {
    this.mWhen = mWhen.getTime();
  }
  
  /**
   * Getter for the Event's number of invited.
   * 
   * @return The event's number of invited.
   */
  public long getInvited() {
    return mInvited;
  }
  
  /**
   * Setter of the Event's invited
   * 
   * @param time
   *          The new invited
   */
  public void setInvited(final long mInvited) {
    this.mInvited = mInvited;
  }
  
  /**
   * @return the mType
   */
  public EventType getEventType() {
    return mType;
  }
  
  /**
   * @param mType
   *          the mType to set
   */
  public void setEventType(final EventType mType) {
    this.mType = mType;
  }
  
  /**
   * Equals to events be id
   */
  @Override public boolean equals(final Object o) {
    if (o == null || !(o instanceof Event))
      return false;
    if (o == this)
      return true;
    return ((Event) o).mId == mId;
  }
  
  /**
   * Returns the event's hash code.
   * 
   * @return The event's hash code.
   */
  @SuppressWarnings("boxing") @Override public int hashCode() {
    return ((Long) mId).hashCode();
  }
  
  /**
   * @return Returns the username of the owner of this event (the one that
   *         created it)
   */
  public String getOwner() {
    return mOwner;
  }
  
  /**
   * 
   * @param mOwner
   *          - the username of the creator of the event
   */
  public void setOwner(final String mOwner) {
    this.mOwner = mOwner;
  }
  
  /**
   * Adds a user to the subscribed users (only if he isn't already subscribed)
   * 
   * @param user
   */
  public boolean addUser(final FacebookUser user) {
    return mSubscribed.add(user);
  }
  
  public void removeUser(final String username) {
    mSubscribed.remove(new FacebookUser(username, username));
  }
  
  public Set<FacebookUser> getUsers() {
    return new HashSet<FacebookUser>(mSubscribed);
  }
  
  public void setUsers(final Set<FacebookUser> users) {
    mSubscribed = users;
  }
  
  public int getConfirmed() {
    return mSubscribed.size();
  }
  
  public boolean isSubscribed(FacebookUser user){
	  return mSubscribed.contains(user);
  }
}
