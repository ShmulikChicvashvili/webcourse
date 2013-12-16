package com.technion.coolie.tecmind.BL;

import com.facebook.model.GraphObject;

public interface IMine {
	

	 /**
	 * @param gO - The response from Facebook that contains the all the posts of a certain user.
	 */
	public void mineUserPosts(GraphObject gO);


}
