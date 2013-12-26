package com.technion.coolie.server.joinin;

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
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addEvent(Event event);

  /**
   * 
   * @param event
   *          - the event to update
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode updateEvent(Event event);

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
   * @return - all the events in the system
   */
  public Event getAllEvents();

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
