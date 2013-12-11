package com.technion.coolie.joinin.communication;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.technion.coolie.joinin.communication.ClientProxy.Executer;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnDoneAsyncTask;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.Event;
import com.technion.coolie.joinin.facebook.FacebookUser;

/**
 * 
 * @author Shimon Kama
 * 
 */
public class EventRequester {
  String SERVER_BASE_ADDRESS;
  String EVENT_SERVLET;
  
  public EventRequester(final String serverBase, final String eventServlet) {
    SERVER_BASE_ADDRESS = serverBase;
    EVENT_SERVLET = eventServlet;
  }
  
  /**
   * Gets events in a radius around a geo.
   * 
   * @param radius
   *          The radius from the current point for events finding (measured in
   *          KM)
   * @param latitude
   *          The geo. point's latitude
   * @param longitude
   *          The geo. point's longitude
   * @param onDone
   *          A listener which will receive a list of events (can be left null
   *          if no special action should be taken after the data has been
   *          received from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void getEventsByRadius(final int radius, final int latitude, final int longitude, final OnDone<List<ClientEvent>> onDone,
      final OnError onError) {
    new OnDoneAsyncTask<List<ClientEvent>>(new Executer<List<ClientEvent>>() {
      @Override public List<ClientEvent> execute() {
        return ClientEvent.toList(Event.toEventList(HttpRequester.httpGet(SERVER_BASE_ADDRESS + EVENT_SERVLET + "?radius=" + radius
            + "&lat=" + latitude + "&long=" + longitude)));
      }
    }, onDone, onError).execute();
  }
  
  /**
   * Adds and event to the GAE database.
   * 
   * @param event
   *          The event that should be added.
   */
  public Long addEvent(final ClientEvent event) {
    return Long.valueOf(HttpRequester.httpPut(SERVER_BASE_ADDRESS + EVENT_SERVLET, event.toString()));
  }
  
  /**
   * Initializes events and accounts on the GAE database.
   * 
   * @param onDone
   *          A listener which will receive a string describing the added
   *          entities (can be left null if no special action should be taken
   *          after the data has been received from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void initAsync(final OnDone<String> onDone, final OnError onError) {
    new OnDoneAsyncTask<String>(new Executer<String>() {
      @Override public String execute() {
        return HttpRequester.httpGet(SERVER_BASE_ADDRESS + "/init");
      }
    }, onDone, onError).execute();
  }
  
  /**
   * Get a certain event by its id.
   * 
   * @param id
   *          The event's id
   * @param onDone
   *          A listener which will receive event (can be left null if no
   *          special action should be taken after the data has been received
   *          from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void getEventById(final long id, final OnDone<ClientEvent> onDone, final OnError onError) {
    new OnDoneAsyncTask<ClientEvent>(new Executer<ClientEvent>() {
      @Override public ClientEvent execute() {
        return ClientEvent.toClientEvent(HttpRequester.httpGet(SERVER_BASE_ADDRESS + EVENT_SERVLET + "/getEvent?id=" + id));
      }
    }, onDone, onError).execute();
  }
  
  /**
   * Get all the events which have been created by a certain user.
   * 
   * @param username
   *          the user's username.
   * @param onDone
   *          A listener which will receive the list of events (can be left null
   *          if no special action should be taken after the data has been
   *          received from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void getEventsByOwner(final String username, final OnDone<List<ClientEvent>> onDone, final OnError onError) {
    new OnDoneAsyncTask<List<ClientEvent>>(new Executer<List<ClientEvent>>() {
      @Override public List<ClientEvent> execute() {
        try {
          return ClientEvent.toList(Event.toEventList(HttpRequester.httpGet(SERVER_BASE_ADDRESS + EVENT_SERVLET
              + "/owner?username=" + URLEncoder.encode(username, "UTF-8"))));
        } catch (final UnsupportedEncodingException e) {
          return null;
        }
      }
    }, onDone, onError).execute();
  }
  
  /**
   * Add a user from an event.
   * 
   * @param username
   *          The user's username.
   * @param id
   *          The event's id.
   * @param onDone
   *          A listener which will receive the new ClientEvent after the number
   *          of participants has been increased by one (can be left null if no
   *          special action should be taken after the data has been received
   *          from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void attend(final FacebookUser username, final long id, final OnDone<ClientEvent> onDone, final OnError onError) {
    new OnDoneAsyncTask<ClientEvent>(new Executer<ClientEvent>() {
      @Override public ClientEvent execute() {
        try {
          return ClientEvent.toClientEvent(HttpRequester.httpGet(SERVER_BASE_ADDRESS + "/attend?username="
              + URLEncoder.encode(username.toJson(), "UTF-8") + "&eventId=" + id));
        } catch (final UnsupportedEncodingException e) {
          return null;
        }
      }
    }, onDone, onError).execute();
  }
  
  /**
   * Remove a user from an event.
   * 
   * @param username
   *          The user's username.
   * @param id
   *          The event's id.
   * @param onDone
   *          A listener which will receive the new ClientEvent after the number
   *          of participants has been decreased by one (can be left null if no
   *          special action should be taken after the data has been received
   *          from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void unattend(final String username, final long id, final OnDone<ClientEvent> onDone, final OnError onError) {
    new OnDoneAsyncTask<ClientEvent>(new Executer<ClientEvent>() {
      @Override public ClientEvent execute() {
        try {
          return ClientEvent.toClientEvent(HttpRequester.httpDelete(SERVER_BASE_ADDRESS + EVENT_SERVLET + "/users?username="
              + URLEncoder.encode(username, "UTF-8") + "&eventId=" + id));
        } catch (final UnsupportedEncodingException e) {
          return null;
        }
      }
    }, onDone, onError).execute();
  }
  
  /**
   * Updates an event in the database.
   * 
   * @param e
   *          The event after update. <br/>
   *          <b>Note: the event id should be corresponding to its id on the
   *          database.</b>
   * @param onDone
   *          A listener which will receive a boolean. The boolean will be set
   *          to 'true' if the update has been successful or 'false' otherwise.
   *          (can be left null if no special action should be taken after the
   *          data has been received from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void modifyEvent(final ClientEvent e, final OnDone<Boolean> onDone, final OnError onError) {
    new OnDoneAsyncTask<Boolean>(new Executer<Boolean>() {
      @SuppressWarnings("boxing") @Override public Boolean execute() {
        return HttpRequester.httpPut(SERVER_BASE_ADDRESS + EVENT_SERVLET + "/getEvent", e.toString()).equals("Y") ? true : false;
      }
    }, onDone, onError).execute();
  }
  
  /**
   * Removes an event from the database.
   * 
   * @param id
   *          the event's id.
   * @param onDone
   *          A listener which will receive a boolean. The boolean will be set
   *          to 'true' if the update has been successful or 'false' otherwise.
   *          (can be left null if no special action should be taken after the
   *          data has been received from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void deleteEvent(final long id, final OnDone<Void> onDone, final OnError onError) {
    new OnDoneAsyncTask<Void>(new Executer<Void>() {
      @Override public Void execute() {
        HttpRequester.httpDelete(SERVER_BASE_ADDRESS + EVENT_SERVLET + "?eventId=" + id);
        return null;
      }
    }, onDone, onError).execute();
  }
}
