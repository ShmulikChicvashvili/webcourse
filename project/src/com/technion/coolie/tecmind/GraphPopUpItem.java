package com.technion.coolie.tecmind;

import android.graphics.Color;

public class GraphPopUpItem {
	private int itemId;
	private String groupName;
	private int color;
	
	public GraphPopUpItem(int itemId, String name, int col) {
		
		this.itemId = itemId;
		this.groupName = name;
		this.color = col;
	}
 
	public int getItemId() {
		return itemId;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public int getColor() {
		return color;
	}
	
}