package com.technion.coolie.tecmind.BL;


public class Like extends UserActivity {
	  int technionValue = 1;
	  String userID;
	  String postId;

	  /**
	   * @param id1
	   * @param date1
	   * @param technionValue1
	   * @param userID1
	   */
	  public Like(String userID1, String postId1) {
	    this.userID = userID1;
	    this.postId = postId1;
	  }
	  
	  public void setId(String id1) {
		  id = id1;
	  }
}
