package com.technion.coolie.tecmind.BL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



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
	
	public static void calculatePosts(Post post, int numOfPosts){
		if (post != null) {
			User.getUserInstance(null).totalTechoins += (post.technionValue)*numOfPosts;
		}
	}
	
	public static void calculateComments(Post post, int numOfComments){
		
		if (post != null) {
			if (post.commentCount < numOfComments) {
				Comment temp = new Comment(null, null, 0, 0);
				int diff = numOfComments - post.commentCount;
				User.getUserInstance(null).commentsNum += diff;
				User.getUserInstance(null).totalTechoins += diff*temp.technionValue;
			}
		}
	}	
	
	public static void calculateLikes(Post post, int numOfLikes){
		
		if (post != null) {
			if (post.commentCount < numOfLikes) {
				Like temp = new Like(null, null, 0);
				int diff = numOfLikes - post.likesCount;
				User.getUserInstance(null).commentsNum += diff;
				User.getUserInstance(null).totalTechoins += diff*temp.technionValue;
			}
		}
	}
	


    public static Date parseDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SSSS");
        Date date = new Date();
        try {
            date = sdf.parse(str);
        } catch(ParseException e) {
            System.out.println("Unable to parse date string");
        }
        
		return date;
      }
}
