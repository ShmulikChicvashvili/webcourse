package com.technion.coolie.letmein;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyCalendar {

	private Calendar calendar;

	public MyCalendar() {
		calendar = Calendar.getInstance();
	}

	public void restore(int arr[]) {
		calendar = new GregorianCalendar(arr[0], arr[1], arr[2], arr[3], arr[4]);
	}

	public int[] backup() {
		return new int[] { getYear(), getMonth(), getDay(), getHour(), getMinute() };
	}

	public void restoreFromTime(Date date) {
		calendar.setTime(date);
	}

	public void setDate(int year, int month, int day) {
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
	}

	public void setTime(int hour, int minute) {
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
	}

	public Date getTime() {
		return calendar.getTime();
	}

	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}

	public int getMonth() {
		return calendar.get(Calendar.MONTH);
	}

	public int getDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getHour() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public int getMinute() {
		return calendar.get(Calendar.MINUTE);
	}

	public String parseDate() {
		return getDay() + "/" + (getMonth() + 1) + "/" + getYear();
	}

	public String parseTime() {
		final String fixedHour = (getHour() < 10 ? "0" : "") + getHour();
		final String fixedMinute = (getMinute() < 10 ? "0" : "") + getMinute();
		return fixedHour + ":" + fixedMinute;
	}
}
