package com.technion.coolie.skeleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.technion.coolie.CoolieModuleManager;
import com.technion.coolie.CoolieNotification;
import com.technion.coolie.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class CoolieNotificationManager {

	private static List<Notif> waitingNotifications = new ArrayList<Notif>();
	private static List<Feed> feeds = new ArrayList<Feed>();
	private static Context mContext;
	
	public static int nextId = 1;
	
	private static class Notif
	{
		String title;
		String text;
		
		CoolieModule module;
		CoolieNotification.Priority priority;
		Date date;
		
		public Notif(CoolieModule module, String title, String text, CoolieNotification.Priority p)
		{
			this.title = title;
			this.text = text;
			this.priority = p;
			this.module = module;
			this.date = Calendar.getInstance().getTime();
		}
	}
	
	public static class Feed
	{
		String title;
		String text;
		
		CoolieModule module;
		CoolieNotification.Priority priority;

		int day;
		int month;
		int year;
		
		int hour;
		int minutes;
		int seconds;
		
		public Feed(CoolieModule module, String title, String text)
		{
			this.title = title;
			this.text = text;
			this.module = module;
			Calendar c = Calendar.getInstance();
			day = c.DATE;
			month = c.MONTH;
			year = c.YEAR;
			hour = c.HOUR;
			minutes = c.MINUTE;
			seconds = c.SECOND;
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
		Notification notif = builder.build();
		notif.flags = Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS;
		mNotificationManager.notify(getNextId(), notif);
	}
	
	public static int addToNotificationList(String title, String text, CoolieNotification.Priority priority, Context c)
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
		
		builder.notify();
	}
	public static void addToFeedList(String title, String text, Activity resultActivity)
	{
		feeds.add(new Feed(CoolieModuleManager.getMyModule(resultActivity.getClass()), title, text));
	}
	
	public static List<Feed> getFeedList()
	{
		return feeds;
	}
	
	public static int getNextId()
	{
		return nextId++;
	}
}
