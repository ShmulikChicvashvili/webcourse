package com.technion.coolie.letmein;

public interface CalendarSupplier {
	public void setDate(int year, int month, int day);

	public void setTime(int hour, int minute);

	public MyCalendar getMyCalendar();
}
