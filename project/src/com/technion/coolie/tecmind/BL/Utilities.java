package com.technion.coolie.tecmind.BL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.technion.coolie.tecmind.MineActivity;
import com.technion.coolie.tecmind.BL.Utilities.LikesObject;

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

	public static void calculatePosts(int numOfPosts) {
		User.getUserInstance(null).postsNum += numOfPosts;
		User.getUserInstance(null).totalTechoins += calculatePostsAux(numOfPosts);
	}

	public static int calculatePostsAux(int numOfPosts) {
		Post temp = new Post(null, null, null, 0, 0, null, null, null);
		return (temp.technionValue) * numOfPosts;
	}

	public static int calculateCommentsAux(int numOfComments) {
		Comment temp = new Comment(null, null, 0, null, null, null, null);
		return (temp.technionValue) * numOfComments;
	}

	public static int calculatelikesAux(int numOfLikes) {
		Like temp = new Like(null, null);
		return (temp.technionValue) * numOfLikes;
	}

	public static void calculateComments(Post post, int numOfComments) {

		if (post != null) {
			if (post.commentCount < numOfComments) {
				User checkUser = User.getUserInstance(null);
				// dummy comment to get the value of one comment
				Comment temp = new Comment(null, null, 0, null, null, null, null);

				// calculates how many comments were collected
				int diff = numOfComments - post.commentCount;
				// updates the commentCount of the post
				post.commentCount = numOfComments;
				// updates the commentNum of the user
				User.getUserInstance(null).commentsNum += diff;
				// updates the amount of Techoins of the user
				User.getUserInstance(null).totalTechoins += diff
						* temp.technionValue;
			}
		}
	}

	public static void calculateLikes(Post post, int numOfLikes) {

		if (post != null) {
			if (post.likesCount < numOfLikes) {
				User checkUser = User.getUserInstance(null);
				// dummy like to get the value of one like
				Like temp = new Like(null, null);

				// calculates how many likes were collected
				int diff = numOfLikes - post.likesCount;
				// updates the likesCount of the post
				post.likesCount = numOfLikes;
				// updates the likestNum of the user
				User.getUserInstance(null).likesOnPostsNum += diff;
				// updates the amount of Techoins of the user
				User.getUserInstance(null).totalTechoins += diff
						* temp.technionValue;
			}
		}
	}

	public static Date parseDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss+SSSS");
		Date date = new Date();
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			System.out.println("Unable to parse date string");
		}

		return date;
	}


}
