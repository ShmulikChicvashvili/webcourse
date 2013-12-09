package com.technion.coolie.letmein;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.widget.SearchView;
import com.technion.coolie.R;
import com.technion.coolie.letmein.model.adapters.BaseInvitationAdapter;
import com.technion.coolie.letmein.model.adapters.InvitationAdapter;
import com.technion.coolie.letmein.model.adapters.MockInvitationAdapter;

public class MainActivity extends DatabaseActivity implements
		InvitationListFragment.AdapterSupplier, EmptyInvitationListFragment.OnNewInvitationListener {

	private final String LOG_TAG = Consts.LOG_PREFIX + getClass().getSimpleName();
	private BaseInvitationAdapter invitationAdapter;

	private Button loginButton;
	private boolean isLoggedIn;

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = this.getSupportMenuInflater();
		inflater.inflate(R.menu.lmi_menu, menu);

		final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) menu.findItem(R.id.lmi_search).getActionView();
		if (null != searchView) {
			searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			searchView.setIconifiedByDefault(false);
		}

		final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(final String newText) {
				getAdapter().getFilter().filter(newText);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(final String query) {
				getAdapter().getFilter().filter(query);
				return true;
			}
		};

		searchView.setOnQueryTextListener(queryTextListener);
		searchView.setQueryHint(getResources().getString(R.string.lmi_search_hint));

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmi_activity_main);

		loginButton = (Button) findViewById(R.id.lmi_login_button);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();

		// For better performance:
		isLoggedIn = isLoggedIn || isUserLoggedIn();

		if (isLoggedIn)
			loginButton.setVisibility(View.GONE);

		new UpdateInvitationsTask().execute();
	}

	private class UpdateInvitationsTask extends AsyncTask<Void, Void, BaseInvitationAdapter> {
		@Override
		protected BaseInvitationAdapter doInBackground(final Void... params) {
			if (isLoggedIn)
				return new InvitationAdapter(MainActivity.this, getHelper());

			return new MockInvitationAdapter(MainActivity.this);
		}

		@Override
		protected void onPostExecute(final BaseInvitationAdapter adapter) {
			invitationAdapter = adapter;

			final Fragment fragment = adapter.isEmpty() ? new EmptyInvitationListFragment()
					: new InvitationListFragment();

			getSupportFragmentManager().beginTransaction()
					.replace(R.id.lmi_invitation_list_container, fragment).commit();
		}
	}

	private boolean isUserLoggedIn() {
		Log.i(LOG_TAG, "Checking for user login");
		// TODO: change to a real check.
		return getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE).getBoolean(
				Consts.IS_LOGGED_IN, false);
	}

	@Override
	public void onNewInvitation() {
		startActivity(new Intent(MainActivity.this, InvitationActivity.class));
	}

	@Override
	public BaseInvitationAdapter getAdapter() {
		return invitationAdapter;
	}
}
