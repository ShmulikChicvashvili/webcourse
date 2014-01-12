package com.technion.coolie.server.joinin;

import java.util.List;

/**
 * 
 * Created on 25/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public interface IJoininAPI {

  /**
   * 
   * @param event
   *          - the event to add
   * @return - event's id if went well, -1 otherwise
   */
  public Long addEvent(Event event);

  /**
   * 
   * @param event
   *          - the event to update
   * @return - event's id if went well, -1 otherwise
   */
  public Long updateEvent(Event event);

  /**
   * 
   * @param event_
   *          - the object to remove with id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode removeEvent(Event event);

  /**
   * 
   * @param event
   *          - the object to get with the id field initialized correctly
   * @return - the requested object
   */
  public Event getEvent(Event event);

  /**
   * 
   * @return - all events in the system as list
   */
  public List<Event> getAllEvents();

  /**
   * 
   * @param event
   *          - the event to add a user to it
   * @param fbUser
   *          - the user to add
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode joinToEvent(Event event, FacebookUser fbUser);

  /**
   * 
   * @param event
   *          - the event to remove from
   * @param fbUser
   *          - the user to remove
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode removeFromEvent(Event event, FacebookUser fbUser);

  /**
   * 
   * @param event
   *          - the event to remove a user to it
   * @param fbUser
   *          - the user to remove
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode leaveEvent(Event event, FacebookUser fbUser);

}
