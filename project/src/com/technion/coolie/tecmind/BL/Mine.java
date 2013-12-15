package com.technion.coolie.tecmind.BL;


import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	        String id = null;
	        String likes = null;
	        ArrayList<Utilities.LikesObject> likesArr;
	        String comments = null;
	        ArrayList<Utilities.CommentObject> commentsArr;
		try {
			arr = jso.getJSONArray( "data" );
		        for ( int i = 0; i < ( arr.length() ); i++ )
		        {
		            JSONObject json_obj = arr.getJSONObject( i );
		            if (json_obj.toString().contains("\"to\":")){
		            	id = ((JSONArray)((JSONObject)json_obj.get("to")).get("data")).getJSONObject(0).get("id").toString();
				           System.out.println(i);
		            }
		           
		            // counts all likes of the post
		           if (mTechGroups.contains(id)) {
		        	   if (json_obj.toString().contains("\"likes\":")){
			            	likes = ((JSONArray)((JSONObject)json_obj.get("likes")).get("data")).toString();
			            	likesArr = new Gson().fromJson(likes, new TypeToken<ArrayList<Utilities.LikesObject>>() 
			            			{}.getType());
			            	User.getUserInstance(null).likesOnPostsNum += likesArr.size();
	
			           }
		        	   
			        	// counts all comments of the post
		        	   if (json_obj.toString().contains("\"comments\":")){
			            	comments = ((JSONArray)((JSONObject)json_obj.get("comments")).get("data")).toString();
			            	
			            	commentsArr = new Gson().fromJson(comments, new TypeToken<ArrayList<Utilities.CommentObject>>() 
			            			{}.getType());
			            	User.getUserInstance(null).commentsNum += commentsArr.size();

			           }
	        	   		// counts all posts of the post
		        	   User.getUserInstance(null).postsNum++;
		           } 
		 
		           
		           
	         
		        }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	

	
}
