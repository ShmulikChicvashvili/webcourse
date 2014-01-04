package com.technion.coolie.tecmind;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import android.util.Pair;

public class PopUpItem {
	private int itemId;
	private String content;
	private Pair<String, String> date;
	private String groupName;
	private URL urlAdd;
	
	public PopUpItem(int itemId, String content, Date date, String url, String groupName) {
		super();
		this.itemId = itemId;
		this.content = content;
		this.date = MineDateView.getInstance().dateToString(date);
		try {
			this.urlAdd = new URL(url);
		} catch (MalformedURLException e) {
			this.urlAdd= null; 
		}
		this.groupName = groupName;
	}
 
	public int getItemId() {
		return itemId;
	}

	public String getContent() {
		return content;
	}
	
	public Pair<String, String> getDate() {
		return date;
	}
	
	public URL getUrlAdd() {
		return urlAdd;
	}
	public String getGroupName() {
		return groupName;
	}
}