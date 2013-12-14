package com.technion.coolie.studybuddy.Model.UnitTests;

import static com.technion.coolie.studybuddy.Models.Technion.WEEKS_IN_SEMESTER;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.Models.StudyResource;
import com.technion.coolie.studybuddy.utils.Utils;

//@RunWith(RobolectricTestRunner.class)
public class StudyResourceTest {

	@Test
	public void doingFirstTaskShouldMarkItDone() throws Exception {
		StudyResource sr = StudyResource.createWithDefaultItems();
		sr.markDone(1);

		assertThat(sr.getNumItemsDone(), is(1));
		assertThat(sr.getItemsDoneIds(), is(asList(1)));
	}

	@Test
	public void doingSeveralTasksShouldMarkItDone() throws Exception {
		StudyResource sr = StudyResource.createWithDefaultItems();
		sr.markDone(3);
		sr.markDone(5);
		sr.markDone(6);

		assertThat(sr.getNumItemsDone(), is(3));
		assertThat(sr.getItemsDoneIds(), is(asList(3, 5, 6)));
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void shouldStartFromListOfTasks() {
		StudyResource sr = StudyResource.fromItemList(Arrays.asList("A", "B",
				"C"));
		assertThat(sr.getItemsTotal(), is(3));
		assertThat(sr.getNumItemsRemaining(), is(3));
		assertThat(sr.getNumItemsDone(), is(0));
		assertThat(sr.getItemsDoneLabels(),
				is(Collections.<String> emptyList()));
		assertThat(sr.getItemsDoneIds(), is(Collections.<Integer> emptyList()));
		assertThat(sr.getItemsRemainingLabels(),
				is(Arrays.asList("A", "B", "C")));
		assertThat(sr.getItemsRemainingIds(), is(asList(1, 2, 3)));
	}

	@Test
	public void shouldStartWithAmountAllocated() {
		int num = Utils.randomInt(20);
		StudyResource sr = StudyResource.createWithItems(num);
		assertThat(sr.getItemsTotal(), is(num));
		assertThat(sr.getNumItemsRemaining(), is(num));
		assertThat(sr.getNumItemsDone(), is(0));
		assertThat(sr.getItemsDoneIds(), is(Collections.<Integer> emptyList()));

	}

	@Test
	public void shouldStartWithSemesterLengthTasks() {
		StudyResource sr = StudyResource.createWithDefaultItems();
		assertThat(sr.getItemsTotal(), is(WEEKS_IN_SEMESTER));
		assertThat(sr.getNumItemsRemaining(), is(WEEKS_IN_SEMESTER));
		assertThat(sr.getNumItemsDone(), is(0));
		assertThat(sr.getItemsDoneIds(), is(Collections.<Integer> emptyList()));
		assertThat(sr.getItemsRemainingIds(),
				is(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)));

	}

	@After
	public void tearDown() throws Exception {
	}

}
