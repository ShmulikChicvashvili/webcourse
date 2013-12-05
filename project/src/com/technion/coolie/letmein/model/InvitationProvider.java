package com.technion.coolie.letmein.model;

import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MimeTypeVnd.SubType;

public class InvitationProvider extends OrmLiteSimpleContentProvider<InvitationDatabaseHelper> {

	@Override
	protected Class<InvitationDatabaseHelper> getHelperClass() {
		return InvitationDatabaseHelper.class;
	}

	@Override
	public boolean onCreate() {
		setMatcherController(new MatcherController().add(Invitation.class)
				.add(SubType.DIRECTORY, "", Contract.Invitation.CONTENT_URI_PATTERN_MANY)
				.add(SubType.ITEM, "#", Contract.Invitation.CONTENT_URI_PATTERN_ONE));

		return true;
	}

}
