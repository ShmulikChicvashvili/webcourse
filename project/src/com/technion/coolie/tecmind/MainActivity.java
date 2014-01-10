package com.technion.coolie.tecmind;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.tecmind.BL.Post;
import com.technion.coolie.tecmind.BL.ReturnValue;
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
	TextView total;
	TextView Mylevel;
	boolean pendingPublishReauthorization = false;
	public void myAccountNav(View view) {
		Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	public void mineNav(View view) {
		Intent intent = new Intent(MainActivity.this, MineActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	public void myTitleNav(View view) {
		Intent intent = new Intent(MainActivity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	public void topPostsNav(View view) {
		Intent intent = new Intent(MainActivity.this, TopPostsActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	public void topMinersNav(View view) {
		Intent intent = new Intent(MainActivity.this, TopMinersActivity.class);
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
//		 new ServerRemoveUser().execute();
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

						/*
						 * callback after Graph API response with user
						 * object
						 */
						@Override
						public void onCompleted(GraphUser user, Response response) {
							
							if (user != null) {
								System.out.println("*****user != null******");
								/* tries to read from file "techmine" that saved in th internal storage of the device 
								at the last use */
								readFromFile();

								/* if file is not exist, try to get user from server, 
								 * if not found at the server reads from Facebook
								 * for the first time. */
								if (userId == null) {

									/* updates the user id and name from Facebook */
									userName = user.getFirstName() + " " + user.getLastName();
									userId = user.getId();

									/* tries to get user from the server. 
									 * if the user cannot be initiate from the server 
									 * it means this is the first use of the app */
									ReturnValue res = getFromServer();
									if (res.equals(ReturnValue.FAIL_FROM_SERVER)
											|| res.equals(ReturnValue.USER_DOESNT_EXIST_IN_SERVER)) {
										
										User.getUserInstance(userId).name = userName;
										//*** post in the facebook that the user has joined techmind **//
										publishJoinedToTechmind();
										/* adds the user to the server */
										addUserToServer();
										
									}
									/* if success to get user details from the server, initiate all user's details */
									else { 
										initiateFromServer();
									}
									
									/* writes the user details to 
									 * internal storage of the device */
									writeToFile();
									
								}
											
								initiateActivityFields();

								/*
								 * initiates fields in case of
								 * "My Account", user didn't mine yet
								 */
								MineActivity.exMiningDate = User.getUserInstance(null).lastMining;
								MineActivity.newMiningDate = null;
								MineActivity.totalDelta = 0;

								progressBar.setVisibility(View.INVISIBLE);
								myTitleLayout.setVisibility(View.VISIBLE);
							}
						}

					}).executeAsync();
				}
			}
		}, Arrays.asList("user_groups", "user_activities", "user_likes","read_stream"));
	}

	private void writeToFile() {
		/*
		 * adds the user ID to internal
		 * storage of the device at the
		 * first time
		 */
		FileOutputStream outputStream;
		try {
			outputStream = openFileOutput("techmine_user_details", Context.MODE_PRIVATE);
			outputStream.write(("first ini").getBytes());
			outputStream.write((User.getUserInstance(null).id + "\n").getBytes());
			outputStream.write((User.getUserInstance(null).name + "\n").getBytes());
			outputStream.write((User.getUserInstance(null).title.value() + "\n").getBytes());
			
			Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SSSS");
			String lastMiningString = formatter.format(User.getUserInstance(null).lastMining);
			
			outputStream.write((lastMiningString + "\n").getBytes());
			outputStream.write((User.getUserInstance(null).totalTechoins + "\n").getBytes());
			outputStream.write((User.getUserInstance(null).postsNum + "*"
					+ User.getUserInstance(null).commentsNum + "*"
					+ User.getUserInstance(null).likesNum + "*"
					+ User.getUserInstance(null).likesOnPostsNum + "\n").getBytes());

			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void readFromFile() {
		/* tries to get the userId from the device's storage */ 
		try {
			
			FileInputStream fileToRead = openFileInput("techmine_user_details");
			InputStreamReader isr = new InputStreamReader(fileToRead);
			char[] inputBuffer = new char[100];
			isr.read(inputBuffer);
			String readString = new String(inputBuffer);

			String[] userDetails = readString.split("\\n");
			userId = userDetails[0];
			userName = userDetails[1];
			
			User.getUserInstance(userId).name = userName;
			
			User check = User.getUserInstance(null);
			
			User.getUserInstance(null).title = Title.valueOf(userDetails[2]);
			User.getUserInstance(null).lastMining = Utilities.parseDate(userDetails[3]);
			User.getUserInstance(null).totalTechoins = Integer.parseInt(userDetails[4]);
			
			String[] userCounters = userDetails[5].split("\\*");
			User.getUserInstance(null).postsNum = Integer.parseInt(userCounters[0]);
			User.getUserInstance(null).commentsNum = Integer.parseInt(userCounters[1]);
			User.getUserInstance(null).likesNum = Integer.parseInt(userCounters[2]);
			User.getUserInstance(null).likesOnPostsNum = Integer.parseInt(userCounters[3]);
			
//			User.getUserInstance(null).name = userName;
//			String counters = parts[2];
//			String[] countersParts = readString.split(" ");
//			User.getUserInstance(null).postsNum = Integer.parseInt(countersParts[0]);
//			User.getUserInstance(null).commentsNum = Integer.parseInt(countersParts[1]);
//			User.getUserInstance(null).likesNum = Integer.parseInt(countersParts[2]);
//			User.getUserInstance(null).likesOnPostsNum = Integer.parseInt(countersParts[3]);

			// Toast.makeText(getApplicationContext(), readString,
			// Toast.LENGTH_LONG).show();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Session openActiveSession(Activity activity,
			boolean allowLoginUI, StatusCallback callback,
			List<String> permissions) {
		OpenRequest openRequest = new OpenRequest(activity).setPermissions(
				permissions).setCallback(callback);
		Session session = new Session.Builder(activity).build();
		if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState())
				|| allowLoginUI) {
			Session.setActiveSession(session);
			session.openForRead(openRequest);
			return session;
		}
		return null;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}
	
	private boolean isSubsetOf(List<String> subset,
	        List<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}

	private ReturnValue getFromServer() {
		try {
			tecUser = new ServerGetUserData().execute().get();
			if (tecUser == null) {
							return ReturnValue.USER_DOESNT_EXIST_IN_SERVER;
			}
			else {
				userId = tecUser.getId();
					}
		} catch (Exception e) {
			return ReturnValue.FAIL_FROM_SERVER;
		}
		return ReturnValue.SUCCESS_FROM_SERVER;	
	}

	private void addUserToServer() {
		/* adds the user to the server at the first time */
		User.getUserInstance(null).name = userName;
		User.getUserInstance(null).title = Title.ATUDAI;
		new ServerAddUser().execute();
	}
	
	private void initiateFromServer() {
		User.getUserInstance(userId).initiateFieldsFromServer(
				tecUser.getName(),
				Title.valueOf(tecUser.getTitle().value()),
				tecUser.getLastMining(), tecUser.getTotalTechoins(),
				tecUser.getBankAccount(), tecUser.postsNum,
				tecUser.commentsNum, tecUser.likesNum,
				tecUser.likeOnPostsNum);
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
			TecUser userToServer = new TecUser(userId, null, null, null, 0, 0,
					0, 0, 0, 0);
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
			TecUser userToSever = new TecUser(userId, userName,
					TecUserTitle.ATUDAI, lastMining, 0, 0, 0, 0, 0, 0);
			return connector.addUser(userToSever);

		}

		@Override
		protected void onPostExecute(ReturnCode result) {

		}

	}

	class ServerRemoveUser extends AsyncTask<Void, Void, ReturnCode> {

		@Override
		protected ReturnCode doInBackground(Void... arg0) {
			Date lastMining = Utilities.parseDate("2013-08-30T16:30:00+0000");
			TecUser userToSever = new TecUser("574717953", null, null,
					lastMining, 0, 0, 0, 0, 0, 0);
			return connector.removeUser(userToSever);

		}

	}
	
	private void publishJoinedToTechmind() {
	    Session session = Session.getActiveSession();
	    if (session != null){
	        // Check for publish permissions    
	        List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(Arrays.asList("publish_actions"), permissions)) {
	            pendingPublishReauthorization = true;
	            Session.NewPermissionsRequest newPermissionsRequest = new Session
	                    .NewPermissionsRequest(this, Arrays.asList("publish_actions"));
	        session.requestNewPublishPermissions(newPermissionsRequest);
	            return;
	        }

	        Bundle postParams = new Bundle();
	        postParams.putString("message", "Has just joined to Techmind");
	        Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	            }
	        };
	        Request request = new Request(session, "me/feed", postParams, 
	                              HttpMethod.POST, callback);
	        RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
	    }

	}

}
