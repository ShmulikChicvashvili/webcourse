package com.technion.coolie.tecmind.BL;

import com.facebook.model.GraphObject;

public interface IMine {
	

	 /**
	 * @param gO - The response from Facebook that contains the all the posts of a certain user.
	 */
	public void mineUserPosts(GraphObject gO);
	
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

}
