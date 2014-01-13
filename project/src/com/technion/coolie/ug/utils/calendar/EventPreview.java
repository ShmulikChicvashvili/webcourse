package com.technion.coolie.ug.utils.calendar;

import java.util.Date;

/**
 * 
 * @author ���� ����
 * 
 */
public class EventPreview {

	public final static String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss"; //

	private String title; //
	private Date startDate; //
	private long eventID; //
	private boolean allDayEvent;

	/**
	 * 
	 * @param eventID
	 * @param title
	 * @param startDate
	 */
	public EventPreview(long eventID, String title, Date startDate,
			boolean allDayEvent) {
		super();
		this.eventID = eventID;
		this.title = title;
		this.startDate = startDate;
		this.allDayEvent = allDayEvent;
	}

	/**
	 * 
	 * @return
	 */
	public long getEventID() {
		return eventID;
	}

	/**
	 * 
	 * @param eventID
	 */
	public void setEventID(long eventID) {
		this.eventID = eventID;
	}

	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * 
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
