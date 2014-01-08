package com.technion.coolie.server.joinin;

import java.util.HashSet;
import java.util.Set;

public class Event {

  Long mId;
  protected String mName;
  protected String mAddress;
  protected String mDescription;
  protected long mWhen;
  protected long mInvited;
  protected EventType mType;
  protected String mOwner;
  protected Set<FacebookUser> mSubscribed;

  /**
   * A no-args constructor. Must have one for Gson to work
   */
  public Event() {
  }

  /**
   * The event's contractor
   * 
   * @param name
   *          The event's name
   * @param address
   *          The event's address
   * @param description
   *          The event's description
   * @param when
   *          The event's time
   * @param invited
   *          The event's number of invited to that event.
   */
  public Event(final String name, final String address,
      final String description, long when, final long invited,
      final EventType type, final String owner) {
    mName = name;
    mAddress = address;
    mDescription = description;
    mWhen = when;
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
    mWhen = e.mWhen;
    mInvited = e.mInvited;
    mType = e.mType;
    mOwner = e.mOwner;
    mSubscribed = new HashSet<FacebookUser>();
    if (e.mSubscribed != null)
      mSubscribed.addAll(e.mSubscribed);
  }

  // -------- Setters and Getters
  /**
   * Getter for the Event's id
   * 
   * @return The events id
   */
  public Long getId() {
    return mId;
  }

  /**
   * Setter of the Event's id
   * 
   * @param mId
   *          The new id
   */
  public void setId(final Long mId) {
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
   * Getter for the Event's time
   * 
   * @return The event's time
   */
  public long getWhen() {
    return mWhen;
  }

  /**
   * Setter of the Event's time
   * 
   * @param time
   *          The new time
   */
  public void setWhen(final long time) {
    this.mWhen = time;
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
  @Override
  public boolean equals(final Object o) {
    if (o == null || o.getClass() != getClass())
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
  @SuppressWarnings("boxing")
  @Override
  public int hashCode() {
    return mId.hashCode();
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

  public long getTime() {
    return mWhen;
  }

}
