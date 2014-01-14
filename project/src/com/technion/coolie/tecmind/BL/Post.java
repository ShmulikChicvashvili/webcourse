package com.technion.coolie.tecmind.BL;

import java.net.URL;
import java.util.Date;

public class Post extends UserActivity {
	  public String id;
	  public Date date;
	  public int technionValue = 5;
	  public String userID;
	  public int likesCount = 0;
	  public int commentCount = 0;
	  public String content;
	  public URL url = null;
	  public String groupName = null;
	  
	  
	  
	  /**
	 * @param id1
	 * @param date1
	 * @param userID1
	 * @param likesCount1
	 * @param commentCount1
	 * @param content1
	 * @param url1
	 * @param groupName1
	 * @param spamCount1
	 * @param spamType1
	 */
	public Post(String id1, Date date1, String userID1, int likesCount1, 
			int commentCount1, String content1, URL url1, String groupName1) {
	    this.id = id1;
	    this.date = date1;
	    this.userID = userID1;
	    this.likesCount = likesCount1;
	    this.commentCount = commentCount1;
	    this.content = content1;
	    this.url = url1;
	    this.groupName = groupName1;

	 }
}
