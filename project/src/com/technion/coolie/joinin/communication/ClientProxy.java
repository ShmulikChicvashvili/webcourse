package com.technion.coolie.joinin.communication;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.util.Log;

import com.technion.coolie.R;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.EventMessage;
import com.technion.coolie.joinin.data.TeamAppFacebookEvent;
import com.technion.coolie.joinin.facebook.FacebookUser;

/**
 * Class used to encapsulate the communication with the server. The public
 * methods should be called with a onDone instance that is called when the
 * communication operation is done.
 * 
 * @author Shimon Kama (Originally by Yaniv Beaudoin)
 */
public abstract class ClientProxy {
  private static String SERVER_BASE_ADDRESS = "http://teamapp-yp.appspot.com";
  private static final String ACCOUNT_SERVLET = "/accounts";
  private static final String EVENT_SERVLET = "/events";
  private static final String EVENT_MESSAGE_SERVLET = "/events/eventmessage";
  private static final String GCMREGISTER_SERVLET = "/gcmregister";
  private static final String FB_SERVLET = "/fbevent";
  private static EventRequester eq = new EventRequester(SERVER_BASE_ADDRESS, EVENT_SERVLET);
  private static EventMessageRequester emq = new EventMessageRequester(SERVER_BASE_ADDRESS, EVENT_MESSAGE_SERVLET);
  private static AccountRequester aq = new AccountRequester(SERVER_BASE_ADDRESS, EVENT_SERVLET, ACCOUNT_SERVLET);
  private static FacebookEventRequester fbq = new FacebookEventRequester(SERVER_BASE_ADDRESS, FB_SERVLET);
  private static GCMRequester gcmq = new GCMRequester(SERVER_BASE_ADDRESS, GCMREGISTER_SERVLET);
  
  /**
   * Sets the base server address to the debug one.
   */
  public static void setDebug() {
    SERVER_BASE_ADDRESS = "http://teamapp-db-test.appspot.com";
    setRequesters();
  }
  
  /**
   * Sets the base server address to the presentation one.
   */
  public static void setPresent() {
    SERVER_BASE_ADDRESS = "http://teamapp-present.appspot.com";
    setRequesters();
  }
  
  /**
   * Sets the base server address to the release one.
   */
  public static void setRelease() {
    SERVER_BASE_ADDRESS = "http://teamapp-release.appspot.com";
    setRequesters();
  }
  
  /**
   * Set requesters based on the server base address.
   */
  private static void setRequesters() {
    eq = new EventRequester(SERVER_BASE_ADDRESS, EVENT_SERVLET);
    emq = new EventMessageRequester(SERVER_BASE_ADDRESS, EVENT_MESSAGE_SERVLET);
    aq = new AccountRequester(SERVER_BASE_ADDRESS, EVENT_SERVLET, ACCOUNT_SERVLET);
    fbq = new FacebookEventRequester(SERVER_BASE_ADDRESS, FB_SERVLET);
    gcmq = new GCMRequester(SERVER_BASE_ADDRESS, GCMREGISTER_SERVLET);
  }
  
  public static String getBaseAddress() {
    return SERVER_BASE_ADDRESS;
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
   * 
   */
  public static void getEventsByRadius(final int radius, final int latitude, final int longitude,
      final OnDone<List<ClientEvent>> onDone, final OnError onError) {
    eq.getEventsByRadius(radius, latitude, longitude, onDone, onError);
  }
  
  /**
   * Adds and event to the GAE database.
   * 
   * @param event
   *          The event that should be added.
   * @param onDone
   *          A listener which will receive the event's id in the database (can
   *          be left null if no special action should be taken after the data
   *          has been received from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   * 
   */
  public static void addEvent(final ClientEvent event, final TeamAppFacebookEvent fbe, final OnDone<Long> onDone,
      final OnError onError) {
    new OnDoneAsyncTask<Long>(new Executer<Long>() {
      @SuppressWarnings("synthetic-access") @Override public Long execute() {
        final Long $ = eq.addEvent(event);
        if (fbe == null || fbe.getFacebookId() < 0)
          return $;
        fbe.setTeamappId($.longValue());
        fbq.addFacebookEvent(fbe);
        return $;
      }
    }, onDone, onError).execute();
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
   * 
   */
  public static void initAsync(final OnDone<String> onDone, final OnError onError) {
    eq.initAsync(onDone, onError);
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
   * 
   */
  public static void getEventById(final long id, final OnDone<ClientEvent> onDone, final OnError onError) {
    eq.getEventById(id, onDone, onError);
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
   * 
   */
  public static void getEventsByOwner(final String username, final OnDone<List<ClientEvent>> onDone, final OnError onError) {
    eq.getEventsByOwner(username, onDone, onError);
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
   * 
   */
  public static void attend(final FacebookUser username, final long id, final OnDone<ClientEvent> onDone, final OnError onError) {
    eq.attend(username, id, onDone, onError);
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
   * 
   */
  public static void unattend(final String username, final long id, final OnDone<ClientEvent> onDone, final OnError onError) {
    eq.unattend(username, id, onDone, onError);
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
   */
  public static void modifyEvent(final ClientEvent e, final OnDone<Boolean> onDone, final OnError onError) {
    eq.modifyEvent(e, onDone, onError);
  }
  
  /**
   * Removes an event from the database.
   * 
   * @param id
   *          the event's id.
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   * 
   */
  public static void deleteEvent(final long id, final OnDone<Void> onDone, final OnError onError) {
    eq.deleteEvent(id, onDone, onError);
  }
  
  /**
   * Logs in an account (actually checks if the given username and password are
   * stored in the database)
   * 
   * @param username
   *          A username
   * @param onDone
   *          A listener which will receive an account (can be left null if no
   *          special action should be taken after the data has been received
   *          from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   * 
   */
  public static void login(final String username, final OnDone<ClientAccount> onDone, final OnError onError) {
    aq.login(username, onDone, onError);
  }
  
  /**
   * Adds an account to the GAE database.
   * 
   * @param a
   *          The account that should be added to the database.
   * @param onDone
   *          A listener which will receive the account's username (can be left
   *          null if no special action should be taken after the data has been
   *          received from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   * 
   */
  public static void addAccount(final ClientAccount a, final OnDone<String> onDone, final OnError onError) {
    aq.addAccount(a, onDone, onError);
  }
  
  /**
   * Get all the events in which a certain user participates.
   * 
   * @param username
   *          The user's username.
   * @param onDone
   *          A listener which will receive the list of ClientEvents (can be
   *          left null if no special action should be taken after the data has
   *          been received from the server).
   * @param onError
   *          A listener which will be called only if an error occurred while
   *          connecting to remote server. This action will be called before a
   *          dialog message will be shown.
   * 
   */
  public static void getEventsAttending(final String username, final OnDone<List<ClientEvent>> onDone, final OnError onError) {
    aq.getEventsAttending(username, onDone, onError);
  }
  
  public static void getAccountByUserName(final String username, final OnDone<ClientAccount> onDone, final OnError onError) {
    aq.getAccountByUserName(username, onDone, onError);
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
  public static void addMessage(final EventMessage m, final OnDone<Long> onDone, final OnError onError) {
    emq.addMessage(m, onDone, onError);
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
  public static void getMessages(final long eventId, final OnDone<List<EventMessage>> onDone, final OnError onError) {
    emq.getMessages(eventId, onDone, onError);
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
  public static void deleteMessage(final long messageId, final OnDone<Void> onDone, final OnError onError) {
    emq.deleteMessage(messageId, onDone, onError);
  }
  
  public static void gcmIsFoundAsync(final String regId, final String userName, final OnDone<String> onDone) {
    gcmq.gcmIsFoundAsync(regId, userName, onDone);
  }
  
  public static String gcmIsFoundNotAsync(final String regId, final String userName) {
    return gcmq.gcmIsFoundNotAsync(regId, userName);
  }
  
  public static String gcmRegisterNotAsync(final String regId, final String userName) {
    return gcmq.gcmRegisterNotAsync(regId, userName);
  }
  
  public static void gcmRegisterAsync(final String regId, final String userName) {
    gcmq.gcmRegisterAsync(regId, userName);
  }
  
  public static String gcmUnregisterNotAsync(final String regId, final String userName) {
    return gcmq.gcmUnregisterNotAsync(regId, userName);
  }
  
  public static void gcmUnregisterAsync(final String regId, final String userName) {
    gcmq.gcmUnregisterAsync(regId, userName);
  }
  
  public static void modifyFacebookEvent(final TeamAppFacebookEvent e, final OnDone<Void> onDone, final OnError onError) {
    new OnDoneAsyncTask<Void>(new Executer<Void>() {
      @SuppressWarnings("synthetic-access") @Override public Void execute() {
        fbq.addFacebookEvent(e);
        return null;
      }
    }, onDone, onError).execute();
  }
  
  public static void getFacebookEvent(final long teamappId, final OnDone<TeamAppFacebookEvent> onDone, final OnError onError) {
    fbq.getFacebookEvent(teamappId, onDone, onError);
  }
  
  public static void deleteFacebookEvent(final long teamappId, final OnDone<Void> onDone, final OnError onError) {
    fbq.deleteFacebookEvent(teamappId, onDone, onError);
  }
  
  /**
   * An interface of a listener. Will be used in all the methods of the
   * ClientProxy class. After the particular task has been completed, the
   * listener will be activated.
   * 
   * @param <T>
   *          The type of the listener.
   */
  public static interface OnDone<T> {
    public void onDone(T t);
  }
  
  /**
   * A listener which will be called by the ClientProxy's methods. It will
   * implement a method which will be called inside a try-catch block.
   * 
   * @param <T>
   *          The return type of the method.
   */
  public static interface Executer<T> {
    public T execute();
  }
  
  /**
   * A listener which will be called by the ClientProxy's method in case of an
   * error.
   * 
   */
  public static abstract class OnError {
    private final Activity activity;
    
    /**
     * 
     * @param activity
     *          The activity in which the alert dialog should appear in case of
     *          an error.
     */
    public OnError(final Activity activity) {
      this.activity = activity;
    }
    
    /**
     * This method will be called when an error occurred. Please notice that
     * this method will be called before an alert dialog will be shown on
     * screen.
     */
    public abstract void beforeHandlingError();
    
    public final void onError() {
      beforeHandlingError();
      new AlertDialog.Builder(activity).setView(activity.getLayoutInflater().inflate(R.layout.ji_connection_error_dialog, null))
          .setNeutralButton("OK", new OnClickListener() {
            @Override public void onClick(final DialogInterface dialog, final int which) {
              // Do nothing
            }
          }).create().show();
    }
  }
  
  /**
   * An extension to the abstract AsyncTask class. An implementation to
   * onPostExecute is added in order to support the OnDone listener parameter
   * 
   * @param <T>
   *          The result type of the AsyncTask
   */
  public static class OnDoneAsyncTask<T> extends AsyncTask<Void, Void, T> {
    private final OnDone<T> onDone;
    private final Executer<T> execute;
    private final OnError onError;
    private Exception error;
    
    public OnDoneAsyncTask(final Executer<T> execute, final OnDone<T> onDone, final OnError onError) {
      this.execute = execute;
      this.onDone = onDone;
      this.onError = onError;
      error = null;
    }
    
    @Override protected final T doInBackground(final Void... params) {
      try {
        return execute.execute();
      } catch (final Exception e) {
        error = e;
      }
      return null;
    }
    
    @Override protected void onPostExecute(final T t) {
      if (error != null) {
        Log.e("ClientProxy", "Error: network exception!");
        error.printStackTrace();
        if (onError != null)
          onError.onError();
        return;
      }
      if (onDone != null)
        onDone.onDone(t);
    }
  }
}
