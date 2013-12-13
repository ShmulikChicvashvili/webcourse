package com.technion.coolie.studybuddy.Model.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.Model.StudyTask;

public class StudyTaskTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void identicalTasksShouldBeEqual() {
		StudyTask task1 = new StudyTask(3);
		StudyTask task2 = new StudyTask(3);

		assertThat(task1, comparesEqualTo(task2));
	}

	@Test
	public void smallerIdTaskShouldBeSmallerThanLargerIdTask() {
		StudyTask task1 = new StudyTask(11);
		StudyTask task2 = new StudyTask(12);

		assertThat(task1, lessThan(task2));
	}

	@Test
	public void defaultLabelEqualToId() throws Exception {
		StudyTask task1 = new StudyTask(3);

		assertThat(task1.getLabel(), is(String.valueOf(3)));
	}

}
