package com.technion.coolie.tecmind.BL;

import java.util.Date;
import java.util.List;

public class User {
	public String mId;
	public String name;
	public String account; //in the center bank
	public int totalTechnions;
	public Date lastMining;
	public Title title;
	List<Post> posts; //all posts from last mining till now
	List<Like> likes; //all likes the user did from last mining till now
	List<Comment> comments; //all the comments the user posts from last mining till now
	
	public int postsNum;
	public int likesNum;
	public int likesOnPostsNum;
	
	private static User mUser = null;
		
	private User(String id) {
		mId = id;
	}
	
	/* Return User Instance if already have been created, initiate new one otherwise */
	public static User getUserInstance(String id) {
		if (mUser != null) {
			/* if curr session is not active update */
			return mUser;
		} else {
			mUser = new User(id);
			return mUser;
		}
		
		
	}

	
}
