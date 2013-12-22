package com.technion.coolie.studybuddy.Model.UnitTests;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.mockito.Mockito;
import org.mockito.internal.util.collections.ArrayUtils;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.fest.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.models.WorkStats;

public class WorkStatsTest
{

	private WorkStats	stats	= WorkStats.getInstance();	;

	@Before
	public void setUp() throws Exception
	{
		WorkStats.clear();
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void emptyStatsShouldReportZero()
	{

		assertThat(stats.getStatsForDate(new Date()), is(0));
	}

	@Test
	public void markingDoneShouldReportCorrectly()
	{
		Calendar cal = new GregorianCalendar(2013, 11, 11);
		Date d = cal.getTime();

		stats.increaseDoneForDate(d);
		stats.increaseDoneForDate(d);
		assertThat(stats.getStatsForDate(d), is(2));

	}

	@Test
	public void markingDoneInDifferentTimesShouldReportCorrectly()
	{
		Calendar cal = new GregorianCalendar(2013, 11, 11);
		Date d = cal.getTime();

		stats.increaseDoneForDate(cal.getTime());

		cal.add(Calendar.HOUR_OF_DAY, 5);
		stats.increaseDoneForDate(cal.getTime());

		cal.add(Calendar.HOUR_OF_DAY, 5);
		stats.increaseDoneForDate(cal.getTime());

		assertThat(stats.getStatsForDate(d), is(3));

	}

	@Test
	public void removingDoneShouldReportCorrectly()
	{
		Calendar cal = new GregorianCalendar(2013, 11, 11);
		Date d = cal.getTime();

		stats.increaseDoneForDate(d);
		stats.increaseDoneForDate(d);
		stats.decreaseDoneForDate(d);
		assertThat(stats.getStatsForDate(d), is(1));

	}

	@Test
	public void reportingForMultipleDaysShouldWork()
	{
		Calendar cal = new GregorianCalendar(2013, 11, 11);
		Date d1 = cal.getTime();
		stats.increaseDoneForDate(d1);

		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date d2 = cal.getTime();
		stats.increaseDoneForDate(d2);

		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date d3 = cal.getTime();
		stats.increaseDoneForDate(d3);
		stats.increaseDoneForDate(d3);

		assertThat(stats.getStatsForRange(d1, d3), is(Arrays.array(1, 1, 2)));

	}

	@Test
	public void reportingForMultipleDaysWithBlanksShouldWork()
	{
		Calendar cal = new GregorianCalendar(2013, 11, 11);
		Date d1 = cal.getTime();
		stats.increaseDoneForDate(d1);

		cal.add(Calendar.DAY_OF_MONTH, 2);
		Date d2 = cal.getTime();
		stats.increaseDoneForDate(d2);

		assertThat(stats.getStatsForRange(d1, d2), is(Arrays.array(1, 0, 1)));

	}

	@Test
	public void reportingDaysBackShouldWork()
	{
		Calendar cal = new GregorianCalendar(2013, 11, 11);
		Date d1 = cal.getTime();
		stats.increaseDoneForDate(d1);

		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date d2 = cal.getTime();
		stats.increaseDoneForDate(d2);

		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date d3 = cal.getTime();
		stats.increaseDoneForDate(d3);
		stats.increaseDoneForDate(d3);

		assertThat(stats.getStatsLastXDays(d3, 1), is(Arrays.array(2)));
		assertThat(stats.getStatsLastXDays(d3, 2), is(Arrays.array(1, 2)));
		assertThat(stats.getStatsLastXDays(d3, 3), is(Arrays.array(1, 1, 2)));
		assertThat(stats.getStatsLastXDays(d3, 4), is(Arrays.array(0, 1, 1, 2)));

	}
}
