package com.technion.coolie.letmein;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.letmein.model.InvitationDatabaseHelper;
import com.technion.coolie.letmein.model.adapters.AbstractInvitationAdapter;
import com.technion.coolie.letmein.model.adapters.InvititationAdapter;
import com.technion.coolie.letmein.model.adapters.MockInvitationAdapter;

public class MainActivity extends CoolieActivity {
	private final String LOG_TAG = Consts.LOG_PREFIX + getClass().getSimpleName();
	private InvitationDatabaseHelper databaseHelper = null;

	private ListView inviteList;
	private Button loginButton;
	private boolean isLoggedIn;

	private InvitationDatabaseHelper getHelper() {
		if (databaseHelper == null)
			databaseHelper = OpenHelperManager.getHelper(this, InvitationDatabaseHelper.class);

		return databaseHelper;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmi_activity_main);

		inviteList = (ListView) findViewById(R.id.lmi_invite_list_view);

		loginButton = (Button) findViewById(R.id.lmi_login_button);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
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

	private class UpdateInvitationsTask extends AsyncTask<Void, Void, AbstractInvitationAdapter> {
		@Override
		protected AbstractInvitationAdapter doInBackground(final Void... params) {
			// For better performance:
			isLoggedIn = isLoggedIn || isUserLoggedIn();

			if (isLoggedIn)
				return new InvititationAdapter(MainActivity.this, getHelper());

			return new MockInvitationAdapter(MainActivity.this);
		}

		@Override
		protected void onPostExecute(final AbstractInvitationAdapter adapter) {
			inviteList.setAdapter(adapter);

			if (isLoggedIn)
				loginButton.setVisibility(View.GONE);
		}
	}

	private boolean isUserLoggedIn() {
		Log.i(LOG_TAG, "Checking for user login");
		// TODO: change to a real check.
		return getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE).getBoolean(
				Consts.IS_LOGGED_IN, false);
	}

	public void inviteNewFriend() {
		startActivity(new Intent(MainActivity.this, InvitationActivity.class));
	}

	public void emptyStateListAction(final View v) {
		inviteNewFriend();
	}
}
