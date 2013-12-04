package com.technion.coolie.letmein;

import java.util.Calendar;

public interface CalendarSupplier {
	public void setDate(Calendar calendar);

	public void setTime(Calendar calendar);

	public int getYear();

	public int getMonth();

	public int getDay();

	public int getHour();

	public int getMinute();
}
