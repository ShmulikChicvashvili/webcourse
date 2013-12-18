package com.technion.coolie.joinin.data;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.technion.coolie.R;
import com.technion.coolie.joinin.calander.CalendarEventDatabase.NotFoundException;
import com.technion.coolie.joinin.calander.CalendarHandler;
import com.technion.coolie.joinin.subactivities.EventActivity;
import com.technion.coolie.joinin.subactivities.MyEventsActivity;
import com.technion.coolie.joinin.subactivities.SettingsActivity;

public class NotificationsClass extends Application {
  protected static Context someContext;
  private static final int NOTIF_INTERESTIN_EVENT_INT = 1;
  private static final int NOTIF_JOINED_INT = 2;
  private static final int NOTIF_UPDATE_INT = 3;
  private static final int NOTIF_CANCEL_INT = 4;
  private static final int NOTIF_LEFT_INT = 5;
  private static final int NOTIF_REMIND_INT = 6;
  private static final int NOTIF_MESSAGE_INT = 7;
  private static int icon = R.drawable.ji_notification;
  
  public NotificationsClass(final Context c) {
    NotificationsClass.someContext = c;
  }
  
  /**
   * Raise a notification saying a new event was created in your area which
   * might interest you. (cause its in your favorite categories)
   * 
   * @param e
   *          - the new event
   * @param a
   *          - the logged account
   */
  @SuppressWarnings("deprecation") public static void createInterestingEventNotification(final ClientEvent e,
      final ClientAccount a, final Context context, final NotificationManager nm) {
    final Notification myNotification = new Notification(icon, "A new Event near you!", System.currentTimeMillis());
    final Intent myIntent = new Intent(context, EventActivity.class);
    myIntent.putExtra("event", e);
    myIntent.putExtra("account", a);
    myIntent.putExtra(EventActivity.INTENT_TAB_POS, EventActivity.TAB_EVENT_INFO);
    myIntent.setAction(e.getName() + Math.random());
    myNotification.defaults |= Notification.DEFAULT_SOUND;
    myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
    myNotification.setLatestEventInfo(context, "A new " + e.getEventType().toString() + " Event",
        "Check this event near you. you might want to join in=)",
        PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK));
    nm.notify(e.getName(), NOTIF_INTERESTIN_EVENT_INT, myNotification);
  }
  
  /**
   * Raise a notification when some other user enrolls to my (me) event
   * 
   * @param e
   *          - the event someone joined to
   * @param me
   *          - the owner of the event (the one that gets the notification)
   * @param joined
   *          - the newly joined member to the event ( can be transformed into
   *          only string )
   */
  @SuppressWarnings("deprecation") public static void createJoinToMyEventNotification(final ClientEvent e, final ClientAccount me,
      final ClientAccount joined, final Context context, final NotificationManager nm) {
    if (!SettingsActivity.EnabledNotifications(context) || !SettingsActivity.isJoinEnabledNotifications(context))
      return;
    final Notification myNotification = new Notification(icon, "Someone joined your event!", System.currentTimeMillis());
    final Intent myIntent = new Intent(context, EventActivity.class);
    myIntent.putExtra("event", e);
    myIntent.putExtra("account", me);
    myIntent.putExtra(EventActivity.INTENT_TAB_POS, EventActivity.TAB_EVENT_ATTENDING);
    myIntent.setAction(e.getName() + joined.getFacebookId() + Math.random());
    myNotification.sound = Uri.parse(PreferenceManager.getDefaultSharedPreferences(context).getString("all_notifications_ringtone",
        ""));
    if (SettingsActivity.isVibarate(context))
      myNotification.defaults |= Notification.DEFAULT_VIBRATE;
    myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
    myNotification.setLatestEventInfo(context, joined.getName() + " has joined your Event=)",
        "click to see the event details including the participants",
        PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK));
    nm.notify(e.getName() + joined.getFacebookId(), NOTIF_JOINED_INT, myNotification);
  }
  
  /**
   * Raise a notification letting the user to know that an event he registered
   * to got updated
   * 
   * @param e
   *          - the event that was updated
   * @param a
   *          - the currently logged account
   */
  @SuppressWarnings("deprecation") public static void createUpdatedEventNotification(final ClientEvent e, final ClientAccount a,
      final Context context, final NotificationManager nm) {
    try {
      new CalendarHandler(context).updateEvent(context, e);
    } catch (final NotFoundException e1) {
      // The user didn't insert the event to his calendar
    }
    if (!SettingsActivity.EnabledNotifications(context) || !SettingsActivity.isUpdateEnabledNotifications(context))
      return;
    final Notification myNotification = new Notification(icon, "Evnet details updated!", System.currentTimeMillis());
    final Intent myIntent = new Intent(context, EventActivity.class);
    myIntent.putExtra("event", e);
    myIntent.putExtra("account", a);
    myIntent.putExtra(EventActivity.INTENT_TAB_POS, EventActivity.TAB_EVENT_INFO);
    myIntent.setAction(e.getName() + Math.random());
    myNotification.sound = Uri.parse(PreferenceManager.getDefaultSharedPreferences(context).getString("all_notifications_ringtone",
        ""));
    if (SettingsActivity.isVibarate(context))
      myNotification.defaults |= Notification.DEFAULT_VIBRATE;
    myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
    myNotification.setLatestEventInfo(context, e.getName() + " Event's details updated", "Click to see the new event's details",
        PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK));
    nm.notify(e.getName(), NOTIF_UPDATE_INT, myNotification);
  }
  
  /**
   * Raise a notification letting the user to know that an event he was
   * registered to was canceled pressing the notification will get him to
   * "MyEvents" page.
   * 
   * @param e
   *          - the event that got canceled (in order to know the event's name)
   * @param me
   *          - the currently logged account
   */
  @SuppressWarnings("deprecation") public static void createCanceledEventNotification(final ClientEvent e, final ClientAccount me,
      final Context context, final NotificationManager nm) {
    new CalendarHandler(context).deleteEvent(context, e);
    if (!SettingsActivity.EnabledNotifications(context) || !SettingsActivity.isCancelEnabledNotifications(context))
      return;
    final Notification myNotification = new Notification(icon, "Event has Canceled", System.currentTimeMillis());
    final Intent myIntent = new Intent(context, MyEventsActivity.class);
    myIntent.putExtra("event", e);
    myIntent.putExtra("account", me);
    myIntent.setAction(e.getName() + Math.random());
    myNotification.sound = Uri.parse(PreferenceManager.getDefaultSharedPreferences(context).getString("all_notifications_ringtone",
        ""));
    if (SettingsActivity.isVibarate(context))
      myNotification.defaults |= Notification.DEFAULT_VIBRATE;
    myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
    myNotification
        .setLatestEventInfo(context, e.getOwner() + " has canceled the Event=(", "click to see the events that are still On",
            PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK));
    nm.notify(e.getName(), NOTIF_CANCEL_INT, myNotification);
  }
  
  /**
   * Raise a notification that let the Event's owner to know that some user left
   * his event
   * 
   * @param e
   *          - the event HANAL
   * @param me
   *          - the currently logged account - which is the owner of the evnet
   * @param left
   *          - the one that left
   */
  @SuppressWarnings("deprecation") public static void createLeftMyEventNotification(final ClientEvent e, final ClientAccount me,
      final ClientAccount left, final Context context, final NotificationManager nm) {
    if (!SettingsActivity.EnabledNotifications(context) || !SettingsActivity.isLeaveEnabledNotifications(context))
      return;
    final Notification myNotification = new Notification(icon, "Someone left your event!=(", System.currentTimeMillis());
    final Intent myIntent = new Intent(context, EventActivity.class);
    myIntent.putExtra("event", e);
    myIntent.putExtra("account", me);
    myIntent.putExtra(EventActivity.INTENT_TAB_POS, EventActivity.TAB_EVENT_ATTENDING);
    myIntent.setAction(e.getName() + left.getFacebookId() + Math.random());
    myNotification.sound = Uri.parse(PreferenceManager.getDefaultSharedPreferences(context).getString("all_notifications_ringtone",
        ""));
    if (SettingsActivity.isVibarate(context))
      myNotification.defaults |= Notification.DEFAULT_VIBRATE;
    myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
    myNotification.setLatestEventInfo(context, left.getName() + " has left your Event=)",
        "click to see the event details including the participants",
        PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK));
    nm.notify(e.getName() + left.getFacebookId(), NOTIF_LEFT_INT, myNotification);
  }
  
  @SuppressWarnings("deprecation") public static void createReminderForEventNotification(final ClientEvent e,
      final ClientAccount a, final Context context, final NotificationManager nm) {
    final Notification myNotification = new Notification(icon, "Event is about to begin", System.currentTimeMillis());
    final Intent myIntent = new Intent(context, EventActivity.class);
    myIntent.putExtra("event", e);
    myIntent.putExtra("account", a);
    myIntent.putExtra(EventActivity.INTENT_TAB_POS, EventActivity.TAB_EVENT_INFO);
    myIntent.setAction(e.getName() + Math.random());
    myNotification.sound = Uri.parse(PreferenceManager.getDefaultSharedPreferences(context).getString("all_notifications_ringtone",
        ""));
    if (SettingsActivity.isVibarate(context))
      myNotification.defaults |= Notification.DEFAULT_VIBRATE;
    myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
    myNotification.setLatestEventInfo(context, e.getName() + " Event starts at: " + e.getWhen().getHour() + ":"
        + e.getWhen().getMinute(), "Click to see the event's details and how to get there!",
        PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK));
    nm.notify(e.getName(), NOTIF_REMIND_INT, myNotification);
  }
  
  /**
   * raise a notification when a messages i s sent to an event i am registered
   * to.
   * 
   * @param me
   *          - the current logined client
   * @param sender
   *          - the message's sender
   * @param message
   *          - the message
   * @param e
   *          - the event which the message was sent on its wall.
   */
  @SuppressWarnings("deprecation") public static void createMessageNotification(final ClientAccount me, final ClientAccount sender,
      final String message, final ClientEvent e, final Context context, final NotificationManager mm) {
    if (!SettingsActivity.EnabledNotifications(context) || !SettingsActivity.isMessageEnabledNotifications(context))
      return;
    final Notification myNotification = new Notification(icon, "TeamApp: Message from " + sender.getName(),
        System.currentTimeMillis());
    final Intent myIntent = new Intent(context, EventActivity.class);
    myIntent.putExtra("event", e);
    myIntent.putExtra("account", me);
    myIntent.putExtra(EventActivity.INTENT_TAB_POS, EventActivity.TAB_EVENT_MESSAGING);
    myIntent.setAction(e.getName() + sender.getFacebookId() + Math.random());
    myNotification.sound = Uri.parse(PreferenceManager.getDefaultSharedPreferences(context).getString("all_notifications_ringtone",
        ""));
    if (SettingsActivity.isVibarate(context))
      myNotification.defaults |= Notification.DEFAULT_VIBRATE;
    myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
    myNotification.setLatestEventInfo(context, "TeamApp Message", sender.getName() + ": "
        + EventMessage.toEventMessage(message).getText(),
        PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK));
    mm.notify(e.getName() + sender.getFacebookId(), NOTIF_MESSAGE_INT, myNotification);
  }
}
