package com.technion.coolie.letmein.model.adapters;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.technion.coolie.R;
import com.technion.coolie.letmein.Consts;
import com.technion.coolie.letmein.model.Contract;
import com.technion.coolie.letmein.model.Invitation;
import com.technion.coolie.letmein.model.InvitationDatabaseHelper;

public class InvitationAdapter extends BaseInvitationAdapter {
	private static final long NUM_ITEMS_TO_LOAD = 20;
	private final String LOG_TAG = Consts.LOG_PREFIX + getClass().getSimpleName();
	private final List<Invitation> invitations;
	private final InvitationDatabaseHelper databaseHelper;

	private List<Invitation> getInvitations(final InvitationDatabaseHelper databaseHelper) {
		try {
			return databaseHelper.getDataDao().queryBuilder()
					.orderBy(Contract.Invitation.DATE, true).limit(NUM_ITEMS_TO_LOAD).query();
		} catch (final SQLException e) {
			Log.e(LOG_TAG, "getInvitations(): Couldn't read invitations", e);
			throw new RuntimeException(e);
		}
	}

	public InvitationAdapter(final Context context, final InvitationDatabaseHelper databaseHelper) {
		super(context);
		this.databaseHelper = databaseHelper;
		invitations = getInvitations(databaseHelper);
	}

	@Override
	protected List<Invitation> getFullDataset() {
		return invitations;
	}

	@Override
	protected ContactView getContactViewById(final Long contactId) {
		Invitation i;

		try {
			i = databaseHelper.getDataDao().queryBuilder().where()
					.eq(Contract.Invitation.CONTACT_ID, contactId).queryForFirst();
		} catch (final SQLException e) {
			Log.e(LOG_TAG,
					"getContactViewById(String contactId): Couldn't get invitation by contactId", e);
			throw new RuntimeException(e);
		}

		final ContactView $ = new ContactView();

		$.ContactName = i.getContactName();
		$.ContactImageId = R.drawable.lmi_google_man;

		return $;
	}
}
