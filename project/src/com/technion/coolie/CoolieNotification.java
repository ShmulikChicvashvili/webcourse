package com.technion.coolie;

import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieNotificationManager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class CoolieNotification {
	private Priority mPriority;
	
	private String mTitle;
	private String mText;
	private Activity mResultActivity;
	
	private Context mContext;
	private boolean mAddToFeeds;
	
	public enum Priority
	{
		IMMEDIATELY,
		IN_AN_HOUR,
		IN_A_DAY
	}
	
	/**
	 * Creates a notification with given params:
	 * @param title
	 * @param text
	 * @param resultActivity - the activity that will be called when the user click the notification.
	 * @param priority - the maximum time until the notification will be shown. 
	 * @param addToFeeds - should the notification should <b>also</b> be displayed in the news feed.
	 * @param context
	 */
	public CoolieNotification(String title, String text, Activity resultActivity, Priority priority, boolean addToFeeds, Context context)
	{
		 mTitle = title;
		 mText = text;
		 mResultActivity = resultActivity;
		 mPriority = priority;
		 mContext = context;
		 mAddToFeeds = addToFeeds;
	}
	
	public void sendNotification()
	{
		CoolieNotificationManager.addNewNotif(mTitle, mText, mResultActivity, mAddToFeeds, mPriority, mContext);
		/**if(mAddToFeeds)
		{
			CoolieNotificationManager.addToFeedList(mTitle, mText, mResultActivity);
		}
		/*if(mPriority == Priority.IMMEDIATELY)
		{//
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
		    .setSmallIcon(R.drawable.ic_launcher)	//TODO - CHANGE!
		    .setContentTitle(mTitle)
		    .setContentText(mText);
			
			CoolieNotificationManager.pushNotification(mBuilder, mResultActivity, mContext);
		/*} else
		{
			CoolieNotificationManager.addToNotificationList(mTitle, mText, mPriority, mContext);
		}*/
	}
	
	
}
