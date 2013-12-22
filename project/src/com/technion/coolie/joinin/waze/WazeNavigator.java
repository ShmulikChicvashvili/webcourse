package com.technion.coolie.joinin.waze;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class WazeNavigator {
  /**
   * Checks whether waze is installed
   * 
   * @param a
   *          - an activity. The context of the call.
   * @return whether waze is installed.
   */
  public static boolean checkForWaze(final Activity a) {
    try {
      a.getPackageManager().getApplicationInfo("com.waze", 0);
      return true;
    } catch (final NameNotFoundException e) {
      return false;
    }
  }
  
  /**
   * Navigates to a specified location from current location using waze.
   * 
   * @param a
   *          - The calling activity
   * @param latitude
   *          - The latitude of the destination (in degrees)
   * @param longitude
   *          - the longitude of the destination (in degrees)
   * 
   * @throws android.content.ActivityNotFoundException
   *           if waze isn't installed
   */
  public static void navigateTo(final Activity a, final double latitude, final double longitude) {
    a.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("waze://?ll=" + latitude + "," + longitude + "&navigate=yes")));
  }
}
