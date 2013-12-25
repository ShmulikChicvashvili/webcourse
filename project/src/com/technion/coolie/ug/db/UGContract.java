package com.technion.coolie.ug.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class UGContract {
	public static final String DATABASE_NAME = "com.technion.coolie.ug";
	public static final int DATABASE_VERSION = 1;

	public static final String AUTHORITY = "com.technion.coolie.ug";

	// Invitations table
	public static class Course implements BaseColumns {
		public static final String TABLENAME = "courses";

		public static final String CONTENT_URI_PATH = TABLENAME;
		public static final String MIMETYPE_TYPE = TABLENAME;
		public static final String MIMETYPE_NAME = AUTHORITY + ".provider";

		// Fields
		public static final String DATE = "date";
		public static final String STATUS = "status";

		public static final String CONTACT_ID = "contactId";
		public static final String CONTACT_NAME = "contactName";
		public static final String CONTACT_PHONE_NUMBER = "contactPhoneNumber";
		public static final String CAR_NUMBER = "carNumber";
		public static final String CAR_MANUFACTURER = "carManufacturer";
		public static final String CAR_COLOR = "carColor";

		// Content URI pattern code
		public static final int CONTENT_URI_PATTERN_MANY = 1;
		public static final int CONTENT_URI_PATTERN_ONE = 2;

		// Refer to activity
		public static final Uri contentUri = new Uri.Builder()
				.scheme(ContentResolver.SCHEME_CONTENT).authority(AUTHORITY)
				.appendPath(CONTENT_URI_PATH).build();
	}

}
