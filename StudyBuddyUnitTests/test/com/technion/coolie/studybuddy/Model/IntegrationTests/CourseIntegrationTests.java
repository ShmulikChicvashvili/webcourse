package com.technion.coolie.studybuddy.Model.IntegrationTests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.StudyResource;

public class CourseIntegrationTests
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
	public void markingAnItemCompleteShouldAffectCourse() throws Exception
	{
		Course c = new Course(123, "name");
		c.addStudyResource(StudyResource.createWithItems("LEC", 10));

		assertThat(c.getNumStudyItemsRemaining(), is(10));

		c.getStudyItems().get(1).markDone();

		assertThat(c.getNumStudyItemsRemaining(), is(9));

	}

	@Test
	public void markingAnItemCompleteAgainShouldNotHelp() throws Exception
	{
		Course c = new Course(123, "name");
		c.addStudyResource(StudyResource.createWithItems("LEC", 10));

		assertThat(c.getNumStudyItemsRemaining(), is(10));

		c.getStudyItems().get(1).markDone();

		assertThat(c.getNumStudyItemsRemaining(), is(9));

		c.getStudyItems().get(1).markDone();

		assertThat(c.getNumStudyItemsRemaining(), is(9));

	}

	@Test
	public void togglingAnItemShouldWork() throws Exception
	{
		Course c = new Course(123, "name");
		c.addStudyResource(StudyResource.createWithItems("LEC", 10));

		assertThat(c.getNumStudyItemsRemaining(), is(10));

		c.getStudyItems().get(1).toggleDone();

		assertThat(c.getNumStudyItemsRemaining(), is(9));

		c.getStudyItems().get(1).toggleDone();

		assertThat(c.getNumStudyItemsRemaining(), is(10));

	}
}
