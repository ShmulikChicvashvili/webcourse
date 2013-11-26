package com.technion.coolie.letmein.model.adapters;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.technion.coolie.R;
import com.technion.coolie.letmein.model.Invitation;
import com.technion.coolie.letmein.model.InvitationDatabaseHelper;

public class InvititationAdapter extends AbstractInvitationAdapter {
	private static final long NUM_ITEMS_TO_LOAD = 20;
	private final String LOG_TAG = getClass().getSimpleName();
	private final List<Invitation> invitations;

	private List<Invitation> getInvitations(final InvitationDatabaseHelper databaseHelper) {
		try {
			return databaseHelper.getDataDao().queryBuilder().orderBy("date", true)
					.limit(NUM_ITEMS_TO_LOAD).query();
		} catch (final SQLException e) {
			Log.e(LOG_TAG, "getInvitations(): Couldn't read invitations", e);
			throw new RuntimeException(e);
		}
	}

	public InvititationAdapter(final Context context, final InvitationDatabaseHelper databaseHelper) {
		super(context);
		invitations = getInvitations(databaseHelper);
	}

	@Override
	protected List<Invitation> getInvitationList() {
		return invitations;
	}

	@Override
	protected ContactView getContactViewById(final String contactId) {
		final ContactView $ = new ContactView();
		$.ContactName = "Contact Name";
		$.ContactImageId = R.drawable.lmi_facebook_man;
		return $;
	}
}
