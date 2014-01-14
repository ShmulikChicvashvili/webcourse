package com.technion.coolie.tecmind.BL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements IUser{
	public String id;
	public String name;
	public Title title = Title.ATUDAI;
	public Date lastMining = Utilities.parseDate("2013-08-30T16:30:00+0000");
	public int totalTechoins = 0;
	public int bankAccount = 0;
	
	public int postsNum = 0;
	public int commentsNum = 0;
	public int likesNum = 0; // likes that the user did
	public int likesOnPostsNum = 0; // likes on posts
	
	public int likesOthers = 0;
	public int commentsOthers = 0;
	public int weeklyTotlal = 0;
	public int spamCount = 0;
	
	public ArrayList<Post> posts = new ArrayList<Post>();
	
	private static User mUser = null;
		
	private User(String userId) {
		id = userId;
	}
	
	/* Return User Instance if already have been created, initiate new one otherwise */
	public static User getUserInstance(String id) {
		if (mUser == null) {
			/* if curr session is not active update */
			mUser = new User(id);		
		} 
		
		return mUser;
		
		
		
	}

	@Override
	public void initiateFieldsFromServer(String sName, Title sTitle,
			Date sLastMining, int sTotalTechoins, int sBankAccount, int sPosts, int sComments,
			int sLikes, int sLikesOnPosts, int sLikesOthers, int sCommentsOthers, int sWeeklyTotal, int sSpamCount) {
		name = sName;
		title = sTitle;
		lastMining = sLastMining;
		totalTechoins = sTotalTechoins;
		bankAccount = sBankAccount;
		postsNum = sPosts;
		likesNum = sLikes;
		likesOnPostsNum = sLikesOnPosts;
		commentsNum = sComments;
		likesOthers = sLikesOthers;
		commentsOthers = sCommentsOthers;
		weeklyTotlal = sWeeklyTotal;
		spamCount = sSpamCount;
		
	}
	
	@Override
	public Post getPostById(String postId) {
		for (Post p : User.getUserInstance(null).posts) {
			System.out.println(p.id);
			System.out.println(postId);
			if (p.id.equals(postId)) {
				return p;
			}
		}
		return null;
	}

	
}
