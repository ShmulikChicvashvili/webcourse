package com.technion.coolie.skeleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.R;
import android.app.Activity;

public enum CoolieModuleManager {
	STUDYBUDDY("Study Buddy", 0, "description",  R.drawable.alert_dark_frame, 0, MainActivity.class, "com.technion.coolie", R.layout.preference_category),
	TECHLIBRARY("Study Buddy", 0, "description",  R.drawable.alert_dark_frame, 0, MainActivity.class, "com.technion.coolie", R.layout.preference_category),
	TECHPARK("Study Buddy", 0, "description",  R.drawable.alert_dark_frame, 0, MainActivity.class, "com.technion.coolie", R.layout.preference_category),
	SKELETON("Study Buddy", 0, "description",  R.drawable.alert_dark_frame, 0, MainActivity.class, "com.technion.coolie", R.layout.preference_category),
	TELETECH("Study Buddy", 0, "description",  R.drawable.alert_dark_frame, 0, MainActivity.class, "com.technion.coolie", R.layout.preference_category),
	TECMIND("Study Buddy", 0, "description",  R.drawable.alert_dark_frame, 0, MainActivity.class, "com.technion.coolie", R.layout.preference_category),
	ASSIGNMENTOR("Study Buddy", 0, "description",  R.drawable.alert_dark_frame, 0, MainActivity.class, "com.technion.coolie", R.layout.preference_category),
	TECHTRADE("Study Buddy", 0, "description",  R.drawable.alert_dark_frame, 0, MainActivity.class, "com.technion.coolie", R.layout.preference_category),
	UG("Study Buddy", 0, "description",  R.drawable.alert_dark_frame, 0, MainActivity.class, "com.technion.coolie", R.layout.preference_category),
	LETMEIN("Study Buddy", 0, "description",  R.drawable.alert_dark_frame, 0, MainActivity.class, "com.technion.coolie", R.layout.preference_category),
	JOININ("Study Buddy", 0, "description",  R.drawable.alert_dark_frame, 0, MainActivity.class, "com.technion.coolie", R.layout.preference_category);
	
	
	private String name;
	private int feedCount;
	private String description;
	
	public class Feed{
		String feedText;
		Date date;
		
		public Feed(String feedText, Date date){
			this.feedText = feedText;
			this.date = date;
		}
	}
	
	private List<Feed> feedList;
	private int photoRes;
	private int usageCounter;
	private Class<Activity> activity;
	private String pack;
	private int settingScreenXmlRes;
	private Date lastUsed;
	
	CoolieModuleManager(String name,int feedCount,String description,int photoRes,int usageCounter,Class activity,String pack,int settingScreenXmlRes)
	{
		this.name = name;
		
		feedList = new ArrayList<CoolieModuleManager.Feed>();
		setLastUsed();
		resetFeedCounter();
	}
	
	public void serilize(CoolieModuleManager source){
		
		this.name = source.name;
		this.feedCount = source.feedCount;
		this.description = source.description;
		Collections.copy(this.feedList, source.feedList);
		this.photoRes = source.photoRes;
		this.usageCounter = source.usageCounter;
		this.activity = source.activity ;
		this.pack = source.pack;
		this.settingScreenXmlRes = source.settingScreenXmlRes;
		this.lastUsed = source.lastUsed;
	}
	
	public String getName() {
		return name;
	}
	public int getFeedCount() {
		return feedCount;
	}
	public String getDescription() {
		return description;
	}
	public List<Feed> getFeedList() {
		return feedList;
	}
	public int getPhotoRes() {
		return photoRes;
	}
	public int getUsageCounter() {
		return usageCounter;
	}
	public Class<Activity> getActivity() {
		return activity;
	}
	public String getPack() {
		return pack;
	}
	public int getSettingScreenXmlRes() {
		return settingScreenXmlRes;
	}
	public Date getLastUsed() {
		return lastUsed;
	}
	
	public void updateName(String name)
	{
		this.name = name;
	}
	
	public void addFeed(Feed feed)
	{
		feedList.add(feed);
	}
	
	public void setMainActivity(Class<Activity> activity)
	{
		this.activity=activity;
	}
	
	////////////
	
	
	private void setLastUsed()
	{
		this.lastUsed = Calendar.getInstance().getTime();
	}
	
	private void addUsage()
	{
		this.usageCounter++;
	}
	
	private void resetFeedCounter()
	{
		this.feedCount=0;
	}
}

