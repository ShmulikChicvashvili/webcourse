package com.technion.coolie.tecmind;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.tecmind.BL.Mine;


public class MineActivity extends CoolieActivity {
	
	private Session currentSession;
	Session.NewPermissionsRequest newPermissionsRequest;
	List<String> permissions;
	String userId;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.techmind_activity_mine);

	    currentSession = Session.getActiveSession();
	    if (currentSession == null) {
	    	
	    }
	    
	    /* start Facebook Login */
	    openActiveSession(this, true, new Session.StatusCallback() {

	      /* callback when session changes state */
	      @Override
	      public void call(Session session, SessionState state, Exception exception) {
	        if (session.isOpened()) {
	        	currentSession = session;

	          /* make request to the /me API */
	          Request.newMeRequest(session, new Request.GraphUserCallback() {

	            /* callback after Graph API response with user object */
	            @Override
	            public void onCompleted(GraphUser user, Response response) {		    
	            	if (user != null) {
	            		userId = user.getId();

	  	  		      Toast.makeText(getApplicationContext(), "OPENED",
	  	  	        		Toast.LENGTH_LONG).show();
	            		mining();
	            	}
	             }
	          }).executeAsync();
	        }		        
	      }
	    }, Arrays.asList("user_groups","user_activities","user_likes"));
	  }

	private static Session openActiveSession(Activity activity, boolean allowLoginUI, 
			StatusCallback callback, List<String> permissions) {
	    OpenRequest openRequest = new OpenRequest(activity).setPermissions(permissions).setCallback(callback);
	    Session session = new Session.Builder(activity).build();
	    if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) || allowLoginUI) {
	        Session.setActiveSession(session);
	        session.openForRead(openRequest);
	        return session;
	    }
	    return null;
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
	  		        
	  		       Mine.getMineInstance(userId).mineUserPosts(gO);
	  		        
//	  		      Toast.makeText(getApplicationContext(), id,
//	  	        		Toast.LENGTH_LONG).show();
	  		        
  		        }
  		    }
  		).executeAsync();
  	}
}




