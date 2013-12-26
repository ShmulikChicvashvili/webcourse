package com.technion.coolie.ug.db;

import android.provider.BaseColumns;

/**
 * defines constants for all tables in the database.
 * 
 */
public final class UGDBTables {
	public static final String DATABASE_NAME = "com.technion.coolie.ug";
	public static final int DATABASE_VERSION = 1;

	public static final String AUTHORITY = "com.technion.coolie.ug";

	// all tables
	public static class CourseTable implements BaseColumns {
		public static final String TABLENAME = "courses";
		// public static final String KEY = "key";
		// public static final String course = "course";
	}

	public static class TrackingTable implements BaseColumns {
		public static final String TABLENAME = "trackingCourses";
		// public static final String KEY = "key";
	}

	public static class AccomplishedTable implements BaseColumns {
		public static final String TABLENAME = "accomplishedCourses";
		// public static final String KEY = "key";
	}

	public static class AcademicEvents implements BaseColumns {
		public static final String TABLENAME = "AcademicEvents";
		// public static final String KEY = "key";
	}

}
