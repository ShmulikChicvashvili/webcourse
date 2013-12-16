package com.technion.coolie.tecmind.BL;

import java.util.Date;

public class Comment extends UserActivity {
	  String id;
	  Date date;
	  int technionValue;
	  int userID = 2;
	  int likesCount;
	
	  /**
	   * @param id1
	   * @param date1
	   * @param technionValue1
	   * @param userID1
	   * @param likesCount1
	   */
	  public Comment(String id1, Date date1, int userID1,
	      int likesCount1) {
	    this.id = id1;
	    this.date = date1;
	    this.userID = userID1;
	    this.likesCount = likesCount1;
	  }
}
