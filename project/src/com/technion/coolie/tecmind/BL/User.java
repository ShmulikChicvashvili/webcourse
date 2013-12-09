package com.technion.coolie.tecmind.BL;

import java.util.Date;
import java.util.List;

public class User {
	public String id;
	public String name;
	public String account; //in the center bank
	public int totalTechnions;
	public Date lastMining;
	public Title title;
	List<Post> posts; //all posts from last mining till now
	List<Like> likes; //all likes the user did from last mining till now
	List<Comment> comments; //all the comments the user posts from last mining till now
	
}
