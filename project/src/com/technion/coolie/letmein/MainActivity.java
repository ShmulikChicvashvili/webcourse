package com.technion.coolie.letmein;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.technion.coolie.R;
import com.technion.coolie.letmein.model.adapters.BaseInvitationAdapter;
import com.technion.coolie.letmein.model.adapters.InvitationAdapter;
import com.technion.coolie.letmein.model.adapters.MockInvitationAdapter;

public class MainActivity extends DatabaseActivity implements
		InvitationListFragment.AdapterSupplier {

	private final String LOG_TAG = Consts.LOG_PREFIX + getClass().getSimpleName();

	private Button loginButton;
	private Button watchDemoButton;
	private Fragment welcomeFragment;

	private BaseInvitationAdapter invitationAdapter;
	private boolean isLoggedIn;
	private boolean isAddInvitationItemVisible = false;

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getSupportMenuInflater().inflate(R.menu.lmi_menu, menu);

		menu.findItem(R.id.lmi_done).setVisible(false);
		menu.findItem(R.id.lmi_discard).setVisible(false);
		menu.findItem(R.id.lmi_add_invitation).setVisible(isAddInvitationItemVisible);

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
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.lmi_add_invitation:
			onNewInvitation();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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

		watchDemoButton = (Button) findViewById(R.id.lmi_watch_demo_button);
		watchDemoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, DemoActivity.class));
			}
		});

		welcomeFragment = getSupportFragmentManager().findFragmentById(R.id.lmi_welcome_fragment);
	}

	@Override
	protected void onStart() {
		super.onStart();

		// For better performance:
		isLoggedIn = isLoggedIn || isUserLoggedIn();

		invitationAdapter = isLoggedIn ? new InvitationAdapter(MainActivity.this, getHelper())
				: new MockInvitationAdapter(MainActivity.this);

		Fragment listFragment = invitationAdapter.isEmpty() ? new EmptyInvitationListFragment()
				: new InvitationListFragment();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.lmi_invitation_list_container, listFragment).commit();

		if (!isLoggedIn) {
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				findViewById(R.id.lmi_body_fragment).setVisibility(View.GONE);
				findViewById(R.id.lmi_seperator).setVisibility(View.GONE);
			}
			return;
		}

		welcomeFragment.getView().setVisibility(View.GONE);

		isAddInvitationItemVisible = true;
		supportInvalidateOptionsMenu();

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			return;

		FrameLayout invitationViewFragme = (FrameLayout) findViewById(R.id.lmi_body_fragment);

		if (invitationAdapter.isEmpty()) {
			invitationViewFragme.setVisibility(View.GONE);
			return;
		}

		invitationViewFragme.setVisibility(View.VISIBLE);

		Fragment fragment = new InvitationViewFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(Consts.POSITION, 0);
		fragment.setArguments(bundle);

		getSupportFragmentManager().beginTransaction().replace(R.id.lmi_body_fragment, fragment)
				.commit();
	}

	private boolean isUserLoggedIn() {
		Log.i(LOG_TAG, "Checking for user login");
		// TODO: change to a real check.
		return getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE).getBoolean(
				Consts.IS_LOGGED_IN, false);
	}

	public void onNewInvitation() {
		startActivity(new Intent(MainActivity.this, InvitationActivity.class));
	}

	@Override
	public BaseInvitationAdapter getAdapter() {
		return invitationAdapter;
	}

	@Override
	public void changeInvitationView(int position) {
		Bundle args = new Bundle();
		args.putInt(Consts.POSITION, position);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Fragment fragment = new InvitationViewFragment();
			fragment.setArguments(args);

			getSupportFragmentManager().beginTransaction()
					.replace(R.id.lmi_body_fragment, fragment).commit();
		} else {
			Intent intent = new Intent(MainActivity.this, InvitationViewActivity.class);
			intent.putExtras(args);
			startActivity(intent);
		}
	}
}
