package com.technion.coolie.tecmind.BL;



public class Utilities {
	public class LikesObject {
		String id;
		String name;
	}
	
	public class CommentObject {
		boolean user_likes;
		String message;
		String id;
		boolean can_remove;
//		ArrayList<UserObject> from;
		String created_time;
		int like_count;
	}
	
	public class UserObject {
		String id;
		String name;
	}
}
