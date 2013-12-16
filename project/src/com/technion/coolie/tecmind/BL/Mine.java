package com.technion.coolie.tecmind.BL;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	private Mine(String userId) {	 
		mUserId = userId;
		mTechGroups = new LinkedList<String>();
		mTechGroups.add("244590982367730");
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
	public void mineUserPosts(GraphObject gO) {

	        JSONObject jso = gO.getInnerJSONObject();	  		        
	        JSONArray arr;
	        String groupId = null;
	        String updateTimeString = null;
	        String createTimeString = null;
	        String likes = null;
	        String postId = null;
	        ArrayList<Utilities.LikesObject> likesArr;
	        String comments = null;
	        ArrayList<Utilities.CommentObject> commentsArr;
	        int postsCounter = 0;
	        int commentsOfPostsCounter = 0;
	        int likesOfPostsCounter = 0;
	        Post post = null;
		try {
			User checkUser = User.getUserInstance(null);
			arr = jso.getJSONArray( "data" );
	        for ( int i = 0; i < ( arr.length() ); i++ ) {
	            JSONObject json_obj = arr.getJSONObject( i );
	            if (json_obj.toString().contains("\"to\":")){
	            	 groupId = ((JSONArray)((JSONObject)json_obj.get("to")).get("data")).getJSONObject(0).get("id").toString();
			         System.out.println(i);
			         
			         /* gets the time stamps of creating the post and the last update */
			         updateTimeString =  json_obj.get("updated_time").toString();
					 Date updateTimeDate = Utilities.parseDate(updateTimeString);

					 createTimeString =  json_obj.get("created_time").toString();
			         Date createTimeDate = Utilities.parseDate(createTimeString);
			         
			         Assert.assertEquals(mUserId, User.getUserInstance(null).id);
			         				         
			         /* if post hasn't been updated after last mining */
			         if (updateTimeDate.before(User.getUserInstance(mUserId).lastMining) ) {
			        	 break;
			         }
			         
			         /* gets the post id */
			         postId = json_obj.getString("id");
			         
		            if (mTechGroups.contains(groupId)) {
		            	 // counts all likes of the post in the certain group
		        	   if (json_obj.toString().contains("\"likes\":")){
			            	likes = ((JSONArray)((JSONObject)json_obj.get("likes")).get("data")).toString();
			            	likesArr = new Gson().fromJson(likes, new TypeToken<ArrayList<Utilities.LikesObject>>() 
			            			{}.getType());
			            	likesOfPostsCounter += likesArr.size();
	
			           }
		        	   
			        	// counts all comments of the post
		        	   if (json_obj.toString().contains("\"comments\":")){
			            	comments = ((JSONArray)((JSONObject)json_obj.get("comments")).get("data")).toString();
			            	
			            	commentsArr = new Gson().fromJson(comments, new TypeToken<ArrayList<Utilities.CommentObject>>() 
			            			{}.getType());
			            	commentsOfPostsCounter += commentsArr.size();
	
			           }
		        	   
		        	   /* if post hasn't been published before last mining count post */
		        	   if (createTimeDate.after(User.getUserInstance(mUserId).lastMining) ) {
		        		   postsCounter++;
		        		   
		        		   /* adds the post to the user's posts list */
		        		   Post newPost = new Post(postId, createTimeDate, mUserId, 0, 0);
		        		   User.getUserInstance(null).posts.add(newPost);
		        	   }
		            }
		            
		            post = User.getUserInstance(null).getPostById(postId);
			        Utilities.calculateComments(post, commentsOfPostsCounter);
			        Utilities.calculateLikes(post, likesOfPostsCounter);
			        likesOfPostsCounter = 0;
			        commentsOfPostsCounter = 0;
	            }
	        }
	        
	        User tempUser = User.getUserInstance(null); 
	        
	        Utilities.calculatePosts(postsCounter);

	        
	        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void endMining() {
		User.getUserInstance(null).lastMining = new Date();
		
	}
	

    
 
	

	
}
