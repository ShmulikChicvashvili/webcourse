package com.technion.coolie.studybuddy.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.j256.ormlite.field.DatabaseField;

public class Stat
{
	@DatabaseField(id = true)
	private Date			date;
	private static Calendar	cal;
	@DatabaseField
	private int				amountDone;

	public Stat()
	{

	}

	public Stat(Date d)
	{
		date = nullifyDate(d);
		amountDone = 0;
	}

	public static Date nullifyDate(Date d)
	{
		if (null == cal)
		{
			cal = new GregorianCalendar();
		}
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	public Date getDate()
	{
		return date;
	}

	public void addDone()
	{
		amountDone++;
	}

	public void decreaseDone()
	{
		if (amountDone == 0)
			return;
		amountDone--;
	}

	public void clearDone()
	{
		amountDone = 0;
	}

	public int getAmountDone()
	{
		return amountDone;
	}

}
