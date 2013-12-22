package com.technion.coolie.tecmind;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import junit.framework.Assert;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.tecmind.BL.Post;
import com.technion.coolie.tecmind.BL.Title;
import com.technion.coolie.tecmind.BL.User;
import com.technion.coolie.tecmind.BL.Utilities;
import com.technion.coolie.tecmind.server.ReturnCode;
import com.technion.coolie.tecmind.server.TecPost;
import com.technion.coolie.tecmind.server.TecUser;
import com.technion.coolie.tecmind.server.TecUserTitle;
import com.technion.coolie.tecmind.server.TechmineAPI;

public class MainActivity extends CoolieActivity {
	LinearLayout progressBar;
	RelativeLayout myTitleLayout;
		TechmineAPI connector = new TechmineAPI();
		String userId = null;
		String userName;
		Session currentSession;
		TecUser tecUser;
		List<TecPost> userPostsFromServer;
		TextView total;
		TextView Mylevel;
	
		public void myAccountNav(View view) {
		    Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
		    startActivity(intent);
		    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}

		public void mineNav(View view) {
		    Intent intent = new Intent(MainActivity.this, MineActivity.class);
		    startActivity(intent);
		    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
		public void myTitleNav(View view) {
		    Intent intent = new Intent(MainActivity.this, MainActivity.class);
		    startActivity(intent);
		    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
		
		
		void firstInitiate() {
			setContentView(R.layout.techmind_activity_my_title);
		    progressBar = (LinearLayout) findViewById(R.id.progressBarLayout);
		    myTitleLayout = (RelativeLayout) findViewById(R.id.my_title_main_layout);
		    progressBar.setVisibility(View.VISIBLE);
			myTitleLayout.setVisibility(View.INVISIBLE);
		}
		
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
//		  new ServerRemoveUser().execute();
	    super.onCreate(savedInstanceState);
	    
	    firstInitiate();
	    
	    addInnerNavigationDrawer(R.layout.techmind_drawer_btn);
	    total = (TextView) findViewById(R.id.total_text);
	    Mylevel = (TextView) findViewById(R.id.level_text);	
	    
	   
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
	            		
	            		
	            		/******************************************************************/
	            		//TODO: NEED TO THINK HOW TO DO IT!!!!!!!
	            		
	            		/* tries to get the userId from the device's storage */ //TODO: add the writing to data storage
	            		try {
							FileInputStream fileToRead = openFileInput("techmine");
							InputStreamReader isr = new InputStreamReader(fileToRead);
						    char[] inputBuffer = new char[100];
						    isr.read(inputBuffer);
						    String readString = new String(inputBuffer);
						    
						    String[] parts = readString.split("\\*");
						    userId = parts[0]; 
						    userName = parts[1]; 
						    
//						    Toast.makeText(getApplicationContext(), readString, Toast.LENGTH_LONG).show();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

	            		if (userId == null) {
	            			/* updates the user id from Facebook */
	            			userId = user.getId();
	    	  	  		    userName = user.getFirstName() + " " + user.getLastName();
	            			
	          			  /* adds the user ID to data storage of the device at the first time */
			    	  	  	FileOutputStream outputStream;
			    	  	  	try {
			    	  	  	  outputStream = openFileOutput("techmine", Context.MODE_PRIVATE);
			    	  	  	  outputStream.write((userId + "*").getBytes());
			    	  	  	  outputStream.write(userName.getBytes());
			    	  	  	  outputStream.close();
			    	  	  	} catch (Exception e) {
			    	  	  	  e.printStackTrace();
			    	  	  	}
	          		
	            		}
	            		
//	            		userName = user.getFirstName() + " " + user.getLastName();
	            		
	  	  		      	initiateFromServer();
	  	  		      
	  	  		      	initiateActivityFields();
	  	  		      	
	  	  		      	/* initiates fields in case of "My Account", user didn't mine yet */
	  	  		      	MineActivity.exMiningDate = User.getUserInstance(null).lastMining;
	  	  		      	MineActivity.newMiningDate = null;
	  	  		      	MineActivity.totalDelta = 0;
	  	  		      	
	  	  		      	progressBar.setVisibility(View.INVISIBLE);
	  	  				myTitleLayout.setVisibility(View.VISIBLE);
	  	  			/******************************************************************/
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
	  
	  void initiateFromServer() {
		  try {
			  tecUser = new ServerGetUserData().execute().get();
			 
		  } catch (Exception e) {

		  } 

		  if (tecUser == null) {
			  /* adds the user to the server at the first time */
			  User.getUserInstance(userId);
			  User.getUserInstance(null).name = userName;
			  User.getUserInstance(null).title = Title.ATUDAI;
			  new ServerAddUser().execute();
		  }
		  else {
			  User.getUserInstance(tecUser.getId());
			  
			  User.getUserInstance(null).initiateFieldsFromServer(tecUser.getName(), 
					  Title.valueOf(tecUser.getTitle().value()),
						tecUser.getLastMining(), tecUser.getTotalTechoins(), tecUser.getBankAccount(), tecUser.postsNum, tecUser.commentsNum
						, tecUser.likesNum, tecUser.likeOnPostsNum);
			  try {
				  userPostsFromServer = new ServeGetAllPostsOfUser().execute().get();
			  } catch (Exception e) {

			  } 
	
			  for (TecPost tp : userPostsFromServer) {
				  User.getUserInstance(null).posts.add(new Post(tp.getId(), tp.getDate(),
							tp.getUserID(), tp.getLikesCount(), tp.getCommentCount()));
			}
		  } 
	  }
	  
	  
		private void initiateActivityFields() {
			User check = User.getUserInstance(null);
			total.setText(String.valueOf(User.getUserInstance(null).totalTechoins));
			String level = User.getUserInstance(null).title.value();
			Mylevel.setText(level);
			if (level.contentEquals("ATUDAI"))
				return;
			ImageView atudaiStar = (ImageView) findViewById(R.id.atudai_star);
			atudaiStar.setVisibility(ImageView.INVISIBLE);
			if (level.contentEquals("NERD")) {
				ImageView nerdStar = (ImageView) findViewById(R.id.cool_nerd_star);
				nerdStar.setVisibility(ImageView.VISIBLE);
			} else if (level.contentEquals("KNIGHT NERD")) {
				ImageView soliderNerdStar = (ImageView) findViewById(R.id.solider_nerd_star);
				soliderNerdStar.setVisibility(ImageView.VISIBLE);
			} else if (level.contentEquals("SUPER NERD")) {
				ImageView superNerdStar = (ImageView) findViewById(R.id.super_nerd_star);
				superNerdStar.setVisibility(ImageView.VISIBLE);
			}
		
	}

		
		
	  class ServerGetUserData extends AsyncTask<Void, Void, TecUser> {

			@Override
			protected TecUser doInBackground(Void... arg0) {
				TecUser userToServer = new TecUser(userId, null, null, null, 0, 0, 0, 0, 0, 0);
				return connector.getUser(userToServer);
				
			}

			@Override
			protected void onPostExecute(TecUser result) {
				Toast.makeText(getApplicationContext(), "Hello " + userName,
		        		Toast.LENGTH_LONG).show();
			}

	
		}
	  
	  class ServerAddUser extends AsyncTask<Void, Void, ReturnCode> {

			@Override
			protected ReturnCode doInBackground(Void... arg0) {
				Date lastMining = Utilities.parseDate("2013-08-30T16:30:00+0000");
				TecUser userToSever = new TecUser(userId, userName, TecUserTitle.ATUDAI, lastMining, 0, 0, 0, 0, 0, 0);
				return connector.addUser(userToSever);
				
			}

			@Override
			protected void onPostExecute(ReturnCode result) {

			}

		}
	  
	  class ServeGetAllPostsOfUser extends AsyncTask<Void, Void, List<TecPost>> {

			@Override
			protected List<TecPost> doInBackground(Void... arg0) {
				Date lastMining = Utilities.parseDate("2013-08-30T16:30:00+0000");
				TecUser userToSever = new TecUser(userId, null, null, lastMining, 0, 0, 0, 0, 0, 0);
				return connector.getAllUserPosts(userToSever);
				
			}

		}
	  
	  class ServerRemoveUser extends AsyncTask<Void, Void, ReturnCode> {

			@Override
			protected ReturnCode doInBackground(Void... arg0) {
				Date lastMining = Utilities.parseDate("2013-08-30T16:30:00+0000");
				TecUser userToSever = new TecUser("574717953", null, null, lastMining, 0, 0, 0, 0, 0, 0);
				return connector.removeUser(userToSever);
				
			}

		}
	  
	
		
}
		

	





