package com.technion.coolie.joinin;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.gson.Gson;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.GCMMessage;
import com.technion.coolie.joinin.map.MainMapActivity;

/**
 * Don't use any UI related operation such as toast. The service is not called
 * from the UI thread.
 */
public class GCMIntentService extends GCMBaseIntentService {
  /**
   * This value should be used when registering a BroadcastReceiver connected to
   * the GCM Actions. If this value is used as the receiver's priority, the
   * receiver would receive the message before the default handler (which will
   * raise a notification).
   */
  public static final int PRIORITY_BEFORE_DEFAULT_BROADCAST = 1000;
  private final BroadcastReceiver messageReceiver = new BroadcastReceiver() {
    @Override public void onReceive(final Context c, final Intent i) {
      final GCMMessage message = new Gson().fromJson(i.getStringExtra("message"), GCMMessage.class);
      message.getTag().send(getLoggedAccount(), message.getAccount() == null ? null : new ClientAccount(message.getAccount()),
          message.getEvent() == null ? null : new ClientEvent(message.getEvent()), message.getMessage(), c,
          (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE));
    }
  };
  
  public GCMIntentService() {
    super(MainMapActivity.SENDER_ID);
    Log.v("GCMIntentService", "GCMIntentService()");
  }
  
  @Override public void onCreate() {
    super.onCreate();
    final IntentFilter f = new IntentFilter();
    for (final GCMTag tag : GCMTag.values())
      f.addAction(tag.getAction());
    registerReceiver(messageReceiver, f);
  }
  
  @Override public void onDestroy() {
    unregisterReceiver(messageReceiver);
    super.onDestroy();
  }
  
  @Override protected void onError(final Context arg0, final String arg1) {
    Log.v("GCMIntentService", "onError()");
  }
  
  @Override protected void onMessage(final Context c, final Intent intent) {
    String m = null;
    try {
      m = URLDecoder.decode(intent.getStringExtra("message"), "UTF-16");
      Log.v("GCMIntentService", "onMessage() --------" + m);
      sendOrderedBroadcast(new Intent(new Gson().fromJson(m, GCMMessage.class).getTag().getAction()).putExtra("message", m), null);
    } catch (final UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * 
   * @return The logged account in this device
   */
  ClientAccount getLoggedAccount() {
    final SharedPreferences mTeamAppPref = getSharedPreferences(MainMapActivity.PREFS_NAME, 0);
    mTeamAppPref.edit().commit();
    return mTeamAppPref.contains("account") ? ClientAccount.fromJson(mTeamAppPref.getString("account", "")) : null;
  }
  
  @Override protected void onRegistered(final Context c, final String regId) {
    Log.v("GCMIntentService", "onRegistered() -- username: " + getLoggedAccount().getUsername() + " regId: " + regId);
    Log.v("GCMIntentService",
        "onRegistered() -- recieved: " + ClientProxy.gcmRegisterNotAsync(regId, getLoggedAccount().getUsername()));
  }
  
  @Override protected void onUnregistered(final Context c, final String regId) {
    Log.v("GCMIntentService", "onUnregistered()");
    Log.v("GCMIntentService",
        "onUnegistered() -- recieved: " + ClientProxy.gcmUnregisterNotAsync(regId, getLoggedAccount().getUsername()));
  }
}
