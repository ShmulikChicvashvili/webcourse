package com.technion.coolie.letmein.model.adapters;

import java.util.Arrays;
import java.util.Date;
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
		final Invitation dad = new Invitation();
		dad.setContactId("dad");
		dad.setDate(new Date());

		final Invitation friend = new Invitation();
		friend.setContactId("friend");
		friend.setDate(new Date());

		final Invitation abed = new Invitation();
		abed.setContactId("abed");
		abed.setDate(new Date());

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
	protected List<Invitation> getInvitationList() {
		return mockInvitations;
	}

	@Override
	protected ContactView getContactViewById(final String contactId) {
		return mockContactViewsById.get(contactId);
	}
}
