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
	private String pId;
	
	public PopUpItem(int itemId, String content, Date date, String postId, String groupName) {
		super();
		this.itemId = itemId;
		this.content = content;
		this.date = MineDateView.getInstance().dateToString(date);
		this.groupName = groupName;
		this.pId = postId;
	}
 
	public int getItemId() {
		return itemId;
	}

	public String getContent() {
		return content;
	}
	
	public String getMonth() {
		return date.first;
	}
	public String getDay() {
		return date.second;
	}
	public String getPId() {
		return pId;
	}
	public String getGroupName() {
		return groupName;
	}
}