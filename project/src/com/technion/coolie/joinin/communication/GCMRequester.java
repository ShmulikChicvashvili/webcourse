package com.technion.coolie.joinin.communication;


import java.util.HashMap;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;

public class GCMRequester {
  private final String SERVER_BASE_ADDRESS;
  private final String GCMREGISTER_SERVLET;
  
  public GCMRequester(final String baseServer, final String GCMServlet) {
    SERVER_BASE_ADDRESS = baseServer;
    GCMREGISTER_SERVLET = GCMServlet;
  }
  
  public String gcmIsFoundNotAsync(final String regId, final String userName) {
    return HttpRequester.httpGet(SERVER_BASE_ADDRESS + GCMREGISTER_SERVLET + "?regId=" + regId + "&username=" + userName);
  }
  
  public void gcmIsFoundAsync(final String regId, final String userName, final OnDone<String> onDone) {
    new AsyncTask<String, String, String>() {
      @Override protected String doInBackground(final String... params) {
        Log.v("ClientProxy", "gcmIsFoundAsync doInBackground: " + params[0]);
        return gcmIsFoundNotAsync(params[0], params[1]);
      }
      
      @Override protected void onPostExecute(final String result) {
        super.onPostExecute(result);
        Log.v("ClientProxy", "gcmIsFoundAsync result: " + result);
        onDone.onDone(result);
      }
    }.execute(regId, userName);
  }
  
  public String gcmRegisterNotAsync(final String regId, final String userName) {
    final HashMap<String, String> message = new HashMap<String, String>();
    message.put("regId", regId);
    message.put("userName", userName);
    return HttpRequester
        .httpPut(SERVER_BASE_ADDRESS + GCMREGISTER_SERVLET + "?message=", new Gson().toJson(message, HashMap.class));
  }
  
  public void gcmRegisterAsync(final String regId, final String userName) {
    new AsyncTask<String, String, String>() {
      @Override protected String doInBackground(final String... params) {
        Log.v("ClientProxy", "gcmRegister doInBackground: " + params[0]);
        return gcmRegisterNotAsync(params[0], params[1]);
      }
      
      @Override protected void onPostExecute(final String result) {
        super.onPostExecute(result);
        Log.v("ClientProxy", "gcmRegister result: " + result);
      }
    }.execute(regId, userName);
  }
  
  public String gcmUnregisterNotAsync(final String regId, final String userName) {
    final HashMap<String, String> message = new HashMap<String, String>();
    message.put("regId", regId);
    message.put("userName", userName);
    return HttpRequester.httpDelete(SERVER_BASE_ADDRESS + GCMREGISTER_SERVLET + "?regId=" + regId + "&username=" + userName);
  }
  
  public void gcmUnregisterAsync(final String regId, final String userName) {
    new AsyncTask<String, String, String>() {
      @Override protected String doInBackground(final String... params) {
        Log.v("ClientProxy", "gcmUnregister doInBackground: " + params[0]);
        return gcmUnregisterNotAsync(params[0], params[1]);
      }
      
      @Override protected void onPostExecute(final String result) {
        super.onPostExecute(result);
        Log.v("ClientProxy", "gcmUnregister result: " + result);
      }
    }.execute(regId, userName);
  }
}
