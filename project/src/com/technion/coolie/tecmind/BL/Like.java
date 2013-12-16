package com.technion.coolie.tecmind.BL;

import java.util.Date;

public class Like extends UserActivity {
	  String id;
	  Date date;
	  int technionValue = 1;
	  int userID;

	  /**
	   * @param id1
	   * @param date1
	   * @param technionValue1
	   * @param userID1
	   */
	  public Like(String id1, Date date1, int userID1) {
	    this.id = id1;
	    this.date = date1;
	    this.userID = userID1;
	  }
}
