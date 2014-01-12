package com.technion.coolie.tecmind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.technion.coolie.R;
import com.technion.coolie.tecmind.BL.User;
import com.technion.coolie.tecmind.server.TecUser;
import com.technion.coolie.tecmind.server.TecUserTitle;
import com.technion.coolie.tecmind.server.TechmineAPI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;

public class TopMinersActivity extends Activity {
	TechmineAPI connector = new TechmineAPI();
	List<TecUser> topMiners = new ArrayList<TecUser>();
	boolean pendingPublishReauthorization = false;
	static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	UiLifecycleHelper mUiHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.techmind_activity_top_miners);
		final ListView listView = (ListView) findViewById(R.id.minersListView);
		TopMinersListAdapter newPostAdapter = new TopMinersListAdapter(
				TopMinersActivity.this, topMiners);
		listView.setAdapter(newPostAdapter);
		  mUiHelper = new UiLifecycleHelper(this, mCallback);
	        mUiHelper.onCreate(savedInstanceState);
//		try {
//			List<TecUser> list = new ServerTopMiners().execute().get();
//			for (int i = 0; i < list.size(); i++)
//				topMiners.add((TecUser) list.get(i));
//		} catch (InterruptedException e) {
//
//		} catch (ExecutionException e) {
//
//		}
		User me = User.getUserInstance(null);
		// return connector.getTopBestMiners();
		TecUser user = new TecUser(me.id, me.name,
				TecUserTitle.valueOf(me.title.value()), me.lastMining,
				me.totalTechoins, me.bankAccount, me.commentsNum, me.postsNum,
				me.likesNum, me.likesOnPostsNum, me.likesOthers, me.commentsOthers, me.weeklyTotlal
				, me.spamCount);
		topMiners.add(user);
		if (savedInstanceState != null) {
			System.out.println("*****StatusCallback call******");
		    pendingPublishReauthorization = 
		        savedInstanceState.getBoolean(PENDING_PUBLISH_KEY, false);
		}
		postTopMinersOnFacebook();
	}
	
	   private Session.StatusCallback mCallback = new Session.StatusCallback() {
		  
	        @Override
	        public void call(Session session, SessionState state, Exception exception) {
	        	 System.out.println("*****StatusCallback call******");
	        	 onSessionStateChange(session, state, exception);
	        }
	    };

	class ServerTopMiners extends AsyncTask<Void, Void, List<TecUser>> {

		@Override
		protected List<TecUser> doInBackground(Void... params) {
			 //return connector.getTopBestMiners();
			return null;
		}

	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 System.out.println("*****onActivityResult******");
        super.onActivityResult(requestCode, resultCode, data);
        mUiHelper.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    System.out.println("*****onSaveInstanceState******");
	    outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
	    mUiHelper.onSaveInstanceState(outState);
	}
	private void postTopMinersOnFacebook(){
		for (TecUser user: topMiners){
			if (user.getId().contentEquals(User.getUserInstance(null).id)){
				 System.out.println("*****postTopMinersOnFacebook******");
				publishToTechmind(user.getName() + "is one of the B-E-S-T miners at techmined with " + String.valueOf(user.getTotalTechoins()) + " Techions!");
			}
		}
	}
	   private void onSessionStateChange(Session session, SessionState state, Exception exception) {
			System.out.println("*****onSessionStateChange******");
	        if (state.isOpened()) {
	            if (pendingPublishReauthorization && 
	                    state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
	                pendingPublishReauthorization = false;
	                postTopMinersOnFacebook();	            }
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
					System.out.println("*****onCompleted******");
				}
			};
			Request request = new Request(session,"me/feed", postParams,
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_miners, menu);
		return true;
	}

}
