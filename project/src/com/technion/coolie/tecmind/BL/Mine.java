package com.technion.coolie.tecmind.BL;


import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.facebook.model.GraphObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.tecmind.BL.Utilities;


public class Mine implements IMine {

	private static Mine mMine = null;
	private static String mUserId = null;
		
	private LinkedList<String> mTechGroups;
	
	private HashMap<String, String> mPostsUrls;
	private HashMap<String, String> mPostsGroupsNames;
	private HashMap<String, Date> mPostsDates;
	private HashMap<String, String> mPostsContent;
	
	public Mine(String userId) {	 
		mUserId = userId;
		mTechGroups = new LinkedList<String>();
		mTechGroups.add("244590982367730");
		mPostsGroupsNames = new HashMap<String, String>();
		mPostsUrls = new HashMap<String, String>();
		mPostsDates = new HashMap<String, Date>();
		mPostsContent = new HashMap<String, String>();
	}
	
	/* Return Mine Instance if already have been created, initiate new one otherwise */
	public static Mine getMineInstance(String userId) {
		if (mMine != null) {
			/* if curr session is not active update */
			return mMine;
		} else {
			mMine = new Mine(userId);
			return mMine;
		}
				
	}
	

	@Override
	public void mineUser(GraphObject gO) {

        JSONObject jso = gO.getInnerJSONObject();	  		        
        JSONArray arr;
        String groupId = null;
        String postId = null;
        
		try {
			User checkUser = User.getUserInstance(null);
			
			/* sets the last mining date to 5 minutes earlier */
			setTimeBack();
					
			arr = jso.getJSONArray("data");
	        for ( int i = 0; i < ( arr.length() ); i++ ) {
	            JSONObject json_obj = arr.getJSONObject( i );
	            if (json_obj.toString().contains("\"to\":")){
	            	
	            	 /* gets the group id where the post has been published */ 
	            	 groupId = ((JSONArray)((JSONObject)json_obj.get("to")).get("data")).getJSONObject(0).get("id").toString();
	    
	            	 if (!mTechGroups.contains(groupId)) {
	            	 	continue;
	            	 }
	            	 
	            	 /* gets the post id */
			         postId = json_obj.getString("id");
			         
			         /* updates times */
			         Date updateTimeDate = getCorrectTime(json_obj, "updated_time");
			         Date createTimeDate = getCorrectTime(json_obj, "created_time");
			         
			         /* if post hasn't been published before last mining count post */
		        	 if (createTimeDate.after(User.getUserInstance(null).lastMining) && 
		        			 User.getUserInstance(null).getPostById(postId) == null) {
		        		 mineUserPosts(json_obj, postId, createTimeDate);
		        	 }
		        	   
		        	 /* mines all user's likes */
			         mineUserLikes(json_obj, postId);
			         
			         /* if post hasn't been updated after last mining */
			         if (updateTimeDate.before(User.getUserInstance(null).lastMining) ) {
			        	 continue;//break;
			         }
			         
			         /* mines all user's comments */
		        	 mineUserComments(json_obj, postId);
	            }
	        }
	        
 		     System.out.println("*****FROM MINE******");
 		     System.out.println("The number of posts after mining is:" + User.getUserInstance(null).postsNum);
 		     System.out.println("The number of comments after mining is:" + User.getUserInstance(null).commentsNum);
 		     System.out.println("The number of likes after mining is:" + User.getUserInstance(null).likesOnPostsNum);
 		     System.out.println("The amount of Techoins i have is:" + User.getUserInstance(null).totalTechoins);
 		     System.out.println("The last mining date is:" + User.getUserInstance(null).lastMining.toString());
 		    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void setTimeBack() {
		final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
		
		long lastDate = User.getUserInstance(null).lastMining.getTime();
		User.getUserInstance(null).lastMining = new Date(lastDate - (5 * ONE_MINUTE_IN_MILLIS));
		
	}

	private void mineUserPosts(JSONObject json_obj, String postId, Date createTimeDate) throws JSONException {
        URL url = null;
		
   	    /* gets the post's group name */
        String postGroupName = ((JSONArray)((JSONObject)json_obj.get("to")).get("data")).getJSONObject(0).get("name").toString();
        
   	    /* gets the post's content */
        String postContent = json_obj.getString("message");
        
        /* gets the post's url */
        String postUrl = json_obj.getJSONArray("actions").getJSONObject(0).get("link").toString();
       		 
		try {
			url = new URL(postUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    /* adds the post to the user's posts list */
	    Post newPost = new Post(postId, createTimeDate, mUserId, 0, 0, 
			   postContent,  url, postGroupName, 0, null);
	    User.getUserInstance(null).posts.add(newPost);
	   
	    /* adds the url to the list by postId */
	    mPostsUrls.put(postId, postUrl);
	   
	    /* adds the group name to the list by postId */
	    mPostsGroupsNames.put(postId, postGroupName);
	   
	    /* adds the post date to the list by postId */
	    mPostsDates.put(postId, createTimeDate);

	    /* adds the post content to the list by postId */
	    mPostsContent.put(postId, postContent);
	    
	    Utilities.calculatePosts(1);
		
	}

	private void mineUserComments(JSONObject json_obj, String postId) throws JSONException {
		
		// counts all comments of the post
        String comments = null;
        ArrayList<Utilities.CommentObject> commentsArr;
        int commentsOfPostsCounter = 0;
        
 	    if (json_obj.toString().contains("\"comments\":")){
          	comments = ((JSONArray)((JSONObject)json_obj.get("comments")).get("data")).toString();
          	
          	commentsArr = new Gson().fromJson(comments, new TypeToken<ArrayList<Utilities.CommentObject>>() 
          			{}.getType());
          	commentsOfPostsCounter += commentsArr.size();

         } 
 	    Post post = User.getUserInstance(null).getPostById(postId);
 	    Utilities.calculateComments(post, commentsOfPostsCounter);
	}

	private void mineUserLikes(JSONObject json_obj, String postId) throws JSONException {
		
   	 // counts all likes of the post in the certain group
		String likes = null;
		ArrayList<Utilities.LikesObject> likesArr;
		int likesOfPostsCounter = 0;
		
	    if (json_obj.toString().contains("\"likes\":")){
	       	likes = ((JSONArray)((JSONObject)json_obj.get("likes")).get("data")).toString();
	       	likesArr = new Gson().fromJson(likes, new TypeToken<ArrayList<Utilities.LikesObject>>() 
	       			{}.getType());
	       	likesOfPostsCounter += likesArr.size();
	    }
	    
	    Post post = User.getUserInstance(null).getPostById(postId);
	    Utilities.calculateLikes(post, likesOfPostsCounter);
	}

	private Date getCorrectTime(JSONObject obj, String action) throws JSONException {
		/* gets the time stamps of creating the post and the last update */
		String timeToChange = null;
		if (action.equals("updated_time")) {
			timeToChange = obj.get("updated_time").toString();
		}
		else {
			timeToChange = obj.get("created_time").toString();
		}
		
        Date newDate = Utilities.parseDate(timeToChange);
        Long time = newDate.getTime();
        time +=(2*60*60*1000);
        return new Date(time);
       
	}

	@Override
	public void endMining() {
		User.getUserInstance(null).lastMining = new Date();
	}

	@Override
	public void mineUserComments(GraphObject gO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mineUserLikes(GraphObject gO) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public  HashMap<String, String> getPostsUrls() {
		return mPostsUrls;
	}
	
	@Override
	public  HashMap<String, String> getPostsGroupsNames() {
		return mPostsGroupsNames;
	}
	
	@Override
	public  HashMap<String, Date> getPostsDates() {
		return mPostsDates;
	}
	
	@Override
	public  HashMap<String, String> getPostsContent() {
		return mPostsContent;
	}
 
	

 
	

	
}
