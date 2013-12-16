package com.technion.coolie.letmein.model.adapters;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.technion.coolie.R;
import com.technion.coolie.letmein.model.Invitation;

public class MockInvitationAdapter extends BaseInvitationAdapter {
	private static final List<Invitation> mockInvitations = initInvitations();
	private static final Map<Long, ContactView> mockContactViewsById = initContactViews();

	private static final long DAD_ID = 0L;
	private static final long FRIEND_ID = 1L;
	private static final long ABED_ID = 2L;
	
	private static String DAD_NAME = "Dad";
	private static String FRIEND_NAME = "My best friend";
	private static String ABED_NAME = "Abed";

	private static List<Invitation> initInvitations() {
		final Calendar c = Calendar.getInstance();

		c.add(Calendar.HOUR_OF_DAY, 9);
		final Invitation dad = Invitation.builder().contactId(DAD_ID)
				.contactName(DAD_NAME).date(c.getTime()).build();

		c.add(Calendar.DAY_OF_YEAR, 2);
		final Invitation friend = Invitation.builder().contactId(FRIEND_ID)
				.contactName(FRIEND_NAME).date(c.getTime()).build();

		c.add(Calendar.WEEK_OF_YEAR, 1);
		final Invitation abed = Invitation.builder().contactId(ABED_ID)
				.contactName(ABED_NAME).date(c.getTime()).build();

		return Arrays.asList(dad, friend, abed);
	}

	private static Map<Long, ContactView> initContactViews() {
		final Map<Long, ContactView> $ = new HashMap<Long, ContactView>();

		final ContactView dad = new ContactView();
		dad.ContactName = DAD_NAME;
		dad.ContactImageId = R.drawable.lmi_dad;
		$.put(DAD_ID, dad);

		final ContactView friend = new ContactView();
		friend.ContactName = FRIEND_NAME;
		friend.ContactImageId = R.drawable.lmi_winger;
		$.put(FRIEND_ID, friend);

		final ContactView abed = new ContactView();
		abed.ContactName = ABED_NAME;
		abed.ContactImageId = R.drawable.lmi_abed;
		$.put(ABED_ID, abed);

		return $;
	}

	public MockInvitationAdapter(final Context context) {
		super(context);
	}

	@Override
	protected List<Invitation> getFullDataset() {
		return mockInvitations;
	}

	@Override
	protected ContactView getContactViewById(final Long contactId) {
		return mockContactViewsById.get(contactId);
	}
}
