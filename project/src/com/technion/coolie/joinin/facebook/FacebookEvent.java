package com.technion.coolie.joinin.facebook;

/**
 * Class representing an event fetched from Facebook
 * 
 * @author Ido
 * 
 */
public class FacebookEvent {
  private final Long mId;
  private final String mName;
  private final String mAddress;
  private final String mDescription;
  private final Long mWhen;
  
  /**
   * C'tor
   * 
   * @param eventId
   *          - the event ID in facebook - not related to our database in any
   *          case.
   * @param name
   *          - the name of the event.
   * @param address
   *          - the address of the event.
   * @param description
   *          - the description of the event.
   * @param when
   *          - the start time of the event.
   */
  public FacebookEvent(final Long eventId, final String name, final String address, final String description, final Long when) {
    mId = eventId;
    mName = name;
    mAddress = address;
    mDescription = description;
    mWhen = when;
  }
  
  /**
   * 
   * @return the event id.
   */
  public Long getEventId() {
    return mId;
  }
  
  /**
   * 
   * @return the event name.
   */
  public String getEventName() {
    return mName;
  }
  
  /**
   * 
   * @return the event address.
   */
  public String getEventAddress() {
    return mAddress;
  }
  
  /**
   * 
   * @return the event description.
   */
  public String getEventDescription() {
    return mDescription;
  }
  
  /**
   * 
   * @return the event start time.
   */
  public Long getEventTime() {
    return mWhen;
  }
}
