package com.technion.coolie.ug.utils.calendar;

import android.net.Uri;

public final class CalendarFields {
	/**
	 * Enum containing events fields
	 * 
	 * @author ���� ����
	 * 
	 */
	public enum Events {
		EVENTS_INSTANCE;

		public String _ID = "";
		public String TITLE = "";
		public String DTSTART = "";
		public String DTEND = "";
		public String ALL_DAY = "";
		public String EVENT_LOCATION = "";
		public String DESCRIPTION = "";
		public String RRULE = "";
		public String RDATE = "";
		public String DURATION = "";
		public String LAST_DATE = "";
		public String DELETED = "";
		public String CALENDAR_ID = "";
		public String EVENT_TIMEZONE = "";
		public String ACCESS_LEVEL = "";
		public String ORGANIZER = "";
		public String ALARM = "";
		public int ACCESS_PUBLIC = -1;
		public Uri CONTENT_URI = null;

		/**
		 * C'tor
		 */
		private Events() {
			_ID = "_id";
			TITLE = "title";
			DTSTART = "dtstart";
			DTEND = "dtend";
			ALL_DAY = "allDay";
			EVENT_LOCATION = "eventLocation";
			DESCRIPTION = "description";
			RRULE = "rrule";
			RDATE = "rdate";
			DURATION = "duration";
			LAST_DATE = "lastDate";
			DELETED = "deleted";
			CALENDAR_ID = "calendar_id";
			EVENT_TIMEZONE = "eventTimezone";
			ORGANIZER = "organizer";
			ACCESS_PUBLIC = 3;
			CONTENT_URI = Uri.parse("content://com.android.calendar/events");
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				// init values for Calendar API 14 and up
				ACCESS_LEVEL = "accessLevel";
				ALARM = "eventEndTimezone";
			} else {
				ALARM = "commentsUri";
			}
		}

		/**
		 * Events instance
		 * 
		 * @return
		 */
		public static Events getInstance() {
			return EVENTS_INSTANCE;
		}
	}

	/**
	 * enum containing calendar fields
	 * 
	 * @author ���� ����
	 * 
	 */
	public enum Calendars {
		CALENDARS_INSTANCE;

		public String _ID = "";
		public String NAME = "";
		public String ACCOUNT_NAME = "";
		public String ACCOUNT_TYPE = "";
		public String VISIBLE = "";
		public String SYNC_EVENTS = "";
		public String CALENDAR_DISPLAY_NAME = "";
		public String CALENDAR_COLOR = "";
		public String CALENDAR_ACCESS_LEVEL = "";
		public String OWNER_ACCOUNT = "";
		public String CALENDAR_TIME_ZONE = "";
		public int CAL_ACCESS_OWNER = -1;
		public String ACCOUNT_TYPE_LOCAL = "";
		public Uri CONTENT_URI = null;

		/**
		 * C'tor
		 */
		private Calendars() {
			_ID = "_id";
			NAME = "name";
			SYNC_EVENTS = "sync_events";
			OWNER_ACCOUNT = "ownerAccount";
			CAL_ACCESS_OWNER = 700;
			CONTENT_URI = Uri.parse("content://com.android.calendar/calendars");
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				// init values for Calendar API 14 and up
				ACCOUNT_NAME = "account_name";
				ACCOUNT_TYPE = "account_type";
				VISIBLE = "visible";
				CALENDAR_DISPLAY_NAME = "calendar_displayName";
				CALENDAR_COLOR = "calendar_color";
				CALENDAR_ACCESS_LEVEL = "calendar_access_level";
				CALENDAR_TIME_ZONE = "calendar_timezone";
				ACCOUNT_TYPE_LOCAL = "LOCAL";
			} else {
				// init values for Calendar API 13 and lower
				ACCOUNT_NAME = "_sync_account";
				ACCOUNT_TYPE = "_sync_account_type";
				VISIBLE = "selected";
				CALENDAR_DISPLAY_NAME = "displayName";
				CALENDAR_COLOR = "color";
				CALENDAR_ACCESS_LEVEL = "access_level";
				CALENDAR_TIME_ZONE = "timezone";
				ACCOUNT_TYPE_LOCAL = "local";
			}
		}

		/**
		 * Calendars instance
		 * 
		 * @return
		 */
		public static Calendars getInstance() {
			return CALENDARS_INSTANCE;
		}
	}

	public static final String CALLER_IS_SYNCADAPTER = "caller_is_syncadapter";
}
