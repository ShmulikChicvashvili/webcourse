package com.technion.coolie.studybuddy.Model.test;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.technion.coolie.studybuddy.Model.StudyResource;
import com.technion.coolie.studybuddy.Model.StudyTask;
import com.technion.coolie.studybuddy.utils.Utils;

import static com.technion.coolie.studybuddy.Model.Technion.*;

@RunWith(RobolectricTestRunner.class)
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
		assertThat(sr.getTasksRemainingIds(),
				is(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)));

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
		assertThat(sr.getTasksRemainingIds(), is(asList(1, 2, 3)));
	}

	@Test
	public void doingFirstTaskShouldMarkItDone() throws Exception {
		StudyResource sr = new StudyResource();
		sr.markDone(1);

		assertThat(sr.getNumTasksDone(), is(1));
		assertThat(sr.getTasksDoneIds(), is(asList(1)));
	}

	@Test
	public void doingSeveralTasksShouldMarkItDone() throws Exception {
		StudyResource sr = new StudyResource();
		sr.markDone(3);
		sr.markDone(5);
		sr.markDone(6);

		assertThat(sr.getNumTasksDone(), is(3));
		assertThat(sr.getTasksDoneIds(), is(asList(3, 5, 6)));
	}

}
