package com.technion.coolie.tecmind.BL;

import java.util.Date;
import java.util.HashMap;

import com.facebook.model.GraphObject;

public interface IMine {
	

	 /**
	 * @param gO - The response from Facebook that contains the all the posts of a certain user.
	 */
	public void mineUser(GraphObject gO);
	
	/**
	 * @param gO - The response from Facebook that contains the all the posts of a certain user.
	 */
	public void mineUserComments(GraphObject gO);
	
	/**
	 * @param gO - The response from Facebook that contains the all the posts of a certain user.
	 */
	public void mineUserLikes(GraphObject gO);

	/**
	 * Ends the mining for the user and updates the last time of mining.
	 */
	public void endMining();
	
	public  HashMap<String, String> getPostsUrls();
	
	public  HashMap<String, String> getPostsGroupsNames();
 
	public  HashMap<String, Date> getPostsDates();
	
	public  HashMap<String, String> getPostsContent();
	

}
