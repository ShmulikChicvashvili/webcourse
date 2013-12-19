package com.technion.coolie.joinin.communication;


import java.util.List;

import com.technion.coolie.joinin.communication.ClientProxy.Executer;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnDoneAsyncTask;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.EventMessage;

/**
 * 
 * @author Shimon Kama
 * 
 */
public class EventMessageRequester {
  String SERVER_BASE_ADDRESS;
  String EVENT_MESSAGE_SERVLET;
  
  public EventMessageRequester(final String serverBase, final String servlet) {
    SERVER_BASE_ADDRESS = serverBase;
    EVENT_MESSAGE_SERVLET = servlet;
  }
  
  /**
   * Adds a new message to the GAE database.
   * 
   * @param m
   *          The message to be added.
   * @param onDone
   *          A listener which will receive a Long. The Long will contain the id
   *          of the message in the GAE database. (can be left null if no
   *          special action should be taken after the data has been received
   *          from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void addMessage(final EventMessage m, final OnDone<Long> onDone, final OnError onError) {
    new OnDoneAsyncTask<Long>(new Executer<Long>() {
      @Override public Long execute() {
        return Long.valueOf(HttpRequester.httpPut(SERVER_BASE_ADDRESS + EVENT_MESSAGE_SERVLET, m.toString()));
      }
    }, onDone, onError).execute();
  }
  
  /**
   * Returns all the messages posted in a certain event.
   * 
   * @param eventId
   *          An event's id.
   * @param onDone
   *          A listener which will receive a List of MessageEvent. The List
   *          will contain the messages which belongs to the given event. (can
   *          be left null if no special action should be taken after the data
   *          has been received from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void getMessages(final long eventId, final OnDone<List<EventMessage>> onDone, final OnError onError) {
    new OnDoneAsyncTask<List<EventMessage>>(new Executer<List<EventMessage>>() {
      @Override public List<EventMessage> execute() {
        return EventMessage.toEventMessageList(HttpRequester.httpGet(SERVER_BASE_ADDRESS + EVENT_MESSAGE_SERVLET + "?eventId="
            + eventId));
      }
    }, onDone, onError).execute();
  }
  
  /**
   * Removes a message from the database.
   * 
   * @param messageId
   *          The message to be removed
   * @param onDone
   *          Lets you know when the action is done. (can be left null if no
   *          special action should be taken after the data has been received
   *          from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void deleteMessage(final long messageId, final OnDone<Void> onDone, final OnError onError) {
    new OnDoneAsyncTask<Void>(new Executer<Void>() {
      @Override public Void execute() {
        HttpRequester.httpDelete(SERVER_BASE_ADDRESS + EVENT_MESSAGE_SERVLET + "?messageId=" + messageId);
        return null;
      }
    }, onDone, onError).execute();
  }
}
