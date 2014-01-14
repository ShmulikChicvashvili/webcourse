package com.technion.coolie.tecmind;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.OpenGraphAction;
import com.facebook.widget.FacebookDialog;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.CoolieNotification;
import com.technion.coolie.CooliePriority;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieModule;
import com.technion.coolie.tecmind.BL.Comment;
import com.technion.coolie.tecmind.BL.Like;
import com.technion.coolie.tecmind.BL.Mine;
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

public class MineActivity extends CoolieActivity {
	boolean pendingPublishReauthorization = false;
	static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	UiLifecycleHelper mUiHelper;
	RelativeLayout mineLayout;
	LinearLayout progressBar;
	Session currentSession;
	Session.NewPermissionsRequest newPermissionsRequest;
	List<String> permissions;
	String userId;
	String userName;
	TechmineAPI connector = new TechmineAPI();
	List<TecPost> userPostsFromServer;

	public static Date exMiningDate;
	public static Date newMiningDate;

	int exTotal;
	int exLikesCounter;
	int exCommentsCounter;
	int exPostsCounter;

	int LIKE = 0;
	int COMMENT = 0;

	public static int totalDelta;
	public static int postsDelta;
	public static int commentsDelta;
	public static int likesDelta;

	public static List<Like> likesFromServer;
	public static List<Comment> commentsFromServer;

	public void myAccountNav(View view) {
		Intent intent = new Intent(MineActivity.this, MyAccountActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	public void mineNav(View view) {
		Intent intent = new Intent(MineActivity.this, MineActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	public void myTitleNav(View view) {
		Intent intent = new Intent(MineActivity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.techmind_activity_mine);
		mineLayout = (RelativeLayout) findViewById(R.id.mine_layout);
		progressBar = (LinearLayout) findViewById(R.id.progressBarLayout);
		progressBar.setVisibility(View.VISIBLE);

		addInnerNavigationDrawer(R.layout.techmind_drawer_btn);
		if (savedInstanceState != null) {
			System.out.println("*****StatusCallback call******");
			pendingPublishReauthorization = savedInstanceState.getBoolean(
					PENDING_PUBLISH_KEY, false);
		}
		mUiHelper = new UiLifecycleHelper(this, mCallback);
		mUiHelper.onCreate(savedInstanceState);
		currentSession = Session.getActiveSession();
		if (currentSession != null && currentSession.isOpened()) {
			userId = User.getUserInstance(null).id;

			/*
			 * saves the last date of mining, the last total amount of Techoins
			 * and the counters of posts, likes and comments
			 */
			exMiningDate = User.getUserInstance(null).lastMining;
			exTotal = User.getUserInstance(null).totalTechoins;
			exLikesCounter = User.getUserInstance(null).likesNum
					+ User.getUserInstance(null).likesOnPostsNum;
			exCommentsCounter = User.getUserInstance(null).commentsNum;
			exPostsCounter = User.getUserInstance(null).postsNum;

			/* gets the user's posts from server */
			if (getPostsOfUserFromServer().equals(ReturnValue.FAIL_FROM_SERVER)) {
				Toast.makeText(getApplicationContext(), "Connection Error",
						Toast.LENGTH_LONG).show();
			}

			/* mine the new posts, comments and likes */
			mining();

		}
	}

	void mining() {

		/* make Facebook API call */
		new Request(currentSession, userId + "/feed", null, HttpMethod.GET,
				new Request.Callback() {

					public void onCompleted(Response response) {
						GraphObject gO = response.getGraphObject();
						Mine.getMineInstance(userId).mineUser(gO);
						Mine.getMineInstance(null).endMining();
						 /* mines from other users */
						 TecUser currentUserFromServer = null;
						 try {
						 currentUserFromServer = new
						 ServerGetUserData().execute().get();
						 } catch (Exception e) {
						 e.printStackTrace();
						 }
						 if (currentUserFromServer == null) {
							 currentUserFromServer = new TecUser();
						 currentUserFromServer.setLikesOthers(0);
						 currentUserFromServer.setCommentsOthers(0);
						 }
						
						
						 TecUser otherUserFromFile =
						 readOtherCountersFromFile();
						 if (currentUserFromServer.getLikesOthers() >
						 otherUserFromFile.getLikesOthers()) {
						 User.getUserInstance(null).likesOthers =
						 currentUserFromServer.getLikesOthers();
						 pushOtherNotifications(LIKE);
						
						 }
						 if (currentUserFromServer.getCommentsOthers() >
						 otherUserFromFile.getCommentsOthers()) {
						 User.getUserInstance(null).commentsOthers =
						 currentUserFromServer.getCommentsOthers();
						 pushOtherNotifications(COMMENT);
						 }
						
						 /* sets the counters diffs */
						 totalDelta = User.getUserInstance(null).totalTechoins
						 - exTotal;
						 newMiningDate =
						 User.getUserInstance(null).lastMining;
						 postsDelta = User.getUserInstance(null).postsNum
						 - exPostsCounter;
						 commentsDelta =
						 User.getUserInstance(null).commentsNum
						 - exCommentsCounter;
						 likesDelta = (User.getUserInstance(null).likesNum +
						 User
						 .getUserInstance(null).likesOnPostsNum)
						 - exLikesCounter;
						 updateUserTitle();
						 writeToFile();
						 updateServer();

						progressBar.setVisibility(View.GONE);
						// mineLayout.setVisibility(View.GONE);
						Intent intent = new Intent(MineActivity.this,
								MyAccountActivity.class);
						startActivity(intent);
						overridePendingTransition(android.R.anim.fade_in,
								android.R.anim.fade_out);

					}

				}).executeAsync();
	}

	private void pushOtherNotifications(int type) {
		CoolieNotification notification;
		try {
			if (type == LIKE) {
				notification = new CoolieNotification("TecMine", "You mined "
						+ User.getUserInstance(null).likesOthers
						+ " Techoins for likes on other's posts!",
						(Activity) CoolieModule.TECMIND.getActivity()
								.newInstance(),
						CooliePriority.IMMEDIATELY, true,
						MineActivity.this);
			} else { // type == COMMENT
				notification = new CoolieNotification("TecMine", "You mined "
						+ User.getUserInstance(null).commentsOthers
						+ " Techoins for comments on other's posts!",
						(Activity) CoolieModule.TECMIND.getActivity()
								.newInstance(),
								CooliePriority.IMMEDIATELY, true,
						MineActivity.this);
			}

			notification.sendNotification();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void writeToFile() {
		/*
		 * adds the user ID to internal storage of the device at the first time
		 */
		FileOutputStream outputStream;
		try {
			outputStream = openFileOutput("techmine_user_details",
					Context.MODE_PRIVATE);
			outputStream.write((User.getUserInstance(null).id + "\n")
					.getBytes());
			outputStream.write((User.getUserInstance(null).name + "\n")
					.getBytes());
			outputStream
					.write((User.getUserInstance(null).title.value() + "\n")
							.getBytes());

			Format formatter = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss+SSSS");
			String lastMiningString = formatter.format(User
					.getUserInstance(null).lastMining);

			outputStream.write((lastMiningString + "\n").getBytes());
			outputStream
					.write((User.getUserInstance(null).totalTechoins + "\n")
							.getBytes());
			outputStream.write((User.getUserInstance(null).postsNum + "*"
					+ User.getUserInstance(null).commentsNum + "*"
					+ User.getUserInstance(null).likesNum + "*"
					+ User.getUserInstance(null).likesOnPostsNum + "*"
					+ User.getUserInstance(null).likesOthers + "*"
					+ User.getUserInstance(null).commentsOthers + "*"
					+ User.getUserInstance(null).weeklyTotlal + "\n")
					.getBytes());

			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TecUser readOtherCountersFromFile() {
		/* tries to get the user other counters from the device's storage */
		TecUser otherUser = null;
		try {
			FileInputStream fileToRead = openFileInput("techmine_user_details");
			InputStreamReader isr = new InputStreamReader(fileToRead);
			char[] inputBuffer = new char[100];
			isr.read(inputBuffer);
			String readString = new String(inputBuffer);

			String[] userDetails = readString.split("\\n");

			otherUser = new TecUser(userId, null, null, null, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0);

			String[] userCounters = userDetails[5].split("\\*");

			otherUser.setLikesOthers(Integer.parseInt(userCounters[4]));
			otherUser.setCommentsOthers(Integer.parseInt(userCounters[5]));

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return otherUser;
	}

	private void updateUserTitle() {
		System.out.println("*****updateUserTitle******");
		System.out.println(String.valueOf(totalDelta));
		int userTotalTechions = User.getUserInstance(null).totalTechoins;
		Title oldTitle = User.getUserInstance(null).title;
		String name = User.getUserInstance(null).name;
		if (userTotalTechions < 1000) {
			User.getUserInstance(null).title = Title.ATUDAI;
			// if (totalDelta > 0)
			publishToTechmind(name + " mined " + String.valueOf(totalDelta)
					+ " Techions!!");

		} else if (userTotalTechions >= 1000 && userTotalTechions < 2000) {
			User.getUserInstance(null).title = Title.NERD;
			if (oldTitle.compareTo(Title.NERD) < 0) {
				// post in facebook!
				publishToTechmind(name + " mined " + String.valueOf(totalDelta)
						+ " Techions!! and he's now a *** NERD ***");
			}
		} else if (userTotalTechions >= 2000 && userTotalTechions < 3000) {
			User.getUserInstance(null).title = Title.KNIGHT_NERD;
			if (oldTitle.compareTo(Title.KNIGHT_NERD) < 0) {
				// post in facebook!
				publishToTechmind(name + " mined " + String.valueOf(totalDelta)
						+ " Techions!! and he's now a *** KNIGHT_NERD ***");
			}
		} else {
			User.getUserInstance(null).title = Title.SUPER_NERD;
			if (oldTitle.compareTo(Title.SUPER_NERD) < 0) {
				// post in facebook!
				publishToTechmind(name + " mined " + String.valueOf(totalDelta)
						+ " Techions!! and he's now a *** SUPER_NERD ***");
			}
		}
	}

	void updateServer() {
		new ServerUpdateUserData().execute();

	}

	class ServerUpdateUserData extends AsyncTask<Void, Void, ReturnCode> {

		ReturnCode addUserMessage;
		ReturnCode updatePostsMessage;

		@Override
		protected ReturnCode doInBackground(Void... arg0) {
			List<TecUser> userToServerList = new ArrayList<TecUser>();
			TecUserTitle titleToServer = TecUserTitle.valueOf(User
					.getUserInstance(null).title.value());
			TecUser userToSever = new TecUser(User.getUserInstance(null).id,
					User.getUserInstance(null).name, titleToServer,
					User.getUserInstance(null).lastMining,
					User.getUserInstance(null).totalTechoins,
					User.getUserInstance(null).bankAccount,
					User.getUserInstance(null).commentsNum,
					User.getUserInstance(null).postsNum,
					User.getUserInstance(null).likesNum,
					User.getUserInstance(null).likesOnPostsNum,
					User.getUserInstance(null).likesOthers,
					User.getUserInstance(null).commentsOthers,
					User.getUserInstance(null).weeklyTotlal,
					User.getUserInstance(null).spamCount);

			userToServerList.add(userToSever);
			addUserMessage = connector.addUsers(userToServerList);

			List<TecPost> postsToServer = new LinkedList<TecPost>();
			for (Post p : User.getUserInstance(null).posts) {

				TecPost newTecPost = new TecPost(p.id, p.date, p.technionValue,
						p.userID, p.likesCount, p.commentCount, p.url,
						p.groupName, p.content);
				postsToServer.add(newTecPost);
			}
			updatePostsMessage = connector.addTecPosts(postsToServer);

			connector.updateUsers(Mine.getMineInstance(null)
					.getOtherUsersList()); // TODO: check it after update with
											// Shpigel

			return addUserMessage;
		}

	}

	private ReturnValue getPostsOfUserFromServer() {
		try {
			userPostsFromServer = new ServeGetAllPostsOfUser().execute().get();
		} catch (Exception e) {
			return ReturnValue.FAIL_FROM_SERVER;
		}

		for (TecPost tp : userPostsFromServer) {
			User.getUserInstance(null).posts.add(new Post(tp.getId(), tp
					.getDate(), tp.getUserID(), tp.getLikesCount(), tp
					.getCommentCount(), tp.getContent(), tp.getUrl(), tp
					.getGroup()));
		}

		return ReturnValue.SUCCESS_FROM_SERVER;
	}

	class ServeGetAllPostsOfUser extends AsyncTask<Void, Void, List<TecPost>> {

		@Override
		protected List<TecPost> doInBackground(Void... arg0) {
			Date lastMining = Utilities.parseDate("2013-08-30T16:30:00+0000");
			TecUser userToSever = new TecUser(userId, null, null, lastMining,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			return connector.getAllUserPosts(userToSever);

		}

	}

	class ServerGetUserData extends AsyncTask<Void, Void, TecUser> {

		@Override
		protected TecUser doInBackground(Void... arg0) {
			TecUser userToServer = new TecUser(userId, null, null, null, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0);
			return connector.getUser(userToServer);

		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("*****onActivityResult******");
		super.onActivityResult(requestCode, resultCode, data);
		//mUiHelper.onActivityResult(requestCode, resultCode, data);
		mUiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", "Success!");
	        }
	    });
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		System.out.println("*****onSaveInstanceState******");
		outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
		mUiHelper.onSaveInstanceState(outState);
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		System.out.println("*****onSessionStateChange******");
		if (state.isOpened()) {
			if (pendingPublishReauthorization
					&& state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
				pendingPublishReauthorization = false;
				updateUserTitle();
			}
		} else if (state.isClosed()) {
		}
	}	
	private void publishToTechmind(String message) {
		Session session = Session.getActiveSession();
		if (session != null) {
			System.out.println("*****session != null******");
			// Check for publish permissions
			List<String> permissions = session.getPermissions();
			if (!isSubsetOf(Arrays.asList("publish_actions"), permissions)) {
				System.out.println("*****!isSubsetOf******");
				pendingPublishReauthorization = true;
				Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
						this, Arrays.asList("publish_actions"));
				session.requestNewPublishPermissions(newPermissionsRequest);
				//return;
			}
			System.out.println("*****isSubsetOf******");
			Bundle postParams = new Bundle();
			postParams.putString("message", message);
			Request.Callback callback = new Request.Callback() {
				public void onCompleted(Response response) {
					if (response.getError() == null){
					System.out.println("*****onCompleted   message******");
					}else{
						System.out.println("*****onCompleted   Error****** " + response.getError().getErrorMessage());
					}
				}
			};
			Request request = new Request(session, "me/feed", postParams,
					HttpMethod.POST, callback);
			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();
		}
	}

	private boolean isSubsetOf(List<String> subset, List<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}
	@Override
	protected void onResume() {
	    super.onResume();
	    mUiHelper.onResume();
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    mUiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    mUiHelper.onDestroy();
	}
		
	private Session.StatusCallback mCallback = new Session.StatusCallback() {

		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			System.out.println("*****StatusCallback call******");
			onSessionStateChange(session, state, exception);
		}
	};
	
}
