package com.technion.coolie.joinin.facebook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.data.ClientAccount;

/**
 * 
 * @author Alon Brifman
 * 
 *         This class encapsulates Facebook login. It implements static methods
 *         and holds a single connected user.
 * 
 *         IMPORTANT: Any activity wishing to use this class, should call
 *         onResult in her onActivityResult method
 * 
 * 
 */
public class FacebookLogin {
  static ClientAccount loggedUser;
  static boolean isInternalUser;
  
  /**
   * 
   * @return whether the logged user is an internal (a known account in the
   *         phone) or an external user
   */
  public static boolean isInternalUser() {
    return isInternalUser;
  }
  
  /**
   * An interface implementing a function to be called after login is done
   * 
   */
  public interface OnLoginDone {
    public void loginCallback(ClientAccount a);
  }
  
  /**
   * Any Activity wishing to use this class should call this method in its
   * onActivityResult. Takes care of inner session changes when resuming from
   * facebook app.
   * 
   * @param a
   *          - the calling activity
   * @param requestCode
   *          - requestCode from onActivityResult
   * @param resultCode
   *          - resultCode from onActivityResult
   * @param data
   *          - data from onActivityResult
   */
  public static void onResult(final Activity a, final int requestCode, final int resultCode, final Intent data) {
    if (Session.getActiveSession() != null)
      Session.getActiveSession().onActivityResult(a, requestCode, resultCode, data);
  }
  
  /**
   * Gets the logged user
   * 
   * @return A ClientAccount representing the user, or null if no one is logged
   *         in.
   */
  public static ClientAccount getLoggedUser() {
    return loggedUser;
  }
  
  /**
   * Shows a loading bar on the given activity and returns a new callback that
   * operates the same as the given one, but dismisses the loading bar first.
   * 
   * @param a
   *          - the calling activity
   * @param callback
   *          - a callback to be done after dismissing the loading bar.
   * @return A modified callback that first dismisses the loading bar and then
   *         works as usual.
   */
  private static OnLoginDone showLoadingBar(final Activity a, final OnLoginDone callback) {
    final ProgressDialog pd = ProgressDialog.show(a, "Join-In", "Logging to Facebook");
    pd.setCancelable(false);
    return new OnLoginDone() {
      @Override public void loginCallback(final ClientAccount ca) {
        pd.dismiss();
        callback.loginCallback(ca);
      }
    };
  }
  
  /**
   * Perform login using Facebook. If the Facebook app exists and there is a
   * logged user he will be logged in automatically, otherwise the app will be
   * called to perform login. If the app doesn't exist it will use the
   * web-browser to login. Function will return immediately if the user is
   * already logged in and will call callback.
   * 
   * WARNING: To be called only from the UI thread!!!!
   * 
   * @param a
   *          - The context of the login
   * 
   * @param callback
   *          - a callback to perform after login was done.
   */
  public static void login(final Activity a, final OnLoginDone callback) {
    final OnLoginDone wrapper = showLoadingBar(a, callback);
    final Session s = loggedUser == null ? setNewSession(a) : activeSession(a);
    isInternalUser = true;
    if (!s.isOpened())
      s.openForRead(new Session.OpenRequest(a).setCallback(new MySessionCallback(a, wrapper)));
    else
      performLogin(a, wrapper, loggedUser.getUsername(), loggedUser.getFacebookId(), loggedUser.getName());
  }
  
  /**
   * Logs out any existing user, and uses the web-browser to login.
   * 
   * WARNING: To be called only from the UI thread!!!!
   * 
   * @param a
   *          - The context of the login
   * @param callback
   *          - a callback to perform after login was done.
   */
  public static void switchUser(final Activity a, final OnLoginDone callback) {
    final OnLoginDone wrapper = showLoadingBar(a, callback);
    final Session s = setNewSession(a);
    isInternalUser = false;
    s.openForRead(new Session.OpenRequest(a).setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO).setCallback(
        new MySessionCallback(a, wrapper)));
  }
  
  /**
   * Logs out the current user. After this getLoggedUser will return null.
   * NOTICE: This doesn't logout the user from facebook. Just logs him out from
   * our app.
   */
  public static void logout() {
    loggedUser = null;
    final Session s = Session.getActiveSession();
    if (s != null && !s.isClosed())
      s.closeAndClearTokenInformation();
  }
  
  /**
   * Closes an existing session and opens a new one.
   * 
   * @param c
   *          - The context of the session
   * @return The new session
   */
  private static Session setNewSession(final Context c) {
    if (Session.getActiveSession() != null)
      Session.getActiveSession().closeAndClearTokenInformation();
    final Session $ = new Session(c);
    Session.setActiveSession($);
    return $;
  }
  
  /**
   * Gets the active session. Opens a new one if there is no such session.
   * 
   * @param c
   *          - The context of the session
   * @return the active session
   */
  private static Session activeSession(final Context c) {
    Session $ = Session.getActiveSession();
    if ($ == null || $.isClosed()) {
      $ = new Session(c);
      Session.setActiveSession($);
    }
    return $;
  }
  
  /**
   * @return whether there is an open session with the user
   */
  public static boolean hasOpenSession() {
    return Session.getActiveSession() != null && Session.getActiveSession().isOpened();
  }
  
  /**
   * Reauthorizes a user, given that his session was closed.
   * 
   * @param a
   *          - The activity in its context the session will be reopened
   * @param callback
   *          - implements what to do after reauthorization was done.
   */
  public static void reauthorize(final Activity a, final OnLoginDone callback) {
    if (loggedUser == null || isInternalUser)
      login(a, callback);
    else
      switchUser(a, callback);
  }
  
  /**
   * Implements a callback to perform when a session changes a state.
   * 
   */
  private static class MySessionCallback implements Session.StatusCallback {
    OnLoginDone finishCallback;
    final Activity a;
    
    /**
     * 
     * @param ac
     *          - the context of the call
     * @param callback
     *          - a call back to perform after process is done
     */
    public MySessionCallback(final Activity ac, final OnLoginDone callback) {
      super();
      finishCallback = callback;
      a = ac;
    }
    
    /**
     * After session is opened read the users details. If the session was closed
     * modify that there is no user in the system. If the session was closed due
     * to an error call the given callback.
     */
    @Override public void call(final Session session, final SessionState state, final Exception exception) {
      if (session.isOpened())
        Request.executeMeRequestAsync(session, new setUser(a, finishCallback));
      if (!session.isClosed())
        return;
      loggedUser = null;
      if (session.getState().equals(SessionState.CLOSED_LOGIN_FAILED))
        finishCallback.loginCallback(loggedUser);
    }
  }
  
  /**
   * Performs login using the GAE server. That is, asks the server for the
   * newest data about the logging user. Subscribes him if he doesn't already
   * exist
   * 
   * @param a
   *          - The activity from which the login is performed
   * 
   * @param finishCallback
   *          - callback to perform after async communication is done
   * @param username
   *          - the user's username
   * @param Id
   *          - the user's id on facebook
   * @param name
   *          - the user's real name
   */
  static void performLogin(final Activity a, final OnLoginDone finishCallback, final String username, final String Id,
      final String name) {
    final ClientProxy.OnError oe = new ClientProxy.OnError(a) {
      @Override public void beforeHandlingError() {
        loggedUser = null;
        finishCallback.loginCallback(loggedUser);
      }
    };
    ClientProxy.login(username, new ClientProxy.OnDone<ClientAccount>() {
      /**
       * An onDone class that will be called after login request from server.
       * Will set the current logged user to the returned account, or will
       * register the user if no such username exists
       */
      @Override public void onDone(final ClientAccount ca) {
        if (ca != null) { // User exists, set him as the user and notify that
                          // login has ended.
          loggedUser = ca;
          finishCallback.loginCallback(loggedUser);
          return;
        }
        // User does not exist, add him to the database
        final ClientAccount newAccount = new ClientAccount(username, Id, name);
        ClientProxy.addAccount(newAccount, new ClientProxy.OnDone<String>() {
          // An onDone class that will be called after the server added the
          // client.
          @Override public void onDone(final String userNameResponce) {
            if (!userNameResponce.equals(username)) // sanity
                                                    // check!!
              throw new RuntimeException("User name returned from server diffrent from facebook username");
            loggedUser = newAccount;
            finishCallback.loginCallback(loggedUser);
          }
        }, oe);
      }
    }, oe);
  }
  
  /**
   * Implements a callback when detail-mining from facebook is done.
   * 
   */
  private static class setUser implements Request.GraphUserCallback {
    OnLoginDone finishCallback;
    Activity a;
    
    public setUser(final Activity ac, final OnLoginDone callback) {
      super();
      finishCallback = callback;
      a = ac;
    }
    
    /**
     * Set the user in the system according to the details granted
     */
    @Override public void onCompleted(final GraphUser user, final Response response) {
      if (user == null) {// Failed to login
        loggedUser = null;
        finishCallback.loginCallback(loggedUser);
        return;
      }
      performLogin(a, finishCallback, user.getUsername(), user.getId(), user.getName());
    }
  }
}
