package com.technion.coolie.tecmind;
import java.util.Arrays;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionState;
import com.facebook.android.Facebook;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.tecmind.BL.Mine;
import com.technion.coolie.tecmind.BL.User;

public class MineActivity extends CoolieActivity {
	
	private Session currentSession;
	Session.NewPermissionsRequest newPermissionsRequest;
	String userId;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.techmind_activity_mine);
	    
	    newPermissionsRequest = new Session.NewPermissionsRequest(
		        this, Arrays.asList("user_groups","user_activities","user_likes"));
	    
	    /* start Facebook Login */
	    Session.openActiveSession(this, true, new Session.StatusCallback() {

	      /* callback when session changes state */
	      @Override
	      public void call(Session session, SessionState state, Exception exception) {
	        if (session.isOpened()) {
	        	currentSession = session;
	            session.requestNewReadPermissions(newPermissionsRequest);
	            //currentSession.getAccessToken();
	          /* make request to the /me API */
	          Request.newMeRequest(session, new Request.GraphUserCallback() {

	            /* callback after Graph API response with user object */
	            @Override
	            public void onCompleted(GraphUser user, Response response) {
	            	Toast.makeText(getApplicationContext(), "OPENED", 
	    	        		Toast.LENGTH_LONG).show();		    
	            	if (user != null) {
	            		userId = user.getId();
	            		mining();
	            	}
	            	
     	
	            }
	          }).executeAsync();
	        }
        	Toast.makeText(getApplicationContext(), session.getState().toString() ,
	        		Toast.LENGTH_LONG).show();		        
	      }
	    });
	  }

	  @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	  }
	  
	  void mining() {
  		
		 		  
		/* make Facebook API call */
  		new Request(currentSession, userId + "/feed", 
  			    null, HttpMethod.GET, new Request.Callback() {

  		        public void onCompleted(Response response) {
  		        	
	  		        GraphObject gO = response.getGraphObject();
	  		        
	  		       String id = Mine.getMineInstance(userId).mineUserPosts(gO);
	  		        
	  		      Toast.makeText(getApplicationContext(), id,
	  	        		Toast.LENGTH_LONG).show();
	  		        
  		        }
  		    }
  		).executeAsync();
  	}
}




