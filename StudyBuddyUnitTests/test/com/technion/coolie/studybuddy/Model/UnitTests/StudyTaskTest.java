package com.technion.coolie.studybuddy.Model.UnitTests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThan;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.models.StudyItem;

public class StudyTaskTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void identicalTasksShouldBeEqual() {
		StudyItem task1 = new StudyItem(3);
		StudyItem task2 = new StudyItem(3);

		assertThat(task1, comparesEqualTo(task2));
	}

	@Test
	public void smallerIdTaskShouldBeSmallerThanLargerIdTask() {
		StudyItem task1 = new StudyItem(11);
		StudyItem task2 = new StudyItem(12);

		assertThat(task1, lessThan(task2));
	}

	@Test
	public void defaultLabelEqualToId() throws Exception {
		StudyItem task1 = new StudyItem(3);

		assertThat(task1.getLabel(), is(String.valueOf(3)));
	}

}
