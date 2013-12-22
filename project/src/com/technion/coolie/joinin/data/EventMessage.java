package com.technion.coolie.joinin.data;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Shimon Kama
 * 
 */
public class EventMessage implements Comparable<EventMessage> {
  private long messageId;
  private long eventId;
  private String username;
  private String fId;
  private String name;
  private String text;
  private EventDate date;
  
  /**
   * Need in order for Gson to work
   */
  @Deprecated public EventMessage() {
  }
  
  /**
   * Creates a new message
   * 
   * @param messageId
   *          the message id in server
   * @param eventId
   *          the corresponding event's id
   * @param username
   *          who wrote this message
   * @param fId
   *          the Facebook id of the writer
   * @param name
   *          the full name of the writer
   * @param text
   *          the body of the message
   * @param date
   *          the date of the message
   */
  public EventMessage(final long messageId, final long eventId, final String username, final String fId, final String name,
      final String text, final EventDate date) {
    this.eventId = eventId;
    this.username = username;
    this.fId = fId;
    this.name = name;
    this.text = text;
    this.date = date;
    this.messageId = messageId;
  }
  
  /**
   * Creates a new message
   * 
   * @param eventId
   *          the corresponding event's id
   * @param username
   *          who wrote this message
   * @param fId
   *          the Facebook id of the writer
   * @param name
   *          the full name of the writer
   * @param text
   *          the body of the message
   * @param date
   *          the date of the message
   */
  public EventMessage(final long eventId, final String username, final String fId, final String name, final String text,
      final EventDate date) {
    this.eventId = eventId;
    this.fId = fId;
    this.username = username;
    this.name = name;
    this.text = text;
    this.date = date;
    messageId = 0;
  }
  
  /**
   * @return the messageId
   */
  public long getMessageId() {
    return messageId;
  }
  
  /**
   * @return the eventId which this message belongs to
   */
  public long getEventId() {
    return eventId;
  }
  
  /**
   * @return the text
   */
  public String getText() {
    return text;
  }
  
  /**
   * @return the date
   */
  public EventDate getDate() {
    return date;
  }
  
  /**
   * @param messageId
   *          the messageId to set
   */
  public void setMessageId(final long messageId) {
    this.messageId = messageId;
  }
  
  /**
   * @param eventId
   *          the eventId to set
   */
  public void setEventId(final long eventId) {
    this.eventId = eventId;
  }
  
  /**
   * @param text
   *          the text to set
   */
  public void setText(final String text) {
    this.text = text;
  }
  
  /**
   * @param date
   *          the date to set
   */
  public void setDate(final EventDate date) {
    this.date = date;
  }
  
  /**
   * Equality is checked by comparing the message id.
   */
  @Override public boolean equals(final Object o) {
    if (o == null || o.getClass() != getClass())
      return false;
    if (o == this)
      return true;
    return ((EventMessage) o).messageId == messageId;
  }
  
  @Override public int hashCode() {
    return Long.valueOf(messageId).hashCode();
  }
  
  /**
   * Returns a Json string representing the object.
   */
  @Override public String toString() {
    return new Gson().toJson(this);
  }
  
  /**
   * 
   * @param s
   *          A Json string representing an EventMessage
   * @return The message represented by the string.
   */
  public static EventMessage toEventMessage(final String s) {
    return new Gson().fromJson(s, EventMessage.class);
  }
  
  /**
   * 
   * @param l
   *          A list of EventMessages
   * @return A Json string representing the list.
   */
  public static String stringOf(final List<EventMessage> l) {
    return new Gson().toJson(l, new TypeToken<List<EventMessage>>() {
      // EMPTY BLOCK
    }.getType());
  }
  
  /**
   * 
   * @param s
   *          A string representing a list of EventMessage
   * @return A list of EventMessages
   */
  public static List<EventMessage> toEventMessageList(final String s) {
    return new Gson().fromJson(s, new TypeToken<List<EventMessage>>() {
      // EMPTY BLOCK
    }.getType());
  }
  
  /**
   * Compares by date.
   * 
   * @param m
   *          The message to compare to
   * @return the value 0 if the argument EventMessage is equal to this
   *         EventMessage; a value less than 0 if this EventMessage was written
   *         before the EventMessage argument; and a value greater than 0 if
   *         this EventMessage was written after the EventMessage argument
   */
  @Override public int compareTo(final EventMessage m) {
    return date.compareTo(m.date);
  }
  
  /**
   * 
   * @return the Facebook id of the writer
   */
  public String getfId() {
    return fId;
  }
  
  /**
   * Sets the Facebook id of the writer
   * 
   * @param fId
   */
  public void setfId(final String fId) {
    this.fId = fId;
  }
  
  /**
   * 
   * @return the name of the writer
   */
  public String getName() {
    return name;
  }
  
  /**
   * Sets the name of the writer
   * 
   * @param name
   *          the name
   */
  public void setName(final String name) {
    this.name = name;
  }
  
  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }
  
  /**
   * @param username
   *          the username to set
   */
  public void setUsername(final String username) {
    this.username = username;
  }
}
