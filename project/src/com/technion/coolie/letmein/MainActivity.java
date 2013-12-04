package com.technion.coolie.letmein;

import android.os.Bundle;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.letmein.model.InvitationDatabaseHelper;
import com.technion.coolie.letmein.model.adapters.MockInvitationAdapter;

public class MainActivity extends CoolieActivity {
	private final String LOG_TAG = Consts.LOG_PREFIX + getClass().getSimpleName();
	private InvitationDatabaseHelper databaseHelper = null;

	private ListView inviteList;
	private Button loginButton;
	private boolean isLoggedIn;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmi_activity_main);

		//inviteList = (ListView) findViewById(R.id.lmi_invite_list_view);
		//inviteList.setAdapter(new MockInvitationAdapter(this));

		loginButton = (Button) findViewById(R.id.lmi_login_button);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		new UpdateInvitationsTask().execute();
	}

	private class UpdateInvitationsTask extends
			AsyncTask<Void, Void, InvitationListAdapter> {
		@Override
		protected InvitationListAdapter doInBackground(Void... params) {
			InvitationListAdapter $ = new InvitationListAdapter(
					MainActivity.this);

			// For better performance:
			isLoggedIn = isLoggedIn || isUserLoggedIn();

			if (isLoggedIn) {
				// TODO: change to the real deal:
				$.setInvitations(new ArrayList<Invite>());
			} else {
				$.setInvitations(InvitationListAdapter.mockupData());
			}

			return $;
		}

		@Override
		protected void onPostExecute(InvitationListAdapter adapter) {
			inviteList.setAdapter(adapter);

			if (isLoggedIn) {
				loginButton.setVisibility(View.GONE);
			}
		}

	}

	private boolean isUserLoggedIn() {
		// TODO: change to a real check.
		return getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE)
				.getBoolean(Consts.IS_LOGGED_IN, false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lmi_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	static class Invite {
		public String name;
		public String timeOfArrival;
		public String imageIndex; // TODO: this is "id" in gilad terms.

		public Invite(String name, String timeOfArrival, String imageIndex) {
			this.name = name;
			this.timeOfArrival = timeOfArrival;
			this.imageIndex = imageIndex;
		}
	}

	public void inviteNewFriend() {
		startActivity(new Intent(MainActivity.this, InvitationActivity.class));
	}

	public void emptyStateListAction(View v) {
		inviteNewFriend();
	}
}
