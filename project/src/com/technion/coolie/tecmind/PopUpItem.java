package com.technion.coolie.tecmind;

public class PopUpItem {
	private int itemId;
	private String titleText;
	private Class<?> activityClassName;
	
	public PopUpItem(int itemId, String titleText, Class<?> activityClassName) {
		super();
		this.itemId = itemId;
		this.titleText = titleText;
		this.activityClassName = activityClassName;
	}
 
	public int getItemId() {
		return itemId;
	}
 
	public String getTitleText() {
		return titleText;
	}
 
	public Class<?> getActivityClassName() {
		return activityClassName;
	}
}