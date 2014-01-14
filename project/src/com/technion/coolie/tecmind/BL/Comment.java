package com.technion.coolie.tecmind.BL;

import java.util.Date;

public class Comment extends UserActivity {
	  String id;
	  int technionValue = 2;
	  String userID;
	  int likesCount = 0;
	  Date date;
	  String message;
	
	  /**
	   * @param id1
	   * @param date1
	   * @param technionValue1
	   * @param userID1
	   * @param likesCount1
	   */
	  public Comment(String id1, String userID1,
	      int likesCount1, String postId1, String postContent1, String groupName1, String message1) {
		this.id = id1;
		this.userID = userID1;
	    this.likesCount = likesCount1;
	    this.message = message1;
	  }
}
