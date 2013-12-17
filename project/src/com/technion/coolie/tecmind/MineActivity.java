package com.technion.coolie.tecmind;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
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
import com.technion.coolie.tecmind.BL.Post;
import com.technion.coolie.tecmind.BL.Title;
import com.technion.coolie.tecmind.BL.User;
import com.technion.coolie.tecmind.server.ReturnCode;
import com.technion.coolie.tecmind.server.TecPost;
import com.technion.coolie.tecmind.server.TecUser;
import com.technion.coolie.tecmind.server.TecUserTitle;
import com.technion.coolie.tecmind.server.TechmineAPI;


public class MineActivity extends CoolieActivity {
	
	Session currentSession;
	Session.NewPermissionsRequest newPermissionsRequest;
	List<String> permissions;
	String userId;
	TechmineAPI connector = new TechmineAPI();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.techmind_activity_mine);

	    currentSession = Session.getActiveSession();
        if (currentSession != null && currentSession.isOpened()) {
        	userId = User.getUserInstance(null).id;
            mining();
        }
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
	  		       updateServer();
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
	 
	  void updateServer() {
		  new ServerUpdateUserData().execute();
		  
	  }
	  
	  class ServerUpdateUserData extends AsyncTask<Void, Void, ReturnCode> {

		  	ReturnCode addUserMessage;
		  	ReturnCode updatePostsMessage;
			@Override
			protected ReturnCode doInBackground(Void... arg0) {
				TecUserTitle titleToServer = TecUserTitle.valueOf(User.getUserInstance(null).title.value());
				TecUser userToSever = new TecUser(User.getUserInstance(null).id, User.getUserInstance(null).name, 
						titleToServer , User.getUserInstance(null).lastMining, 
						User.getUserInstance(null).totalTechoins, User.getUserInstance(null).bankAccount
						,User.getUserInstance(null).commentsNum, User.getUserInstance(null).postsNum
						, User.getUserInstance(null).likesNum, User.getUserInstance(null).likesOnPostsNum);
				addUserMessage = connector.addUser(userToSever);	
				
				List<TecPost> postsToServer = new LinkedList<TecPost>();
				for (Post p : User.getUserInstance(null).posts) {
					TecPost newTecPost = new TecPost(p.id, p.date, p.technionValue, p.userID,
						     p.likesCount, p.commentCount);
					postsToServer.add(newTecPost);
				}
				updatePostsMessage = connector.addTecPostList(postsToServer);
				return addUserMessage;
			}

			@Override
			protected void onPostExecute(ReturnCode result) {
				Toast.makeText(getApplicationContext(), "Add user message: " + addUserMessage.value(),
		        		Toast.LENGTH_LONG).show();		
				Toast.makeText(getApplicationContext(), "Update posts message: " + updatePostsMessage.value(),
		        		Toast.LENGTH_LONG).show();		
			}

		}
	  
	  
	  

	  
}






