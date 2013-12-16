package com.technion.coolie.tecmind.BL;

import java.util.Date;

public class Post extends UserActivity {
	  String id;
	  Date date;
	  int technionValue = 5;
	  String userID;
	  int likesCount;
	  int commentCount;
	  
	  /**
	   * @param id1
	   * @param date1
	   * @param technionValue1
	   * @param userID1
	   * @param likesCount1
	   */
	  public Post(String id1, Date date1, String userID1,
	      int likesCount1, int commentCount1) {
	    this.id = id1;
	    this.date = date1;
	    this.userID = userID1;
	    this.likesCount = likesCount1;
	    this.commentCount = commentCount1;
	  }
}
