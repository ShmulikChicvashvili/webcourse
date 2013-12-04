package com.technion.coolie.skeleton;

import java.util.ArrayList;
import java.util.List;

import com.technion.coolie.CollieNotification;
import com.technion.coolie.R;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class CoolieNotificationManager {

	private static List<Notif> waitingNotifications = new ArrayList<Notif>();
	private static Context mContext;
	
	public static int nextId = 1;
	
	private class Notif
	{
		String title;
		String text;
		//TODO add module
		CollieNotification.Priority priority;
		//TODO adding time
		
		public Notif(String title, String text, CollieNotification.Priority p)
		{
			this.title = title;
			this.text = text;
			this.priority = p;
		}
	}	
	
	public static void pushNotification(NotificationCompat.Builder builder, Activity resultActivity, Context c)
	{
		mContext = c;
		Intent resultIntent = new Intent(c, resultActivity.getClass());
		
		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(c);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(resultActivity.getClass());
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		builder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(getNextId(), builder.build());
	}
	
	public static int addToNotificationList(String title, String text, CollieNotification.Priority priority, Context c)
	{
		mContext = c;
		
		
		return getNextId();
	}
	
	public static void pushBigNotificationList()
	{
		NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
		.setSmallIcon(R.drawable.ic_launcher)	//TODO CHANGE
		.setContentTitle(mContext.getString(R.string.skel_group_notif_title))
		.setContentText("Coolie")
		.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher));
		
		NotificationCompat.InboxStyle inboxStyle =
		        new NotificationCompat.InboxStyle();
		
		for(Notif n : waitingNotifications)
		{
			inboxStyle.addLine("<b>"+n.title+"<\b>"+"	"+n.text);
		}
	}
	public static void addToFeedList(String title, String text, Activity resultActivity)
	{

	}
	public static int getNextId()
	{
		return nextId++;
	}
}
