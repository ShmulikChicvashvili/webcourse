package com.technion.coolie.tecmind;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.technion.coolie.tecmind.BL.Title;
import com.technion.coolie.tecmind.BL.User;
import com.technion.coolie.tecmind.server.TecUser;
import com.technion.coolie.tecmind.server.TecUserTitle;
import com.technion.coolie.tecmind.server.TechmineAPI;


public class MineActivity extends CoolieActivity {
	
	Session currentSession;
	Session.NewPermissionsRequest newPermissionsRequest;
	List<String> permissions;
	String userId;
	TechmineAPI connector;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.techmind_activity_mine);

	    currentSession = Session.getActiveSession();
        if (currentSession != null && currentSession.isOpened()) {
            mining();
        }
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
	  		       User tempUser = User.getUserInstance(null); 
	  		       Mine.getMineInstance(null).endMining();
//	  		      Toast.makeText(getApplicationContext(), id,
//	  	        		Toast.LENGTH_LONG).show();
	  		     System.out.println("*****After Mining******");
	  		     System.out.println("The number of posts after mining is:" + User.getUserInstance(null).postsNum);
	  		     System.out.println("The number of comments after mining is:" + User.getUserInstance(null).commentsNum);
	  		     System.out.println("The number of likes after mining is:" + User.getUserInstance(null).likesOnPostsNum);
	  		   System.out.println("The amount of Techoins i have is:" + User.getUserInstance(null).totalTechoins);
	  		     System.out.println("The last mining date is:" + User.getUserInstance(null).lastMining.toString());
  		        }
  		    }
  		).executeAsync();
  	}
	 
	  void initiateFromServer() {
		  new ServerUserData().execute();
		  
	  }
	  
	  class ServerUserData extends AsyncTask<Void, Void, TecUser> {

			@Override
			protected TecUser doInBackground(Void... arg0) {
				TecUser userToServer = new TecUser(userId, null, null, null, 0, 0);
				return connector.getUser(userToServer);
				
			}

			@Override
			protected void onPostExecute(TecUser result) {
				User.getUserInstance(result.getId());
				User.getUserInstance(null).initiateFieldsFromServer(result.getName(), Title.ATUDAI ,
						result.getLastMining(), result.getTotalTechoins(), result.getBankAccount());
				
			}

	
		}
	  

	  
}






