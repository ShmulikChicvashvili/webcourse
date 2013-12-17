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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
	
	RelativeLayout mineLayout;
	LinearLayout progressBar;
	Session currentSession;
	Session.NewPermissionsRequest newPermissionsRequest;
	List<String> permissions;
	String userId;
	TechmineAPI connector = new TechmineAPI();
	public static Date exMiningDate;
	public static Date newMiningDate;
	int exTotal;
	public static int totalDelta;
	
	public void myAccountNav(View view) {
	    Intent intent = new Intent(MineActivity.this, MyAccountActivity.class);
	    startActivity(intent);
	    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public void mineNav(View view) {
	    Intent intent = new Intent(MineActivity.this, MineActivity.class);
	    startActivity(intent);
	    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	public void myTitleNav(View view) {
	    Intent intent = new Intent(MineActivity.this, MainActivity.class);
	    startActivity(intent);
	    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.techmind_activity_mine);
	    
	    mineLayout = (RelativeLayout) findViewById(R.id.mine_layout);
	    progressBar = (LinearLayout) findViewById(R.id.progressBarLayout);
	    progressBar.setVisibility(View.VISIBLE);

	    addInnerNavigationDrawer(R.layout.techmind_drawer_btn);
	    
	    currentSession = Session.getActiveSession();
        if (currentSession != null && currentSession.isOpened()) {
        	userId = User.getUserInstance(null).id;
        	
        	/* saves the last date of mining and the last total amount of Techoins */
        	exMiningDate = User.getUserInstance(null).lastMining;
        	exTotal = User.getUserInstance(null).totalTechoins;
        	/* mine the new posts, comments and likes */
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
	  		       
	  	           /* sets the total Techoins diff */
	  	           totalDelta = User.getUserInstance(null).totalTechoins - exTotal;
	  	           newMiningDate = User.getUserInstance(null).lastMining;
	  		       
	  	            /* updates the last mining in server */
	  		       updateServer();
	  		       
	  		     System.out.println("*****After Mining******");
	  		     System.out.println("The number of posts after mining is:" + User.getUserInstance(null).postsNum);
	  		     System.out.println("The number of comments after mining is:" + User.getUserInstance(null).commentsNum);
	  		     System.out.println("The number of likes after mining is:" + User.getUserInstance(null).likesOnPostsNum);
	  		     System.out.println("The amount of Techoins i have is:" + User.getUserInstance(null).totalTechoins);
	  		     System.out.println("The last mining date is:" + User.getUserInstance(null).lastMining.toString());
	  		     
		  		    progressBar.setVisibility(View.GONE);
		  		    //mineLayout.setVisibility(View.GONE);
		  		    Intent intent = new Intent(MineActivity.this, MyAccountActivity.class);
		  		    startActivity(intent);
		  		    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
					
			}

		}
	  
	  
	  

	  
}






