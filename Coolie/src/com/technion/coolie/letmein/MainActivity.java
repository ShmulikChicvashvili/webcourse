package com.technion.coolie.letmein;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.letmein.model.InvitationDatabaseHelper;
import com.technion.coolie.letmein.model.adapters.MockInvitationAdapter;

public class MainActivity extends CoolieActivity {
	private final String LOG_TAG = getClass().getSimpleName();
	private InvitationDatabaseHelper databaseHelper = null;

	private ListView inviteList;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmi_activity_main);

		inviteList = (ListView) findViewById(R.id.lmi_invite_list_view);
		inviteList.setAdapter(new MockInvitationAdapter(this));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	private InvitationDatabaseHelper getHelper() {
		if (databaseHelper == null)
			databaseHelper = OpenHelperManager.getHelper(this, InvitationDatabaseHelper.class);

		return databaseHelper;
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.lmi_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}
}
