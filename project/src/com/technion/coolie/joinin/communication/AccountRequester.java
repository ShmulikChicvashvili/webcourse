package com.technion.coolie.joinin.communication;


import java.util.List;

import com.technion.coolie.joinin.communication.ClientProxy.Executer;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnDoneAsyncTask;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.Event;

/**
 * 
 * @author Shimon Kama
 * 
 */
public class AccountRequester {
  String SERVER_BASE_ADDRESS;
  String EVENT_SERVLET;
  String ACCOUNT_SERVLET;
  
  public AccountRequester(final String serverBase, final String eventServlet, final String accountServlet) {
    SERVER_BASE_ADDRESS = serverBase;
    EVENT_SERVLET = eventServlet;
    ACCOUNT_SERVLET = accountServlet;
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
   */
  public void login(final String username, final OnDone<ClientAccount> onDone, final OnError onError) {
    new OnDoneAsyncTask<ClientAccount>(new Executer<ClientAccount>() {
      @Override public ClientAccount execute() {
        return ClientAccount.toClientAccount(HttpRequester.httpGet(SERVER_BASE_ADDRESS + ACCOUNT_SERVLET + "/login?username="
            + username));
      }
    }, onDone, onError).execute();
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
   */
  public void addAccount(final ClientAccount a, final OnDone<String> onDone, final OnError onError) {
    new OnDoneAsyncTask<String>(new Executer<String>() {
      @Override public String execute() {
        return HttpRequester.httpPut(SERVER_BASE_ADDRESS + ACCOUNT_SERVLET, a.toString());
      }
    }, onDone, onError).execute();
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
   */
  public void getEventsAttending(final String username, final OnDone<List<ClientEvent>> onDone, final OnError onError) {
    new OnDoneAsyncTask<List<ClientEvent>>(new Executer<List<ClientEvent>>() {
      @Override public List<ClientEvent> execute() {
        return ClientEvent.toList(Event.toEventList(HttpRequester.httpGet(SERVER_BASE_ADDRESS + EVENT_SERVLET + "/users?username="
            + username)));
      }
    }, onDone, onError).execute();
  }
  
  public void getAccountByUserName(final String username, final OnDone<ClientAccount> onDone, final OnError onError) {
    new OnDoneAsyncTask<ClientAccount>(new Executer<ClientAccount>() {
      @Override public ClientAccount execute() {
        return ClientAccount.toClientAccount(HttpRequester.httpGet(SERVER_BASE_ADDRESS + ACCOUNT_SERVLET + "?username=" + username));
      }
    }, onDone, onError).execute();
  }
}
