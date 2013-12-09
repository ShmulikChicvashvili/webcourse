package com.technion.coolie.letmein.model.adapters;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.util.Log;

import com.technion.coolie.R;
import com.technion.coolie.letmein.Consts;
import com.technion.coolie.letmein.model.Contract;
import com.technion.coolie.letmein.model.Invitation;

public class InvitationCursorAdapter extends BaseInvitationAdapter {
	private final String LOG_TAG = Consts.LOG_PREFIX + getClass().getSimpleName();
	private static final long NUM_ITEMS_TO_LOAD = 20;
	private final List<Invitation> invitations;

	private final Context context;

	private Invitation invitationFromCursor(final Cursor c) throws ParseException {
		final Invitation.Builder builder = Invitation.builder();

		// TODO:: Figure how is date actually formatted (currently, not sure...)
		builder.date(DateFormat.getDateFormat(context).parse(
				c.getString(c.getColumnIndex(Contract.Invitation.DATE))));

		builder.contactId(c.getString(c.getColumnIndex(Contract.Invitation.CONTACT_ID)));

		builder.status(Enum.valueOf(Invitation.Status.class,
				c.getString(c.getColumnIndex(Contract.Invitation.STATUS))));

		return builder.build();
	}

	private List<Invitation> getInvitations() {
		final List<Invitation> $ = new ArrayList<Invitation>();

		final Cursor c = context.getContentResolver().query(Contract.Invitation.contentUri, null,
				null, null, Contract.Invitation.DATE + " ASC " + " LIMIT " + NUM_ITEMS_TO_LOAD);

		if (c == null)
			return $;

		while (c.moveToNext())
			try {
				$.add(invitationFromCursor(c));
			} catch (final ParseException e) {
				Log.e(LOG_TAG, "getInvitations(): Couldn't read invitations", e);
				throw new RuntimeException(e);
			}

		return $;
	}

	public InvitationCursorAdapter(final Context context) {
		super(context);

		this.context = context;
		this.invitations = getInvitations();
	}

	@Override
	protected List<Invitation> getFullDataset() {
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
