package com.technion.coolie.studybuddy.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.studybuddy.data.DataStore;

@DatabaseTable
public class DailyStatistic extends Observable
{
	@DatabaseField(generatedId = true)
	private UUID			id;
	@DatabaseField
	private Date			date;
	private static Calendar	cal;
	@DatabaseField
	private int				amountDone;
	@DatabaseField
	// (foreign = true, foreignAutoRefresh = true)
	private WorkStats		parent;

	public DailyStatistic()
	{

	}

	public DailyStatistic(Date d)
	{
		date = setMidnight(d);
		amountDone = 0;
	}

	public static Date setMidnight(Date d)
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
//		// TODO: hack for speed
//		DataStore.getHelper().getStatDao().createOrUpdate(this);
//		notifyObservers(PersistMessages.UPDATE);
	}

	public void decreaseDone()
	{
		if (amountDone == 0)
			return;
		amountDone--;
		DataStore.getHelper().getStatDao().createOrUpdate(this);
//		setChanged();
//		notifyObservers(PersistMessages.UPDATE);
	}

	public int getAmountDone()
	{
		return amountDone;
	}

	public void setParent(WorkStats workStats)
	{
		parent = workStats;
	}

}
