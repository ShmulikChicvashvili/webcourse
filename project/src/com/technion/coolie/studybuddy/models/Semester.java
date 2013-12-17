package com.technion.coolie.studybuddy.models;

import java.util.Date;
import java.util.UUID;

import android.content.Context;
import android.text.format.DateUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.studybuddy.data.DataStore;
import com.technion.coolie.studybuddy.views.StbSettingsActivity;

@DatabaseTable(tableName = "semesters")
public class Semester
{

	public final static int	WEEKS_IN_SEMESTER	= 14;

	// private final static long MILISECONDS_IN_WEEK = 1000 * 60 * 60 * 24 * 7;

	public static int countWeeksBetween(Date start, Date end)
	{
		int result = (int) ((end.getTime() - start.getTime()) / DateUtils.WEEK_IN_MILLIS);
		return result;
	}

	@DatabaseField(generatedId = true)
	private UUID	id;
	@DatabaseField
	private Date	startDate;

	@DatabaseField
	private Date	endDate;

	public Semester()
	{

	}

	public Date getEndDate()
	{
		return endDate;
	}

	public int getSemesterWeek(Date today)
	{
		return Semester.countWeeksBetween(startDate, today) + 1;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;

		// preset endDate
		endDate = new Date(startDate.getTime() + WEEKS_IN_SEMESTER
						* DateUtils.WEEK_IN_MILLIS);

		// TODO : refactor that to datastore
		DataStore.getHelper().getSemesterDao().createOrUpdate(this);
	}
}
