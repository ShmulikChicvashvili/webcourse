package com.technion.coolie.letmein.model.adapters;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.net.Uri;

import com.technion.coolie.letmein.model.Invitation;

public class MockInvitationAdapter extends BaseInvitationAdapter {
	private final List<Invitation> mockInvitations;
	private final Map<Long, ContactView> mockContactViewsById;
	private final Context context;

	private static final long DAD_ID = 0L;
	private static final long FRIEND_ID = 1L;
	private static final long ABED_ID = 2L;
	
	private static final String DAD_NAME = "Dad";
	private static final String FRIEND_NAME = "My best friend";
	private static final String ABED_NAME = "Abed";
	
	private Uri getDrawableUri(String drawable) {
		return Uri.parse("android.resource://" + context.getPackageName() + "/drawable/" + drawable);
	}

	private List<Invitation> initInvitations() {
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

	private Map<Long, ContactView> initContactViews() {
		final Map<Long, ContactView> $ = new HashMap<Long, ContactView>();

		final ContactView dad = new ContactView();
		dad.ContactName = DAD_NAME;
		dad.ContactImageUri = getDrawableUri("lmi_dad");
		$.put(DAD_ID, dad);

		final ContactView friend = new ContactView();
		friend.ContactName = FRIEND_NAME;
		friend.ContactImageUri = getDrawableUri("lmi_winger");
		$.put(FRIEND_ID, friend);

		final ContactView abed = new ContactView();
		abed.ContactName = ABED_NAME;
		abed.ContactImageUri = getDrawableUri("lmi_abed");
		$.put(ABED_ID, abed);

		return $;
	}

	public MockInvitationAdapter(final Context context) {
		super(context);
		
		this.context = context;
		
		mockInvitations = initInvitations();
		mockContactViewsById = initContactViews();
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
