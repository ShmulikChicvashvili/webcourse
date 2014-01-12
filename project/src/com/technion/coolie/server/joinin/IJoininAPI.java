package com.technion.coolie.server.joinin;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

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
   * @throws IOException
   * @throws NumberFormatException
   */
  public Long addEvent(Event event) throws NumberFormatException, IOException;

  /**
   * 
   * @param event
   *          - the event to update
   * @return - event's id if went well, -1 otherwise
   * @throws IOException
   * @throws NumberFormatException
   */
  public Long updateEvent(Event event) throws NumberFormatException,
      IOException;

  /**
   * 
   * @param event_
   *          - the object to remove with id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode removeEvent(Event event) throws IOException;

  /**
   * 
   * @param event
   *          - the object to get with the id field initialized correctly
   * @return - the requested object
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public Event getEvent(Event event) throws JsonSyntaxException, IOException;

  /**
   * 
   * @return - all events in the system as list
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public List<Event> getAllEvents() throws JsonSyntaxException, IOException;

  /**
   * 
   * @param event
   *          - the event to add a user to it
   * @param fbUser
   *          - the user to add
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode joinToEvent(Event event, FacebookUser fbUser)
      throws IOException;

  /**
   * 
   * @param event
   *          - the event to remove from
   * @param fbUser
   *          - the user to remove
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode removeFromEvent(Event event, FacebookUser fbUser)
      throws IOException;

  /**
   * 
   * @param event
   *          - the event to remove a user to it
   * @param fbUser
   *          - the user to remove
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode leaveEvent(Event event, FacebookUser fbUser)
      throws IOException;

}
