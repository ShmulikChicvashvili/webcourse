package com.technion.coolie.letmein;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.letmein.model.InvitationDatabaseHelper;

public class DatabaseActivity extends CoolieActivity {

	private InvitationDatabaseHelper databaseHelper = null;

	protected InvitationDatabaseHelper getHelper() {
		if (databaseHelper == null)
			databaseHelper = OpenHelperManager.getHelper(this, InvitationDatabaseHelper.class);

		return databaseHelper;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}
}
