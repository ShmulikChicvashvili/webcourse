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
	private static final Map<String, ContactView> mockContactViewsById = initContactViews();

	private static List<Invitation> initInvitations() {
		final Calendar c = Calendar.getInstance();

		c.add(Calendar.HOUR_OF_DAY, 9);
		final Invitation dad = Invitation.builder().contactId("dad").date(c.getTime()).build();

		c.add(Calendar.DAY_OF_YEAR, 2);
		final Invitation friend = Invitation.builder().contactId("friend").date(c.getTime())
				.build();

		c.add(Calendar.WEEK_OF_YEAR, 1);
		final Invitation abed = Invitation.builder().contactId("abed").date(c.getTime()).build();

		return Arrays.asList(dad, friend, abed);
	}

	private static Map<String, ContactView> initContactViews() {
		final Map<String, ContactView> $ = new HashMap<String, ContactView>();

		final ContactView dad = new ContactView();
		dad.ContactName = "Dad";
		dad.ContactImageId = R.drawable.lmi_dad;
		$.put("dad", dad);

		final ContactView friend = new ContactView();
		friend.ContactName = "My best friend";
		friend.ContactImageId = R.drawable.lmi_winger;
		$.put("friend", friend);

		final ContactView abed = new ContactView();
		abed.ContactName = "Abed";
		abed.ContactImageId = R.drawable.lmi_abed;
		$.put("abed", abed);

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
	protected ContactView getContactViewById(final String contactId) {
		return mockContactViewsById.get(contactId);
	}
}
