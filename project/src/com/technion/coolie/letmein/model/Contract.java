package com.technion.coolie.letmein.model;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import com.technion.coolie.letmein.Consts;

public final class Contract {
	public static final String DATABASE_NAME = Consts.DATABASE_NAME;
	public static final int DATABASE_VERSION = 1;

	public static final String AUTHORITY = "com.technion.coolie.letmein";

	// Invitations table
	public static class Invitation implements BaseColumns {
		public static final String TABLENAME = "invitations";

		public static final String CONTENT_URI_PATH = TABLENAME;
		public static final String MIMETYPE_TYPE = TABLENAME;
		public static final String MIMETYPE_NAME = AUTHORITY + ".provider";

		// Fields
		public static final String CONTACT_ID = "contactId";
		public static final String DATE = "date";
		public static final String STATUS = "status";

		// Content URI pattern code
		public static final int CONTENT_URI_PATTERN_MANY = 1;
		public static final int CONTENT_URI_PATTERN_ONE = 2;

		// Refer to activity
		public static final Uri contentUri = new Uri.Builder()
				.scheme(ContentResolver.SCHEME_CONTENT).authority(AUTHORITY)
				.appendPath(CONTENT_URI_PATH).build();
	}

}
