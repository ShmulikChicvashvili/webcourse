package com.technion.coolie.studybuddy.Model.test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.Model.StudyResource;
import com.technion.coolie.studybuddy.Model.StudyTask;
import com.technion.coolie.studybuddy.Model.Utils;

import static com.technion.coolie.studybuddy.Model.Technion.*;

public class StudyResourceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldStartWithSemesterLengthTasks() {
		StudyResource sr = new StudyResource();
		assertThat(sr.getTasksTotal(), is(WEEKS_IN_SEMESTER));
		assertThat(sr.getNumTasksRemaining(), is(WEEKS_IN_SEMESTER));
		assertThat(sr.getNumTasksDone(), is(0));
		assertThat(sr.getTasksDoneIds(), is(Collections.<Integer> emptyList()));
		assertThat(
				sr.getTasksRemainingIds(),
				is(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)));

	}

	@Test
	public void shouldStartWithAmountAllocated() {
		int num = Utils.randomInt(20);
		StudyResource sr = new StudyResource(num);
		assertThat(sr.getTasksTotal(), is(num));
		assertThat(sr.getNumTasksRemaining(), is(num));
		assertThat(sr.getNumTasksDone(), is(0));
		assertThat(sr.getTasksDoneIds(), is(Collections.<Integer> emptyList()));

	}

	@Test
	public void shouldStartFromListOfTasks() {
		StudyResource sr = new StudyResource(Arrays.asList("A", "B", "C"));
		assertThat(sr.getTasksTotal(), is(3));
		assertThat(sr.getNumTasksRemaining(), is(3));
		assertThat(sr.getNumTasksDone(), is(0));
		assertThat(sr.getTasksDoneLabels(),
				is(Collections.<String> emptyList()));
		assertThat(sr.getTasksDoneIds(), is(Collections.<Integer> emptyList()));
		assertThat(sr.getTasksRemainingLabels(),
				is(Arrays.asList("A", "B", "C")));
		assertThat(sr.getTasksRemainingIds(), is(Arrays.asList(1, 2, 3)));
	}
}
