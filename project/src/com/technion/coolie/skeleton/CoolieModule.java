package com.technion.coolie.skeleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.technion.coolie.R;

import android.app.Activity;
import android.content.Context;

public enum CoolieModule {
	STUDYBUDDY	(R.string.stb_name, R.string.stb_description,  R.drawable.stb_icon, com.technion.coolie.studybuddy.MainActivity.class, Package.getPackage("com.technion.coolie.studybuddy"), 0),
	TECHLIBRARY	(R.string.lib_name, R.string.lib_description,  R.drawable.lib_icon, com.technion.coolie.techlibrary.MainActivity.class, Package.getPackage("com.technion.coolie.techlibrary"), 0),
	TECHPARK	(R.string.park_name, R.string.park_description,  R.drawable.park_icon, com.technion.coolie.techpark.MainActivity.class, Package.getPackage("com.technion.coolie.techpark"), 0),
//	SKELETON	(R.string.skel_name, R.string.skel_description,  R.drawable.ic_launcher, com.technion.coolie.skeleton.MainActivity.class, Package.getPackage("com.technion.coolie.skeleton"), 0),
	TELETECH	(R.string.tele_name, R.string.tele_description,  R.drawable.tele_icon, com.technion.coolie.teletech.MainActivity.class, Package.getPackage("com.technion.coolie.teletech"), 0),
	TECMIND		(R.string.mind_name, R.string.mind_description,  R.drawable.mind_icon, com.technion.coolie.tecmind.MainActivity.class, Package.getPackage("com.technion.coolie.tecmind"), 0),
	ASSIGNMENTOR(R.string.am_name, R.string.am_description,  R.drawable.am_icon, com.technion.coolie.assignmentor.MainActivity.class, Package.getPackage("com.technion.coolie.assignmentor"), 0),
	TECHTRADE	(R.string.trad_name, R.string.trad_description,  R.drawable.trad_icon, com.technion.coolie.techtrade.MainActivity.class, Package.getPackage("com.technion.coolie.techtrade"), 0),
	UG			(R.string.ug_name, R.string.ug_description,  R.drawable.ug_icon, com.technion.coolie.ug.MainActivity.class, Package.getPackage("com.technion.coolie.ug"), 0),
	LETMEIN		(R.string.lmi_name, R.string.lmi_description,  R.drawable.lmi_icon, com.technion.coolie.letmein.MainActivity.class, Package.getPackage("com.technion.coolie.letmein"), 0),
	JOININ		(R.string.ji_name, R.string.ji_description,  R.drawable.ji_icon, com.technion.coolie.joinin.MainActivity.class, Package.getPackage("com.technion.coolie.joinin"), 0),
	WEBCOURSE	(R.string.web_name, R.string.web_description,  R.drawable.web_icon, com.technion.coolie.webcourse.gr_plusplus.CoursesActivity.class, Package.getPackage("com.technion.coolie.webcourse.gr_plusplus"), 0);
	
	
	private int nameResource;
	private int feedCount;
	private int descriptionResource;
	
	public class Feed{
		String feedText;
		Date date;
		
		public Feed(String feedText, Date date){
			this.feedText = feedText;
			this.date = date;
		}
	}
	
	private List<Feed> feedList;
	private int iconResource;
	private int usageCounter;
	private Class<?> activity;
	private Package pack;
	private int settingScreenXmlRes;
	private Date lastUsed;
	private boolean isFavorite;
	
	CoolieModule(int nameResource,int descriptionResource,int photoRes,Class<?> activity,Package pack,int settingScreenXmlRes)
	{
		this.nameResource = nameResource;
		this.iconResource = photoRes;
		this.descriptionResource = descriptionResource;
		this.nameResource = nameResource;
		this.activity = activity;
		this.pack = pack;
		this.settingScreenXmlRes = settingScreenXmlRes;
		
		feedList = new ArrayList<CoolieModule.Feed>();
		resetFeedCounter();
		isFavorite = false;
		
		usageCounter = 0;
		feedCount = 0;
		lastUsed = null;
	}
	
	public void serilize(CoolieModule source){
		
		this.nameResource = source.nameResource;
		this.feedCount = source.feedCount;
		this.descriptionResource = source.descriptionResource;
		Collections.copy(this.feedList, source.feedList);
		this.iconResource = source.iconResource;
		this.usageCounter = source.usageCounter;
		this.activity = source.activity ;
		this.pack = source.pack;
		this.settingScreenXmlRes = source.settingScreenXmlRes;
		this.lastUsed = source.lastUsed;
		this.isFavorite = source.isFavorite;
	}
	
	public String getName(Context c) {
		return c.getString(nameResource);
	}
	public int getFeedCount() {
		return feedCount;
	}
	public String getDescription(Context c) {
		return c.getString(descriptionResource);
	}
	public List<Feed> getFeedList() {
		return feedList;
	}
	public int getPhotoRes() {
		return iconResource;
	}
	public int getUsageCounter() {
		return usageCounter;
	}
	public Class<?> getActivity() {
		return activity;
	}
	public Package getPackage() {
		return pack;
	}
	public int getSettingScreenXmlRes() {
		return settingScreenXmlRes;
	}
	public Date getLastUsed() {
		return lastUsed;
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
	
	private void resetFeedCounter()
	{
		this.feedCount=0;
	}

	public void setLastUsage() {
		this.lastUsed = Calendar.getInstance().getTime();
	}
	public void addUsage()
	{
		this.usageCounter++;
		this.setLastUsage();

	}
	public boolean isFavorite()
	{
		return isFavorite;
	}
	public void setFavorite()
	{
		this.isFavorite = true;
	}
	public void setNotFavorite()
	{
		this.isFavorite = false;
	}
	
	public void setLastUsage(Date date) {
		this.lastUsed = date;
	}
	
	public void setActivity(Class<?> activity){
		this.activity = activity;
	}
	
	public void setDescription()
	{
		this.isFavorite = false;
	}
	public void setUsageCounter(int usageCounter){
		this.usageCounter = usageCounter;
	}
	
	public static class serializeClass{
		public int usageCounter;
		public boolean isFavorite;
		public String activityString;
		public Date lastUsed;
		
		/*public serializeClass(int usageCounter, boolean isFavorite, Class<?> activity, Date lastUsed){
			this.usageCounter = usageCounter;
			this.isFavorite = isFavorite;
			this.activity = activity;
			this.lastUsed = lastUsed;
		}*/
	};
}

