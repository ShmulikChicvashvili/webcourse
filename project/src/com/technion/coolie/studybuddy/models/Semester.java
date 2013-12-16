package com.technion.coolie.studybuddy.models;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "semesters")
public class Semester
{

	public final static int		WEEKS_IN_SEMESTER	= 14;
	private final static long	MILISECONDS_IN_WEEK	= 1000 * 60 * 60 * 24 * 7;

	@DatabaseField(generatedId = true)
	private UUID				id;
	@DatabaseField
	private Date				startDate;
	@DatabaseField
	private Date				endDate;

	public Semester()
	{

	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public int getSemesterWeek(Date today)
	{
		return Semester.countWeeksBetween(startDate, today) + 1;
	}

	public static int countWeeksBetween(Date start, Date end)
	{
		int result = (int) ((end.getTime() - start.getTime()) / MILISECONDS_IN_WEEK);
		return result;
	}
}
