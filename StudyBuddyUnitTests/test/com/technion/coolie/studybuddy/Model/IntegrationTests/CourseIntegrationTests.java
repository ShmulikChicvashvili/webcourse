package com.technion.coolie.studybuddy.Model.IntegrationTests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.Models.Course;
import com.technion.coolie.studybuddy.Models.StudyItem;
import com.technion.coolie.studybuddy.Models.StudyResource;

public class CourseIntegrationTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void markingAnItemCompleteShouldAffectCourse() throws Exception {
		Course c = new Course(123, "name");
		c.addStudyResource(StudyResource.createWithItems(10));

		assertThat(c.getNumStudyItemsRemaining(), is(10));

		c.getStudyItems().get(1).markDone();

		assertThat(c.getNumStudyItemsRemaining(), is(9));

	}

	@Test
	public void markingAnItemCompleteAgainShouldNotHelp() throws Exception {
		Course c = new Course(123, "name");
		c.addStudyResource(StudyResource.createWithItems(10));

		assertThat(c.getNumStudyItemsRemaining(), is(10));

		c.getStudyItems().get(1).markDone();

		assertThat(c.getNumStudyItemsRemaining(), is(9));

		c.getStudyItems().get(1).markDone();

		assertThat(c.getNumStudyItemsRemaining(), is(9));

	}

	@Test
	public void togglingAnItemShouldWork() throws Exception {
		Course c = new Course(123, "name");
		c.addStudyResource(StudyResource.createWithItems(10));

		assertThat(c.getNumStudyItemsRemaining(), is(10));

		c.getStudyItems().get(1).toggleDone();

		assertThat(c.getNumStudyItemsRemaining(), is(9));

		c.getStudyItems().get(1).toggleDone();

		assertThat(c.getNumStudyItemsRemaining(), is(10));

	}
}
