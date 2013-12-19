package com.technion.coolie.joinin;

import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.NotificationsClass;
import com.technion.coolie.joinin.data.SendNotification;

import android.app.NotificationManager;
import android.content.Context;

/**
 * 
 * An explanation for the notify function of the notification according to the
 * certains TAGS
 * 
 * notify(ClientAccount loggedAccount, ClientAccount otherAccount, ClientEvent
 * event, String message, String tag, Context context, NotificationManager
 * notificationManager)
 * 
 * In all of these cases the context and the notificationManager are: context =
 * this; notificationManager = (NotificationManager)
 * getSystemService(Context.NOTIFICATION_SERVICE);
 * 
 * Case tag=
 * 
 * TAG_EVENT_CANCELED: loggedAccount - the logged account otherAccount - NULL
 * event - the event which was canceled message - NULL
 * 
 * TAG_USER_JOINED_EVENT: loggedAccount - the logged account otherAccount - the
 * new user which just joined my event event - the event which the new user
 * joined to message - NULL
 * 
 * TAG_USER_LEFT_EVENT: loggedAccount - the logged account otherAccount - the
 * user who just left my event event - the event which the user left from
 * message - NULL
 * 
 * TAG_UPDATED_EVENT: loggedAccount - the logged account otherAccount - NULL
 * event - the event which was updated message - NULL
 * 
 * TAG_INTERESTING_EVENT: loggedAccount - the logged account otherAccount - NULL
 * event - the newly created event which might interest the logged account
 * message - NULL
 * 
 * TAG_EVENT_REMINDER: loggedAccount - the logged account otherAccount - NULL
 * event - the upcoming event message - NULL
 * 
 * TAG_EVENT_MESSAGE: loggedAccount - the logged account otherAccount - the
 * message sender event - the event which the message sent on its wall message -
 * the message
 * 
 */
public enum GCMTag implements SendNotification, GCMActions {
  EVENT_CANCELED {
    @Override public String toString() {
      return "Event canceled";
    }
    
    @Override public void send(final ClientAccount loggedAccount, final ClientAccount otherAccount, final ClientEvent event,
        final String message, final Context context, final NotificationManager notifMang) {
      NotificationsClass.createCanceledEventNotification(event, loggedAccount, context, notifMang);
    }
    
    @Override public String getAction() {
      return GCMActions.ACTION_DELETED;
    }
  },
  USER_JOINED_EVENT {
    @Override public String toString() {
      return "User joined event";
    }
    
    @Override public void send(final ClientAccount loggedAccount, final ClientAccount otherAccount, final ClientEvent event,
        final String messag, final Context context, final NotificationManager notifMang) {
      NotificationsClass.createJoinToMyEventNotification(event, loggedAccount, otherAccount, context, notifMang);
    }
    
    @Override public String getAction() {
      return GCMActions.ACTION_ATTEND;
    }
  },
  USER_LEFT_EVENT {
    @Override public String toString() {
      return "User left event";
    }
    
    @Override public void send(final ClientAccount loggedAccount, final ClientAccount otherAccount, final ClientEvent event,
        final String message, final Context context, final NotificationManager notifMang) {
      NotificationsClass.createLeftMyEventNotification(event, loggedAccount, otherAccount, context, notifMang);
    }
    
    @Override public String getAction() {
      return GCMActions.ACTION_UNATTEND;
    }
  },
  UPDATED_EVENT {
    @Override public String toString() {
      return "Updated event";
    }
    
    @Override public void send(final ClientAccount loggedAccount, final ClientAccount otherAccount, final ClientEvent event,
        final String message, final Context context, final NotificationManager notifMang) {
      NotificationsClass.createUpdatedEventNotification(event, loggedAccount, context, notifMang);
    }
    
    @Override public String getAction() {
      return GCMActions.ACTION_EDITED;
    }
  },
  INTERESTING_EVENT {
    @Override public String toString() {
      return "Interesting event";
    }
    
    @Override public void send(final ClientAccount loggedAccount, final ClientAccount otherAccount, final ClientEvent event,
        final String message, final Context context, final NotificationManager notifMang) {
      NotificationsClass.createInterestingEventNotification(event, loggedAccount, context, notifMang);
    }
    
    @Override public String getAction() {
      return GCMActions.ACTION_INTEREST;
    }
  },
  EVENT_REMINDER {
    @Override public String toString() {
      return "Event reminder";
    }
    
    @Override public void send(final ClientAccount loggedAccount, final ClientAccount otherAccount, final ClientEvent event,
        final String message, final Context context, final NotificationManager notifMang) {
      NotificationsClass.createReminderForEventNotification(event, loggedAccount, context, notifMang);
    }
    
    @Override public String getAction() {
      return GCMActions.ACTION_REMINDER;
    }
  },
  EVENT_MESSAGE {
    @Override public String toString() {
      return "Event message";
    }
    
    @Override public void send(final ClientAccount loggedAccount, final ClientAccount otherAccount, final ClientEvent event,
        final String message, final Context context, final NotificationManager notifMang) {
      NotificationsClass.createMessageNotification(loggedAccount, otherAccount, message, event, context, notifMang);
    }
    
    @Override public String getAction() {
      return GCMActions.ACTION_ADD_MESSAGE;
    }
  }
}
