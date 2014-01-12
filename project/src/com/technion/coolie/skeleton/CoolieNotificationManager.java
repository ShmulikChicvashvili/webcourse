package com.technion.coolie.skeleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.technion.coolie.CoolieModuleManager;
import com.technion.coolie.CoolieNotification;
import com.technion.coolie.CoolieNotification.Priority;
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

	public final static String CALLED_BY_SINGLE_NOTIFICATION = "CALLED_BY_SINGLE_NOTIFICATION";
	public final static String CALLER_SINGLE_NOTIFICATION_ID = "CALLER_SINGLE_NOTIFICATION_ID";
	
	public final static String CALLED_BY_STACKED_NOTIFICATION = "CALLED_BY_STACKED_NOTIFICATION";
	public final static String CALLED_STACKED_NOTIFICATION_IDS = "CALLED_STACKED_NOTIFICATION_IDS";

	
	
	
	private static List<Notif> waitingNotifications = new ArrayList<Notif>();
	private static List<Notif> feeds = new ArrayList<Notif>();
	private static Context mContext;
	
	public static int nextId = 1;
	
	public static class Notif
	{
		Activity resultActivity;
		String title;
		String text;
		
		CoolieModule module;
		Priority priority;

		int id;
		
		int day;
		int month;
		int year;
		
		int hour;
		int minutes;
		int seconds;
		
		public Notif(CoolieModule module, String title, String text, Priority p, Activity resultActivity)
		{
			this.resultActivity = resultActivity;
			id = getNextId();
			this.priority = p;
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
	
	public static void addNewNotif(String title, String text, Activity resultActivity, boolean showInFeed, Priority p, Context c)
	{
		mContext = c;
		Notif n = new Notif(CoolieModuleManager.getMyModule(resultActivity.getClass()), title, text, p, resultActivity);
		
		if(showInFeed)
		{
			CoolieNotificationManager.addToFeedList(n);
		}
		/*if(p == Priority.IMMEDIATELY)
		{*/
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
		    .setSmallIcon(n.module.getPhotoRes())
		    .setContentTitle(title)
		    .setContentText(text);
			
			CoolieNotificationManager.pushNotification(n, mBuilder);
		//}
		/*else
		{
			CoolieNotificationManager.addToNotificationList(mTitle, mText, mPriority, mContext);
		}*/
	}
	
	private static void pushNotification(Notif n, NotificationCompat.Builder builder)
	{
		Intent resultIntent = new Intent(mContext, n.resultActivity.getClass());
		resultIntent.putExtra(CALLED_BY_SINGLE_NOTIFICATION, true);
		resultIntent.putExtra(CALLER_SINGLE_NOTIFICATION_ID, n.id);
		
		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(n.resultActivity.getClass());
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		builder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		Notification notif = builder.build();
		notif.flags = Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS;
		mNotificationManager.notify(n.id, notif);
	}
	
	private static void pushBigNotificationList()
	{
		/**Intent resultIntent = new Intent(c, ourResultActivity.getClass());
		resultIntent.putExtra(CALLED_BY_STACKED_NOTIFICATION, true);
		resultIntent.putExtra(CALLER_STACKED_NOTIFICATION_IDS, );*/
		
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
	
	private static void addToFeedList(Notif n)
	{
		feeds.add(n);
	}
	
	public static void removeFromFeedList(int id)
	{
		for(int i=0; i<feeds.size(); i++)
			if(feeds.get(i).id == id)
				feeds.remove(i);
	}
	
	
	private static void addToNotificationList(Notif n)
	{
		feeds.add(n);
	}
	
	public static List<Notif> getFeedList()
	{
		return feeds;
	}
	
	private static int getNextId()
	{
		return nextId++;
	}
}
