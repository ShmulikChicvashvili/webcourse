package com.technion.coolie.tecmind.BL;

import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.model.GraphObject;


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
	public String mineUserPosts(GraphObject gO) {

	        JSONObject jso = gO.getInnerJSONObject();	  		        
	        JSONArray arr;
	        String id = null;
	        
		try {
			arr = jso.getJSONArray( "data" );
		        for ( int i = 0; i < ( arr.length() ); i++ )
		        {
		            JSONObject json_obj = arr.getJSONObject( i );
		            
		           id = ((JSONArray)((JSONObject)json_obj.get("to")).get("data")).getJSONObject(0).get("id").toString();
		           System.out.println(i);
		           
		           if (mTechGroups.contains(id)) {
		        	   User.getUserInstance(null).postsNum++;
		           }           
		        }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	 
	
}
