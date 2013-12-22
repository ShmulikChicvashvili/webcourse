package com.technion.coolie.joinin.communication;

import com.technion.coolie.joinin.communication.ClientProxy.Executer;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnDoneAsyncTask;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.TeamAppFacebookEvent;

/**
 * @author Kama
 * 
 */
public class FacebookEventRequester {
  String SERVER_BASE_ADDRESS;
  String FB_SERVLET;
  
  public FacebookEventRequester(final String serverBase, final String servlet) {
    SERVER_BASE_ADDRESS = serverBase;
    FB_SERVLET = servlet;
  }
  
  /**
   * Gets a facebook-teamapp event connection from the GAE database
   * 
   * @param teamappId
   *          the event's id on teamapp database
   * @param onDone
   *          A listener which will receive the event (can be left null if no
   *          special action should be taken after the data has been received
   *          from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void getFacebookEvent(final long teamappId, final OnDone<TeamAppFacebookEvent> onDone, final OnError onError) {
    new OnDoneAsyncTask<TeamAppFacebookEvent>(new Executer<TeamAppFacebookEvent>() {
      @Override public TeamAppFacebookEvent execute() {
        return TeamAppFacebookEvent.fromJson(HttpRequester.httpGet(SERVER_BASE_ADDRESS + FB_SERVLET + "?teamappId=" + teamappId));
      }
    }, onDone, onError).execute();
  }
  
  /**
   * Adds a facebook-teamapp event connection to the GAE database
   * 
   * @param e
   *          the event to add
   * @param onDone
   *          A listener which will let you know when the action is done (can be
   *          left null if no special action should be taken after the data has
   *          been received from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void addFacebookEvent(final TeamAppFacebookEvent fbe) {
    HttpRequester.httpPut(SERVER_BASE_ADDRESS + FB_SERVLET, fbe.toJson());
  }
  
  /**
   * Deletes a facebook-teamapp event connection from the GAE database
   * 
   * @param teamappId
   *          the event's id on teamapp database
   * @param onDone
   *          A listener which will let you know when the action is done (can be
   *          left null if no special action should be taken after the data has
   *          been received from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   */
  public void deleteFacebookEvent(final long teamappId, final OnDone<Void> onDone, final OnError onError) {
    new OnDoneAsyncTask<Void>(new Executer<Void>() {
      @Override public Void execute() {
        HttpRequester.httpDelete(SERVER_BASE_ADDRESS + FB_SERVLET + "?teamappId=" + teamappId);
        return null;
      }
    }, onDone, onError).execute();
  }
}
