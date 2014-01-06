package com.technion.coolie.ug.utils.calendar;

import java.util.Date;
import java.util.TimeZone;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.technion.coolie.ug.utils.calendar.EventValues.EVENT_FREQ;

public class CalendarProvider {

	// context that uses the calendar
	private Context context;
	// private static final String CALENDAR_NAME = "VoiceeCalendar";
	private Long calendarID;
	private CalendarFields.Calendars Calendars;
	private CalendarFields.Events Events;
	private String[] EVENT_PROJECTION;

	/**
	 * C'tor. sets calendarID.
	 * 
	 * @param currentContext
	 *            - context that uses the class
	 * @throws Exception
	 */
	public CalendarProvider(Context currentContext) {
		this.Calendars = CalendarFields.Calendars.getInstance();
		this.Events = CalendarFields.Events.getInstance();
		this.EVENT_PROJECTION = new String[] { Events._ID, // event id
				Events.TITLE, // event title
				Events.DTSTART, // event start date and time
				Events.DTEND, // event end date and time
				Events.ALL_DAY, // is the event an all day event
				Events.EVENT_LOCATION, // event location
				Events.DESCRIPTION, // event description
				Events.RRULE, // event recurring rule
				Events.RDATE, // event recurring date
				Events.DURATION, // event duration
				Events.LAST_DATE, // event end recurring date
				Events.DELETED, // event was deleted
				Events.CALENDAR_ID, // calendar id of the event
				Events.ORGANIZER, // event organizer
				Events.ALARM // event alarm
		};
		this.context = currentContext;
		// try {
		// assertCalendarIsValid();
		// } catch (Exception e) {
		// e.printStackTrace();
		// assert (false);
		// }
		// MyAlarmManager.onStart(context);
	}

	/**
	 * 
	 * @throws Exception
	 */
	// private void assertCalendarIsValid() throws Exception {
	//
	// calendarID = findCalendarID(CALENDAR_NAME);
	// if (calendarID == -1) {
	// MyApp.logError("calendar  ___NOT AVAILABLE___!!! ");
	// calendarID = createCalendar(CALENDAR_NAME);
	// }
	// if (calendarID == -1)
	// throw new Exception("cant create calendar!!");
	//
	// MyApp.log("calendar is found and his id is " + calendarID);
	// }

	// /**
	// *
	// * @param name
	// * @return
	// */
	// private long findCalendarID(String name) {
	// String[] projection = new String[] { Calendars._ID, Calendars.NAME };
	//
	// Cursor calCursor = context.getContentResolver().query(
	// Calendars.CONTENT_URI, projection, null, null, null);
	//
	// if (calCursor.moveToFirst()) {
	// do {
	// MyApp.log(calCursor.getString(calCursor
	// .getColumnIndex(Calendars.NAME)));
	// if (calCursor.getString(
	// calCursor.getColumnIndex(Calendars.NAME)).equals(name))
	// return calCursor.getLong(calCursor
	// .getColumnIndex(Calendars._ID));
	// } while (calCursor.moveToNext());
	// }
	//
	// MyApp.logError("***couldn't find Calendar at findCalendar!!!!!!!**");
	//
	// return -1;
	// }

	/**
	 * 
	 * @param name
	 * @return //
	 */
	// private long createCalendar(String name) {
	// // values is a set for storing data that a contentProvidor can process.
	// ContentValues values = new ContentValues();
	//
	// final String MY_ACCOUNT_NAME = "voiceeApp";
	// TimeZone tz = TimeZone.getDefault();
	// final String timeZone = tz.getID();
	//
	// values.put(Calendars.ACCOUNT_NAME, MY_ACCOUNT_NAME);
	// values.put(Calendars.ACCOUNT_TYPE, Calendars.ACCOUNT_TYPE_LOCAL);
	// values.put(Calendars.NAME, name);
	// values.put(Calendars.VISIBLE, 1);
	// values.put(Calendars.SYNC_EVENTS, 1);
	// values.put(Calendars.CALENDAR_DISPLAY_NAME, name);
	// values.put(Calendars.CALENDAR_COLOR, 0xffff0000);
	// values.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);
	// values.put(Calendars.OWNER_ACCOUNT, "voiceeCalendar");
	// values.put(Calendars.CALENDAR_TIME_ZONE, timeZone);
	// Uri.Builder builder = Calendars.CONTENT_URI.buildUpon();
	// builder.appendQueryParameter(Calendars.ACCOUNT_NAME, "VoiceeCalendar");
	// builder.appendQueryParameter(Calendars.ACCOUNT_TYPE,
	// Calendars.ACCOUNT_TYPE_LOCAL); // CANNOT by synced. just
	// // local.
	// builder.appendQueryParameter(CalendarFields.CALLER_IS_SYNCADAPTER,
	// "true");
	// Uri uri = context.getContentResolver().insert(builder.build(), values);
	// long newID = Long.parseLong(uri.getLastPathSegment());
	//
	// MyApp.log("The new Calendar id is" + newID);
	// return newID;
	//
	// }

	public long addEvent(EventValues event) {

		long eventId = -1;
		// set correct values in case of a one time all day event
		if (event.isAllDayEvent()) {
			event.setEventDuration("P1D");
			event.setEventEndDate(null);
		} else {
			event.setEventDuration("");
		}
		if (event.isAllDayEvent()
				&& event.getEventFrequency() == EVENT_FREQ.FREQ_NONE) {
			// workaround for all day events
			// set them as daily event with one recurring count
			event.setEventFrequency(EVENT_FREQ.FREQ_DAILY);
			event.setEventRecurringCount(1);
		}
		// add the event
		if (!event.isRecurringEvent()) {
			// handle a one time event
			eventId = addOneTimeEvent(event);
		} else {
			// handle a recurring event
			eventId = addRecurringEvent(event);
		}
		return eventId;
	}

	/**
	 * internal, add a one time event
	 * 
	 * @param event
	 *            - event to add
	 * @return - new event ID
	 */
	private long addOneTimeEvent(EventValues event) {
		ContentResolver cr = context.getContentResolver();

		// insert event values
		ContentValues values = new ContentValues();
		values.put(Events.TITLE, event.getEventTitle());

		if (event.getEventStartDate() != null) {
			int timeZoneOffset = 0;
			if (event.isAllDayEvent()) {
				// when we have an all day event we need to enter the start time
				// with UTC time zone
				// so we are calculating the offset that is needed to be added
				// to the start date and time
				timeZoneOffset = TimeZone.getTimeZone(
						TimeZone.getDefault().getDisplayName()).getOffset(
						event.getEventStartDate().getTime());
			}

			values.put(Events.DTSTART, event.getEventStartDate().getTime()
					+ timeZoneOffset);
		}
		if (event.getEventEndDate() != null) {
			int timeZoneOffset = 0;
			if (event.isAllDayEvent()) {
				// when we have an all day event we need to enter the end time
				// with UTC time zone
				// so we are calculating the offset that is needed to be added
				// to the end date and time
				// timeZoneOffset = TimeZone.getTimeZone(
				// TimeZone.getDefault().getDisplayName()).getOffset(
				// event.getEventStartDate().getTime());
			}

			values.put(Events.DTEND, event.getEventEndDate().getTime()
					+ timeZoneOffset);
			// event.setEventDuration("");
		}
		values.put(Events.DESCRIPTION, event.getEventDescription());
		values.put(Events.EVENT_LOCATION, event.getEventLocation());
		values.put(Events.ALL_DAY, event.isAllDayEvent() ? 1 : 0);
		values.put(Events.DURATION, event.getEventDuration());
		values.put(Events.CALENDAR_ID, 1);
		values.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
		if (!Events.ACCESS_LEVEL.equals("")) {
			values.put(Events.ACCESS_LEVEL, Events.ACCESS_PUBLIC);
		}

		// insert event to calendar
		Uri newEvent = cr.insert(Events.CONTENT_URI, values);
		//
		long eventID = Long.parseLong(newEvent.getLastPathSegment());
		// EventValues addEvent = findEventById(eventID);
		// if (event.getEventFrequency() != EVENT_FREQ.FREQ_NONE
		// && event.getEventEndDateRecurring() == null
		// && event.getEventRecurringCount() > 0) {
		// event.setEventEndDateRecurring(addEvent.getEventEndDateRecurring());
		// }

		// return new event Id
		return eventID;
	}

	/**
	 * internal, add a recurring event
	 * 
	 * @param event
	 *            - event to add
	 * @return - new event ID
	 */
	private long addRecurringEvent(EventValues event) {
		ContentResolver cr = context.getContentResolver();

		// insert event values
		ContentValues values = new ContentValues();
		values.put(Events.TITLE, event.getEventTitle());
		if (event.getEventStartDate() != null) {
			int timeZoneOffset = 0;
			if (event.isAllDayEvent()) {
				// when we have an all day event we need to enter the start time
				// with UTC time zone
				// so we are calculating the offset that is needed to be added
				// to the start date and time
				timeZoneOffset = TimeZone.getTimeZone(
						TimeZone.getDefault().getDisplayName()).getOffset(
						event.getEventStartDate().getTime());
			}

			values.put(Events.DTSTART, event.getEventStartDate().getTime()
					+ timeZoneOffset);
		}
		if (event.getEventEndDate() != null) {
			int timeZoneOffset = 0;
			if (event.isAllDayEvent()) {
				// when we have an all day event we need to enter the end time
				// with UTC time zone
				// so we are calculating the offset that is needed to be added
				// to the end date and time
				// timeZoneOffset = TimeZone.getTimeZone(
				// TimeZone.getDefault().getDisplayName()).getOffset(
				// event.getEventStartDate().getTime());
			}

			values.put(Events.DTEND, event.getEventEndDate().getTime()
					+ timeZoneOffset);
			// event.setEventDuration("");
		}
		values.put(Events.DESCRIPTION, event.getEventDescription());
		values.put(Events.EVENT_LOCATION, event.getEventLocation());
		values.put(Events.ALL_DAY, event.isAllDayEvent() ? 1 : 0);
		values.put(Events.DURATION, event.getEventDuration());

		if (event.getEventEndDateRecurring() != null) {
			// event recurring has an end date
			int count = event.getRecurringEventCountByEndDate();
			values.put(Events.RRULE,
					"FREQ=" + event.getEventFrequencyAsString() + ";COUNT="
							+ count);
		} else if (event.getEventRecurringCount() > 0) {
			// event has number of recurring events
			int count = event.getEventRecurringCount();
			values.put(Events.RRULE,
					"FREQ=" + event.getEventFrequencyAsString() + ";COUNT="
							+ count);
		} else {
			// event recurring doesn't have an end date
			values.put(Events.RRULE,
					"FREQ=" + event.getEventFrequencyAsString());
		}
		values.put(Events.CALENDAR_ID, calendarID);
		values.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
		if (!Events.ACCESS_LEVEL.equals("")) {
			values.put(Events.ACCESS_LEVEL, Events.ACCESS_PUBLIC);
		}

		// insert event to calendar
		Uri newEvent = cr.insert(Events.CONTENT_URI, values);

		long eventID = Long.parseLong(newEvent.getLastPathSegment());

		EventValues addEvent = findEventById(eventID);
		if (event.getEventFrequency() != EVENT_FREQ.FREQ_NONE
				&& event.getEventEndDateRecurring() == null
				&& event.getEventRecurringCount() > 0) {
			event.setEventEndDateRecurring(addEvent.getEventEndDateRecurring());
		}

		// return new event Id
		return eventID;
	}

	public boolean deleteEvent(Long eventId) {
		ContentResolver cr = context.getContentResolver();

		Uri deleteUri = null;
		deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, eventId);
		int rows = cr.delete(deleteUri, null, null);
		boolean ret = false;
		if (rows > 0) {
			ret = true;
		} else {
			ret = false;
		}

		return ret;
	}

	public long editEvent(EventValues event) {
		long newEventId = -1;
		// delete the event (if exists)
		if (deleteEvent(event.getEventId())) {
			// add the updated event
			newEventId = addEvent(event);
		}

		return newEventId;
	}

	public EventValues findEventById(Long eventId) {
		Cursor cursor = null;

		ContentResolver cr = context.getContentResolver();
		cursor = cr.query(Events.CONTENT_URI, EVENT_PROJECTION, Events._ID
				+ " = ? AND " + Events.CALENDAR_ID + " = ?", new String[] {
				eventId.toString(), calendarID.toString() }, null);

		if (isCursorEmpty(cursor)) {
			return null;
		}

		return getEventRow(cursor);
	}

	/**
	 * 
	 * @param cursor
	 * @return
	 */
	private boolean isCursorEmpty(Cursor cursor) {
		boolean isNull = (cursor == null);
		boolean isEmpty = (!cursor.moveToFirst());

		return (isNull || isEmpty);
	}

	/**
	 * 
	 * @param cursor
	 * @return
	 */
	private EventValues getEventRow(Cursor cursor) {
		EventValues event = new EventValues();
		cursor.moveToFirst();
		if (cursor.getInt(cursor.getColumnIndex(Events.DELETED)) == 1) {
			// event was deleted
			return null;
		} else {
			// event was not deleted
			// fill in the event values
			event.setEventId(cursor.getLong(cursor.getColumnIndex(Events._ID))); // event
																					// id
			event.setEventTitle(cursor.getString(cursor
					.getColumnIndex(Events.TITLE))); // event
														// title
			event.setEventStartDate(new Date(cursor.getLong(cursor
					.getColumnIndex(Events.DTSTART)))); // event
														// start
														// date
														// and
														// time
			event.setEventEndDate(new Date(cursor.getLong(cursor
					.getColumnIndex(Events.DTEND)))); // event
														// end
														// date
														// and
														// time
			event.setAllDayEvent((cursor.getInt(cursor
					.getColumnIndex(Events.ALL_DAY)) == 1) ? true : false); // all
																			// day
																			// event
			event.setEventLocation(cursor.getString(cursor
					.getColumnIndex(Events.EVENT_LOCATION))); // event
																// location
			event.setEventDescription(cursor.getString(cursor
					.getColumnIndex(Events.DESCRIPTION))); // event
															// description
			event.setEventFrequencyByString(cursor.getString(cursor
					.getColumnIndex(Events.RRULE))); // event
														// frequency
			event.setEventDuration(cursor.getString(cursor
					.getColumnIndex(Events.DURATION))); // event
														// duration
			event.setEventEndDateRecurring(new Date(cursor.getLong(cursor
					.getColumnIndex(Events.LAST_DATE)))); // event
															// end
															// date
															// recurring
		}

		return event;
	}

	// public List<EventPreview> findEventsByDate(Date date) {
	// Date selectedDate = date;
	// Date nextDate = new Date(selectedDate.getTime() + (1000 * 60 * 60 * 24));
	// String selection = "((" + Events.DTSTART + " <= ? AND ? <= "
	// + Events.DTEND + ") OR " + "(? <= " + Events.DTSTART + " AND "
	// + Events.DTSTART + " < ?)) AND (" + Events.CALENDAR_ID
	// + " = ?)";
	// String sDate = Long.valueOf(selectedDate.getTime()).toString();
	// String nDate = Long.valueOf(nextDate.getTime()).toString();
	// Cursor cursor = null;
	// ContentResolver cr = context.getContentResolver();
	// cursor = cr.query(
	// Events.CONTENT_URI,
	// EVENT_PROJECTION,
	// selection,
	// new String[] { sDate, sDate, sDate, nDate,
	// calendarID.toString() }, null);
	//
	// return cursorToList(cursor);
	// }

}
