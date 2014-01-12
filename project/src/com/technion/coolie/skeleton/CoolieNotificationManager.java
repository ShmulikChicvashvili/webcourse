package com.technion.coolie.skeleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.technion.coolie.CoolieModuleManager;
import com.technion.coolie.CoolieNotification;
import com.technion.coolie.CooliePriority;
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
import android.os.Handler;
import android.text.Html;

public class CoolieNotificationManager {

	public final static String CALLED_BY_SINGLE_NOTIFICATION = "CALLED_BY_SINGLE_NOTIFICATION";
	public final static String CALLER_SINGLE_NOTIFICATION_ID = "CALLER_SINGLE_NOTIFICATION_ID";
	
	public final static String CALLED_BY_STACKED_NOTIFICATION = "CALLED_BY_STACKED_NOTIFICATION";
	public final static String CALLED_STACKED_NOTIFICATION_IDS = "CALLED_STACKED_NOTIFICATION_IDS";
		
	
	
	private static final int INTERVAL_IMMEDIATELY = 5000; // 1 Second
	private static final int INTERVAL_IN_AN_HOUR = 3600000; // 1 hour
	private static final int INTERVAL_IN_A_DAY = 86400000; // 1 day

	private static List<Notif> waitingNotifications_immediately = new ArrayList<Notif>();
	private static List<Notif> waitingNotifications_in_an_hour = new ArrayList<Notif>();
	private static List<Notif> waitingNotifications_in_a_day = new ArrayList<Notif>();
	
	private static List<Notif> feeds = new ArrayList<Notif>();
	private static Context mContext;
	
	private static Handler handler = new Handler();

	public static class MyRunnable implements Runnable {
		private CooliePriority p;

		public MyRunnable(CooliePriority p) {
			this.p = p;
		}

		public void run() {
			pushBigNotificationList(p);
		}
	}

	private static MyRunnable runnable_immediately;
	private static MyRunnable runnable_in_an_hour;
	private static MyRunnable runnable_in_a_day;
	
	private static boolean handlerPostDelayed = false;
	
	public static int nextId = 1;
	public static int nextStackedId = 50000;
	
	public static class Notif
	{
		Activity resultActivity;
		String title;
		String text;
		
		CoolieModule module;
		CooliePriority priority;

		int id;
		
		int day;
		int month;
		int year;
		
		int hour;
		int minutes;
		int seconds;
		
		public Notif(CoolieModule module, String title, String text, CooliePriority p, Activity resultActivity)
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
	
	public static void addNewNotif(String title, String text,
			Activity resultActivity, boolean showInFeed,CooliePriority p, Context c) {
		mContext = c;
		Notif n = new Notif(CoolieModuleManager.getMyModule(resultActivity
				.getClass()), title, text, p, resultActivity);

		if (showInFeed) {
			CoolieNotificationManager.addToFeedList(n);
		}
		
		switch(p)
		{
		case IMMEDIATELY:
			/*
			 * NotificationCompat.Builder mBuilder = new
			 * NotificationCompat.Builder(mContext)
			 * .setSmallIcon(n.module.getPhotoRes()) .setContentTitle(title)
			 * .setContentText(text);
			 * 
			 * CoolieNotificationManager.pushNotification(n, mBuilder);
			 */

			waitingNotifications_immediately.add(n);

			if (runnable_immediately == null) {
				runnable_immediately = new MyRunnable(CooliePriority.IMMEDIATELY);
				handler.postDelayed(runnable_immediately, INTERVAL_IMMEDIATELY);
			}
			break;
		case IN_AN_HOUR:
				waitingNotifications_in_an_hour.add(n);
				if (runnable_in_an_hour == null) {
					runnable_in_an_hour= new MyRunnable(CooliePriority.IN_AN_HOUR);
					handler.postDelayed(runnable_in_an_hour, INTERVAL_IN_AN_HOUR);
				}
		break;
			case IN_A_DAY:
				if(p ==CooliePriority.IN_A_DAY){
					waitingNotifications_in_a_day.add(n);
					if (runnable_in_a_day == null) {
						runnable_in_a_day = new MyRunnable(CooliePriority.IN_A_DAY);
						handler.postDelayed(runnable_in_a_day, INTERVAL_IN_A_DAY);
					}
				}
		break;
			/*NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					mContext).setSmallIcon(n.module.getPhotoRes())
					.setContentTitle(title).setContentText(text);

			CoolieNotificationManager.pushNotification(n, mBuilder);*/

			// CoolieNotificationManager.addToNotificationList(mTitle, mText,
			// mPriority, mContext);
		}
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
	
	private static void pushBigNotificationList(CooliePriority p)
	{
		List<Notif> waiting = null;
		switch(p)
		{
		case IMMEDIATELY:
			waiting = waitingNotifications_immediately;
			break;
		case IN_AN_HOUR:
			waiting = waitingNotifications_in_an_hour;
			break;
		case IN_A_DAY:
			waiting = waitingNotifications_in_a_day;
			break;
		}
		
		if(waiting.size() == 1)
		{
			Notif n = waiting.get(0);
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					mContext).setSmallIcon(n.module.getPhotoRes())
					.setContentTitle(n.title).setContentText(n.text);

			CoolieNotificationManager.pushNotification(n, mBuilder);
			pushNotification(n, mBuilder);
			return;
		}
		Class<?> resultClass = com.technion.coolie.skeleton.MainActivity.class;
		Intent resultIntent = new Intent(mContext, resultClass);
		resultIntent.putExtra(CALLED_BY_STACKED_NOTIFICATION, true);
		int[] ids = new int[waiting.size()];
		for(int i=0; i<waiting.size(); i++)
		{
			Notif n = waiting.get(i);
			ids[i] = n.id;
		}
		resultIntent.putExtra(CALLED_STACKED_NOTIFICATION_IDS, ids);
		
		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(resultClass);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
				
		NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
		.setSmallIcon(R.drawable.ic_launcher)	//TODO CHANGE
		.setContentTitle(mContext.getString(R.string.skel_group_notif_title))
		.setContentText("Coolie")
		.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher));
		
		NotificationCompat.InboxStyle inboxStyle =
		        new NotificationCompat.InboxStyle();
				
		StringBuilder summery = new StringBuilder(mContext.getString(R.string.skel_notification_from));
		
		for(int i=0; i<waiting.size(); i++)
		{
			Notif n = waiting.get(i);
			inboxStyle.addLine(Html.fromHtml("<b>"+n.module.getName(mContext)+"</b>  "+n.title));
			summery.append(" " + n.module.getName(mContext)+",");
		}
		summery.deleteCharAt(summery.length()-1);
		summery.append(".");
		
		inboxStyle.setSummaryText(mContext.getString(R.string.skel_notification_click_to_view));
		
		builder.setContentText(summery);
		builder.setStyle(inboxStyle);
		
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		builder.setContentIntent(resultPendingIntent);
		
				NotificationManager mNotificationManager =
		    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notif = builder.build();
		notif.flags = Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS;
		mNotificationManager.notify(getNextStackedId(), notif);
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
	
	
	/*private static void addToNotificationList(Notif n)
	{
		waitingNotifications.add(n);
	}*/
	
	public static List<Notif> getFeedList()
	{
		return feeds;
	}
	
	private static int getNextId()
	{
		return nextId++;
	}
	
	private static int getNextStackedId()
	{
		return nextStackedId;
	}
}
