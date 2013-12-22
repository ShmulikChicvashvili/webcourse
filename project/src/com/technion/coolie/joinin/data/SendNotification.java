package com.technion.coolie.joinin.data;

import android.app.NotificationManager;
import android.content.Context;

/**
 * Interface used to allow sending notifications.
 * 
 * @author Shimon Kama
 * 
 */
public interface SendNotification {
  /**
   * Sends a notification to the user.
   * 
   * @param loggedAccount
   *          the currently logged account
   * @param otherAccount
   *          the related account received from the GCM message
   * @param event
   *          the related event recevied from the GCM message
   * @param message
   *          the message received from GCM
   * @param context
   *          android context
   * @param notifMang
   *          a notification manager
   */
  public void send(final ClientAccount loggedAccount, final ClientAccount otherAccount, final ClientEvent event,
      final String message, final Context context, final NotificationManager notifMang);
}
