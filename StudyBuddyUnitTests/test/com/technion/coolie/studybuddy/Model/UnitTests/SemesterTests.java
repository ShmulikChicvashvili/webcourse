package com.technion.coolie.studybuddy.Model.UnitTests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.models.Semester;

public class SemesterTests
{

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void countingWeeksInSameYearWorks()
	{
		assertThat(
				Semester.countWeeksBetween(getDate(2013, 12, 3),
						getDate(2013, 12, 17)), is(2));

	}

	@Test
	public void waitForCompleteWeek() throws Exception
	{
		assertThat(
				Semester.countWeeksBetween(getDate(2013, 12, 17),
						getDate(2013, 12, 23)), is(0));

	}

	@Test
	public void daysInDifferentYearsSameWeekCorrect() throws Exception
	{
		assertThat(
				Semester.countWeeksBetween(getDate(2013, 12, 30),
						getDate(2014, 1, 3)), is(0));
	}

	@Test
	public void daysInDifferentYearsDifferentWeeksCorrect() throws Exception
	{
		assertThat(
				Semester.countWeeksBetween(getDate(2013, 12, 15),
						getDate(2014, 1, 19)), is(5));
	}

	private Date getDate(int year, int month, int day)
	{
		return new GregorianCalendar(year, month - 1, day).getTime();
	}
}
