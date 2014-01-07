package com.technion.coolie.tecmind;

import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
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
import com.facebook.RequestAsyncTask;
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
	RelativeLayout mineLayout;
	LinearLayout progressBar;
	Session currentSession;
	Session.NewPermissionsRequest newPermissionsRequest;
	List<String> permissions;
	String userId;
	TechmineAPI connector = new TechmineAPI();
	List<TecPost> userPostsFromServer;

	public static Date exMiningDate;
	public static Date newMiningDate;

	int exTotal;
	int exLikesCounter;
	int exCommentsCounter;
	int exPostsCounter;

	public static int totalDelta;
	public static int postsDelta;
	public static int commentsDelta;
	public static int likesDelta;

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
				Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
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
						System.out.println("*****After Mining******");
						System.out
								.println("The number of posts after mining is:"
										+ User.getUserInstance(null).postsNum);
						System.out
								.println("The number of comments after mining is:"
										+ User.getUserInstance(null).commentsNum);
						System.out
								.println("The number of likes after mining is:"
										+ User.getUserInstance(null).likesOnPostsNum);
						System.out.println("The amount of Techoins i have is:"
								+ User.getUserInstance(null).totalTechoins);
						System.out.println("The last mining date is:"
								+ User.getUserInstance(null).lastMining
										.toString());
						/* sets the counters diffs */
						totalDelta = User.getUserInstance(null).totalTechoins
								- exTotal;
						newMiningDate = User.getUserInstance(null).lastMining;
						postsDelta = User.getUserInstance(null).postsNum
								- exPostsCounter;
						commentsDelta = User.getUserInstance(null).commentsNum
								- exCommentsCounter;
						likesDelta = (User.getUserInstance(null).likesNum + User
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

					private void updateUserTitle() {
						System.out.println("*****updateUserTitle******");
						System.out.println(String.valueOf(totalDelta));
						int userTotalTechions = User.getUserInstance(null).totalTechoins;
						Title oldTitle = User.getUserInstance(null).title;
						String name = User.getUserInstance(null).name;
						if (userTotalTechions < 1000) {
							User.getUserInstance(null).title = Title.ATUDAI;
							if (totalDelta > 0)
								publishToTechmind(name + " mined "
										+ String.valueOf(totalDelta)
										+ " Techions!!");

						} else if (userTotalTechions >= 1000
								&& userTotalTechions < 2000) {
							User.getUserInstance(null).title = Title.NERD;
							if (oldTitle.compareTo(Title.NERD) < 0) {
								// post in facebook!
								publishToTechmind(name
										+ " mined "
										+ String.valueOf(totalDelta)
										+ " Techions!! and he's now a *** NERD ***");
							}
						} else if (userTotalTechions >= 2000
								&& userTotalTechions < 3000) {
							User.getUserInstance(null).title = Title.KNIGHT_NERD;
							if (oldTitle.compareTo(Title.KNIGHT_NERD) < 0) {
								// post in facebook!
								publishToTechmind(name
										+ " mined "
										+ String.valueOf(totalDelta)
										+ " Techions!! and he's now a *** KNIGHT_NERD ***");
							}
						} else {
							User.getUserInstance(null).title = Title.SUPER_NERD;
							if (oldTitle.compareTo(Title.SUPER_NERD) < 0) {
								// post in facebook!
								publishToTechmind(name
										+ " mined "
										+ String.valueOf(totalDelta)
										+ " Techions!! and he's now a *** SUPER_NERD ***");
							}
						}
					}
				}).executeAsync();
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
					+ User.getUserInstance(null).likesOnPostsNum + "\n")
					.getBytes());

			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
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
				return;
			}
			System.out.println("*****isSubsetOf******");
			Bundle postParams = new Bundle();
			postParams.putString("message", message);
			Request.Callback callback = new Request.Callback() {
				public void onCompleted(Response response) {

					System.out.println("*****onCompleted******");
				}
			};

			Request request = new Request(session, "me/feed", postParams,
					HttpMethod.POST, callback);

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();
		}
		System.out.println("*****session == null******");
	}

	void updateServer() {
		new ServerUpdateUserData().execute();

	}

	class ServerUpdateUserData extends AsyncTask<Void, Void, ReturnCode> {

		ReturnCode addUserMessage;
		ReturnCode updatePostsMessage;

		@Override
		protected ReturnCode doInBackground(Void... arg0) {
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
					User.getUserInstance(null).likesOnPostsNum);
			addUserMessage = connector.addUser(userToSever);

			List<TecPost> postsToServer = new LinkedList<TecPost>();
			for (Post p : User.getUserInstance(null).posts) {

				TecPost newTecPost = new TecPost(p.id, p.date, p.technionValue,
						p.userID, p.likesCount, p.commentCount, p.url,
						p.groupName, p.content, 0, null);
				postsToServer.add(newTecPost);
			}
			updatePostsMessage = connector.addTecPostList(postsToServer);
			return addUserMessage;
		}

		@Override
		protected void onPostExecute(ReturnCode result) {
			// Toast.makeText(getApplicationContext(), "updated user" +
			// addUserMessage.value(),
			// Toast.LENGTH_LONG).show();
			// Toast.makeText(getApplicationContext(),
			// updatePostsMessage.value(),
			// Toast.LENGTH_LONG).show();
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
					.getCommentCount(), null, null, null, 0, null));
			// TODO: change the null to the content from the server));
		}

		return ReturnValue.SUCCESS_FROM_SERVER;
	}

	class ServeGetAllPostsOfUser extends AsyncTask<Void, Void, List<TecPost>> {

		@Override
		protected List<TecPost> doInBackground(Void... arg0) {
			Date lastMining = Utilities.parseDate("2013-08-30T16:30:00+0000");
			TecUser userToSever = new TecUser(userId, null, null, lastMining,
					0, 0, 0, 0, 0, 0);
			return connector.getAllUserPosts(userToSever);

		}

	}

}
