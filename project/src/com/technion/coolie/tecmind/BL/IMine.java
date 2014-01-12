package com.technion.coolie.tecmind.BL;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import com.facebook.model.GraphObject;
import com.technion.coolie.tecmind.server.TecUser;

public interface IMine {
	

	 /**
	 * @param gO - The response from Facebook that contains the all the posts of a certain user.
	 */
	public void mineUser(GraphObject gO);
	
	/**
	 * Ends the mining for the user and updates the last time of mining.
	 */
	public void endMining();
	
	public  HashMap<String, String> getPostsUrls();
	
	public  HashMap<String, String> getPostsGroupsNames();
 
	public  HashMap<String, Date> getPostsDates();
	
	public  HashMap<String, String> getPostsContent();
	
	public  LinkedList<TecUser> getOtherUsersList();
	

}
