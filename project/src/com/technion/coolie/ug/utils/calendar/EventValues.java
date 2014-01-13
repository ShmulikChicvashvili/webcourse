package com.technion.coolie.ug.utils.calendar;

/**
 * 
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Calendar;
import java.util.Date;

/**
 * This class represents an event for the calendar
 * 
 * @author ���� ����
 * 
 */
public class EventValues {

	/**
	 * This enumeration represents the possible values for a recurring event
	 * 
	 * @author ���� ����
	 * 
	 */
	public enum EVENT_FREQ {

		FREQ_NONE {
			public int getTimeUnit() {
				return -1;
			}
		},
		FREQ_DAILY {
			public int getTimeUnit() {
				return Calendar.DATE;
			}
		},
		FREQ_WEEKLY {
			public int getTimeUnit() {
				return Calendar.WEEK_OF_MONTH;
			}
		},
		FREQ_MONTHLY {
			public int getTimeUnit() {
				return Calendar.MONTH;
			}
		},
		FREQ_YEARLY;
		public int getTimeUnit() {
			return Calendar.YEAR;
		}

	}

	public enum EVENT_REMINDERS {
		REMINDER_NONE {
			public String toString() {
				return "none";
			}
		},
		REMINDER_MINUTES {
			public String toString() {
				return "Minutes";
			}
		},
		REMINDER_HOURS {
			public String toString() {
				return "Hours";
			}
		},
		REMINDER_DAYS {
			public String toString() {
				return "Days";
			}
		},
		REMINDER_WEEKS {
			public String toString() {
				return "Weeks";
			}
		}
	}

	private long eventId; // Event ID
	private String eventTitle; // Title of the event
	private Date eventStartDate; // event start date and time
	private Date eventEndDate; // event end date and time
	private boolean allDayEvent; // flag indicating a full day event
	private String eventLocation; // location of the event
	private String eventDescription; // description of the event
	private EVENT_FREQ eventFrequency; // for recurring events
	private Date eventEndDateRecurring; // end date for a recurring event
	private int eventRecurringCount; // number of recurring events
	private String eventDuration; // time duration of the event (in minutes)
	private SimpleEntry<EVENT_REMINDERS, Integer> reminder;

	/**
	 * C'tor, init the class with default values
	 */
	public EventValues() {
		eventId = -1;
		eventTitle = "";
		eventStartDate = null;
		eventEndDate = null;
		allDayEvent = false;
		eventLocation = "";
		eventDescription = "";
		eventFrequency = EVENT_FREQ.FREQ_NONE;
		eventEndDateRecurring = null;
		eventRecurringCount = -1;
		eventDuration = "";
		reminder = new SimpleEntry<EventValues.EVENT_REMINDERS, Integer>(
				EVENT_REMINDERS.REMINDER_NONE, -1);
		// alertTime = null;
	}

	/**
	 * 
	 * @return
	 */
	public SimpleEntry<EVENT_REMINDERS, Integer> getReminder() {
		return reminder;
	}

	/**
	 * 
	 * @param reminder
	 */
	public void setReminder(SimpleEntry<EVENT_REMINDERS, Integer> reminder) {
		this.reminder = reminder;
	}

	/**
	 * 
	 * @return
	 */
	public int getEventRecurringCount() {
		return eventRecurringCount;
	}

	/**
	 * 
	 * @param eventRecurringCount
	 */
	public void setEventRecurringCount(int eventRecurringCount) {
		this.eventRecurringCount = eventRecurringCount;
	}

	/**
	 * 
	 * @return
	 */

	/**
	 * 
	 * @param alarmHandler
	 */

	/**
	 * 
	 * @return
	 */
	public long getEventId() {
		return eventId;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isRecurringEvent() {
		return eventFrequency != EVENT_FREQ.FREQ_NONE;
	}

	/**
	 * 
	 * @param eventId
	 */
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	/**
	 * Calculate alarm date
	 * 
	 * @param reminder
	 * @param startDate
	 * @return
	 */
	public Date getReminderByDate() {
		Date ret = new Date();
		int count = reminder.getValue();
		Calendar cal = Calendar.getInstance();
		// set time to event start date
		SimpleDateFormat formatDateOnly = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm");
		Date startDateOnly = eventStartDate;
		try {
			startDateOnly = formatDateOnly.parse(formatDateOnly
					.format(eventStartDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(startDateOnly);

		switch (reminder.getKey()) {
		case REMINDER_MINUTES:
			ret.setTime(eventStartDate.getTime() - count * 60 * 1000);
			break;
		case REMINDER_HOURS:
			ret.setTime(eventStartDate.getTime() - count * 60 * 60 * 1000);
			break;
		case REMINDER_DAYS:
			// decrease the time by day till
			while (count > 0) {
				cal.add(Calendar.DATE, -1);
				count--;
			}
			ret.setTime(cal.getTimeInMillis());
			break;
		case REMINDER_WEEKS:
			// decease the time by week
			while (count > 0) {
				cal.add(Calendar.WEEK_OF_MONTH, -1);
				count--;
			}
			ret.setTime(cal.getTimeInMillis());
			break;
		}

		return ret;
	}

	/**
	 * This method calculated the amount of repetitions for a recurring event
	 * 
	 * @return - amount of repetitions for the recurring event
	 */
	public int getRecurringEventCountByEndDate() {
		int count = 0;

		switch (eventFrequency) {
		case FREQ_DAILY:
			count = getDailyRecurringEventCount();
			break;
		case FREQ_WEEKLY:
			count = getWeeklyRecurringEventCount();
			break;
		case FREQ_MONTHLY:
			count = getMonthlyRecurringEventCount();
			break;
		case FREQ_YEARLY:
			count = getYearlyRecurringEventCount();
			break;
		}

		return count;
	}

	/**
	 * 
	 * @return
	 */
	private int getDailyRecurringEventCount() {
		int count = 0;
		// get current calendar
		Calendar cal = Calendar.getInstance();
		// set time to event start date
		SimpleDateFormat formatDateOnly = new SimpleDateFormat("dd/MM/yyyy");
		Date startDateOnly = eventStartDate;
		try {
			startDateOnly = formatDateOnly.parse(formatDateOnly
					.format(eventStartDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(startDateOnly);
		// advance the time by day till we reach the endRecurringDate
		while (eventEndDateRecurring.compareTo(cal.getTime()) >= 0) {
			cal.add(Calendar.DATE, 1);
			count++;
		}

		return count;
	}

	/**
	 * 
	 * @return
	 */
	private int getWeeklyRecurringEventCount() {
		int count = 0;
		// get current calendar
		Calendar cal = Calendar.getInstance();
		// set time to event start date
		SimpleDateFormat formatDateOnly = new SimpleDateFormat("dd/MM/yyyy");
		Date startDateOnly = eventStartDate;
		try {
			startDateOnly = formatDateOnly.parse(formatDateOnly
					.format(eventStartDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(startDateOnly);
		// advance the time by week till we reach the endRecurringDate
		while (eventEndDateRecurring.compareTo(cal.getTime()) >= 0) {
			cal.add(Calendar.WEEK_OF_MONTH, 1);
			count++;
		}

		return count;
	}

	/**
	 * 
	 * @return
	 */
	private int getMonthlyRecurringEventCount() {
		int count = 0;
		// get current calendar
		Calendar cal = Calendar.getInstance();
		// set time to event start date
		SimpleDateFormat formatDateOnly = new SimpleDateFormat("dd/MM/yyyy");
		Date startDateOnly = eventStartDate;
		try {
			startDateOnly = formatDateOnly.parse(formatDateOnly
					.format(eventStartDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(startDateOnly);
		// advance the time by month till we reach the endRecurringDate
		while (eventEndDateRecurring.compareTo(cal.getTime()) >= 0) {
			cal.add(Calendar.MONTH, 1);
			count++;
		}

		return count;
	}

	/**
	 * 
	 * @return
	 */
	private int getYearlyRecurringEventCount() {
		int count = 0;

		// get current calendar
		Calendar cal = Calendar.getInstance();
		// set time to event start date
		SimpleDateFormat formatDateOnly = new SimpleDateFormat("dd/MM/yyyy");
		Date startDateOnly = eventStartDate;
		try {
			startDateOnly = formatDateOnly.parse(formatDateOnly
					.format(eventStartDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(startDateOnly);
		// advance the time by year till we reach the endRecurringDate
		while (eventEndDateRecurring.compareTo(cal.getTime()) >= 0) {
			cal.add(Calendar.YEAR, 1);
			count++;
		}

		return count;
	}

	/**
	 * getEventFrequency
	 * 
	 * @return - the current event frequency
	 */
	public EVENT_FREQ getEventFrequency() {
		return eventFrequency;
	}

	/**
	 * 
	 * @return
	 */
	public String getEventFrequencyAsString() {
		String freq = "";
		switch (eventFrequency) {
		case FREQ_NONE:
			freq = "NONE";
			break;
		case FREQ_DAILY:
			freq = "DAILY";
			break;
		case FREQ_WEEKLY:
			freq = "WEEKLY";
			break;
		case FREQ_MONTHLY:
			freq = "MONTHLY";
			break;
		case FREQ_YEARLY:
			freq = "YEARLY";
			break;
		}

		return freq;
	}

	/**
	 * 
	 * @param eventFrequency
	 */
	public void setEventFrequency(EVENT_FREQ eventFrequency) {
		this.eventFrequency = eventFrequency;
	}

	/**
	 * 
	 * @return
	 */
	public String getEventDuration() {
		return eventDuration;
	}

	/**
	 * 
	 * @param eventDuration
	 */
	public void setEventDuration(String eventDuration) {
		this.eventDuration = eventDuration;
	}

	/**
	 * 
	 * @return
	 */

	// /**
	// *
	// * @return
	// */
	// public Date getAlertTime() {
	// return alertTime;
	// }
	//
	// /**
	// *
	// * @param alertTime
	// */
	// public void setAlertTime(Date alertTime) {
	// this.alertTime = new Date(alertTime.getTime());
	// }

	/**
	 * 
	 * @return
	 */
	public String getEventTitle() {
		return eventTitle;
	}

	/**
	 * 
	 * @param eventTitle
	 */
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	/**
	 * 
	 * @return
	 */
	public Date getEventStartDate() {
		return eventStartDate;
	}

	/**
	 * 
	 * @param eventStartDate
	 */
	public void setEventStartDate(Date eventStartDate) {
		this.eventStartDate = eventStartDate;// new
												// Date(eventStartDate.getTime());
	}

	/**
	 * 
	 * @return
	 */
	public Date getEventEndDate() {
		return eventEndDate;
	}

	/**
	 * 
	 * @param eventEndDate
	 */
	public void setEventEndDate(Date eventEndDate) {
		this.eventEndDate = eventEndDate;// new Date(eventEndDate.getTime());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAllDayEvent() {
		return allDayEvent;
	}

	/**
	 * 
	 * @param allDayEvent
	 */
	public void setAllDayEvent(boolean allDayEvent) {
		this.allDayEvent = allDayEvent;
	}

	/**
	 * 
	 * @return
	 */
	public String getEventLocation() {
		return eventLocation;
	}

	/**
	 * 
	 * @param eventLocation
	 */
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	/**
	 * 
	 * @return
	 */
	public String getEventDescription() {
		return eventDescription;
	}

	/**
	 * 
	 * @param eventDescription
	 */
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	/**
	 * 
	 * @return
	 */
	public Date getEventEndDateRecurring() {
		return eventEndDateRecurring;
	}

	/**
	 * 
	 * @param eventEndDateRecurring
	 */
	public void setEventEndDateRecurring(Date eventEndDateRecurring) {
		this.eventEndDateRecurring = eventEndDateRecurring;// new
															// Date(eventEndDateRecurring.getTime());
	}

	/**
	 * 
	 * @param rule
	 */
	public void setEventFrequencyByString(String rule) {
		eventFrequency = EVENT_FREQ.FREQ_NONE;
		if (rule == null) {
			return;
		}

		if (rule.contains("DAILY")) {
			eventFrequency = EVENT_FREQ.FREQ_DAILY;
		} else if (rule.contains("WEEKLY")) {
			eventFrequency = EVENT_FREQ.FREQ_WEEKLY;
		} else if (rule.contains("MONTHLY")) {
			eventFrequency = EVENT_FREQ.FREQ_MONTHLY;
		} else if (rule.contains("YEARLY")) {
			eventFrequency = EVENT_FREQ.FREQ_YEARLY;
		}
	}

	@Override
	public String toString() {
		return "EventValues [eventId=" + eventId + ", eventTitle=" + eventTitle
				+ ", eventStartDate=" + eventStartDate + ", eventEndDate="
				+ eventEndDate + ", allDayEvent=" + allDayEvent
				+ ", eventLocation=" + eventLocation + ", eventDescription="
				+ eventDescription + ", eventFrequency=" + eventFrequency
				+ ", eventEndDateRecurring=" + eventEndDateRecurring
				+ ", eventRecurringCount=" + eventRecurringCount
				+ ", eventDuration=" + eventDuration;
	}

	public Date calcRecurringEndDateByCount() {
		if (!isRecurringEvent()) {
			return null;
		}
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(this.getEventStartDate());

		// count of 1 gives the start date.. and so on.
		for (int i = 0; i < (this.getEventRecurringCount() - 1); i++)
			endDate.add(eventFrequency.getTimeUnit(), 1);

		return endDate.getTime();
	}
}
