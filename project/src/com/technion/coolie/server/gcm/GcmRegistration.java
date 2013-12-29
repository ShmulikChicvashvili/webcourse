package com.technion.coolie.server.gcm;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.technion.coolie.server.Communicator;

public class GcmRegistration extends Activity {

  public static final String EXTRA_MESSAGE = "message";
  public static final String PROPERTY_REG_ID = "registration_id";
  private static final String PROPERTY_APP_VERSION = "appVersion";

  String SENDER_ID = "372487289738";

  static final String TAG = "Coolie Gcm";
  GoogleCloudMessaging gcm;
  AtomicInteger msgId = new AtomicInteger();
  SharedPreferences prefs;
  Context context;

  String regid;

  public void doRegistration(Context context_, boolean hasPlayServices) {
    context = context_;

    // Check device for Play Services APK. If check succeeds, proceed with
    // GCM registration.

    if (hasPlayServices == true) {
      gcm = GoogleCloudMessaging.getInstance(context);
      regid = getRegistrationId(context);

      if (regid.isEmpty()) {
        registerInBackground();
      }
    } else {
      Log.i(TAG, "No valid Google Play Services APK found.");
    }
  }

  /**
   * Gets the current registration ID for application on GCM service.
   * <p>
   * If result is empty, the app needs to register.
   * 
   * @return registration ID, or empty string if there is no existing
   *         registration ID.
   */
  private String getRegistrationId(Context context_) {
    final SharedPreferences prefs_ = getGCMPreferences(context_);
    String registrationId = prefs_.getString(PROPERTY_REG_ID, "");
    if (registrationId.isEmpty()) {
      Log.i(TAG, "Registration not found.");
      return "";
    }
    // Check if app was updated; if so, it must clear the registration ID
    // since the existing regID is not guaranteed to work with the new
    // app version.
    int registeredVersion = prefs_.getInt(PROPERTY_APP_VERSION,
        Integer.MIN_VALUE);
    int currentVersion = getAppVersion(context_);
    if (registeredVersion != currentVersion) {
      Log.i(TAG, "App version changed.");
      return "";
    }
    return registrationId;
  }

  /**
   * @return Application's version code from the {@code PackageManager}.
   */
  private static int getAppVersion(Context context) {
    try {
      PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
          context.getPackageName(), 0);
      return packageInfo.versionCode;
    } catch (NameNotFoundException e) {
      // should never happen
      throw new RuntimeException("Could not get package name: " + e);
    }
  }

  /**
   * @return Application's {@code SharedPreferences}.
   */
  private SharedPreferences getGCMPreferences(Context context) {
    // This sample app persists the registration ID in shared preferences, but
    // how you store the regID in your app is up to you.
    return context.getSharedPreferences("gcm regid", Context.MODE_PRIVATE);
  }

  /**
   * Registers the application with GCM servers asynchronously.
   * <p>
   * Stores the registration ID and app versionCode in the application's shared
   * preferences.
   */
  private void registerInBackground() {
    new AsyncTask<Void, Void, String>() {
      @Override
      protected String doInBackground(Void... params) {
        String msg = "";
        try {
          if (gcm == null) {
            gcm = GoogleCloudMessaging.getInstance(context);
          }
          regid = gcm.register(SENDER_ID);
          msg = "Device registered, registration ID=" + regid;

          // You should send the registration ID to your server over HTTP,
          // so it can use GCM/HTTP or CCS to send messages to your app.
          // The request to your server should be authenticated if your app
          // is using accounts.
          sendRegistrationIdToBackend();

          // For this demo: we don't need to send it because the device
          // will send upstream messages to a server that echo back the
          // message using the 'from' address in the message.

          // Persist the regID - no need to register again.
          storeRegistrationId(context, regid);
        } catch (IOException ex) {
          msg = "Error: " + ex.getMessage();
          // TODO: add recovery if service not available
          // If there is an error, don't just keep trying to register.
          // Require the user to click a button again, or perform
          // exponential back-off.
        }
        return msg;
      }

      @Override
      protected void onPostExecute(String msg) {
        Log.i(TAG, msg + "\n");
      }
    }.execute(null, null, null);

  }

  /**
   * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
   * or CCS to send messages to your app. Not needed for this demo since the
   * device sends upstream messages to a server that echoes back the message
   * using the 'from' address in the message.
   */
  void sendRegistrationIdToBackend() {
    Log.i(TAG, "sendRegistrationIdToBackend: " + regid);
    Communicator.execute("GcmServlet", "regId", regid, "message", "coooooooo");
  }

  /**
   * Stores the registration ID and app versionCode in the application's
   * {@code SharedPreferences}.
   * 
   * @param context_
   *          application's context.
   * @param regId
   *          registration ID
   */
  void storeRegistrationId(Context context_, String regId) {
    final SharedPreferences prefs_ = getGCMPreferences(context_);
    int appVersion = getAppVersion(context_);
    Log.i(TAG, "Saving regId: " + regId);
    Log.i(TAG, "Saving regId on app version " + appVersion);
    SharedPreferences.Editor editor = prefs_.edit();
    editor.putString(PROPERTY_REG_ID, regId);
    editor.putInt(PROPERTY_APP_VERSION, appVersion);
    editor.commit();
  }
}
