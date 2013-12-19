package com.technion.coolie.tecmind.BL;

import java.util.Date;

public interface IUser {
	public void initiateFieldsFromServer(String sName, Title sTitle, Date sLastMining, int sTotalTechoins, int sBankAccountint, int sPosts, int sComments,
			int sLikes, int sLikesOnPosts);
	
	public Post getPostById(String postId);
}
