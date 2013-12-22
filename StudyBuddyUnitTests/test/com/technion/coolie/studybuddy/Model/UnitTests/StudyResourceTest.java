package com.technion.coolie.studybuddy.Model.UnitTests;

import static com.technion.coolie.studybuddy.models.Semester.WEEKS_IN_SEMESTER;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.models.StudyResource;
import com.technion.coolie.studybuddy.utils.Utils;

//@RunWith(RobolectricTestRunner.class)
public class StudyResourceTest
{

	@Test
	public void doingFirstTaskShouldMarkItDone() throws Exception
	{
		StudyResource sr = StudyResource.createWithItems("LEC",
				WEEKS_IN_SEMESTER);
		sr.markDone(1);

		assertThat(sr.getDoneItemsCount(), is(1));
		assertThat(sr.getRemainingItemsCount(), is(WEEKS_IN_SEMESTER - 1));
		assertThat(sr.getItemsDoneIds(), is(asList(1)));
	}

	@Test
	public void doingSeveralTasksShouldMarkItDone() throws Exception
	{
		StudyResource sr = StudyResource.createWithItems("LEC",
				WEEKS_IN_SEMESTER);
		sr.markDone(3);
		sr.markDone(5);
		sr.markDone(6);

		assertThat(sr.getDoneItemsCount(), is(3));
		assertThat(sr.getRemainingItemsCount(), is(WEEKS_IN_SEMESTER - 3));
		assertThat(sr.getItemsDoneIds(), is(asList(3, 5, 6)));
	}

	@Test
	public void togglingShouldWork() throws Exception
	{
		StudyResource sr = StudyResource.createWithItems("LEC",
				WEEKS_IN_SEMESTER);
		sr.markDone(3);
		sr.markDone(5);
		sr.markDone(6);
		sr.markUnDone(3);
		sr.markUnDone(5);

		assertThat(sr.getDoneItemsCount(), is(1));
		assertThat(sr.getRemainingItemsCount(), is(WEEKS_IN_SEMESTER - 1));
		assertThat(sr.getItemsDoneIds(), is(asList(6)));
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void shouldStartFromListOfTasks()
	{
		StudyResource sr = StudyResource.fromItemList("TEST",
				Arrays.asList("A", "B", "C"));
		assertThat(sr.getTotalItemCount(), is(3));
		assertThat(sr.getRemainingItemsCount(), is(3));
		assertThat(sr.getDoneItemsCount(), is(0));
		assertThat(sr.getItemsDoneLabels(),
				is(Collections.<String> emptyList()));
		assertThat(sr.getItemsDoneIds(), is(Collections.<Integer> emptyList()));
		assertThat(sr.getItemsRemainingLabels(),
				is(Arrays.asList("A", "B", "C")));
		assertThat(sr.getItemsRemainingIds(), is(asList(1, 2, 3)));
	}

	@Test
	public void shouldStartWithAmountAllocated()
	{
		int num = Utils.randomInt(20);
		StudyResource sr = StudyResource.createWithItems("LEC", num);
		assertThat(sr.getTotalItemCount(), is(num));
		assertThat(sr.getRemainingItemsCount(), is(num));
		assertThat(sr.getDoneItemsCount(), is(0));
		assertThat(sr.getItemsDoneIds(), is(Collections.<Integer> emptyList()));

	}

	@Test
	public void shouldStartWithSemesterLengthTasks()
	{
		StudyResource sr = StudyResource.createWithItems("LEC",
				WEEKS_IN_SEMESTER);
		assertThat(sr.getTotalItemCount(), is(WEEKS_IN_SEMESTER));
		assertThat(sr.getRemainingItemsCount(), is(WEEKS_IN_SEMESTER));
		assertThat(sr.getDoneItemsCount(), is(0));
		assertThat(sr.getItemsDoneIds(), is(Collections.<Integer> emptyList()));
		assertThat(sr.getItemsRemainingIds(),
				is(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)));

	}

	@Test
	public void reduceResourceSizeWorks()
	{
		StudyResource sr = StudyResource.createWithItems("LEC", 10);

		sr.resizeTo(15);
		assertThat(sr.getTotalItemCount(), is(15));
		assertThat(sr.getItemsRemainingIds(),
				is(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)));
	}

	@Test
	public void increaseResourceSizeWorks()
	{
		StudyResource sr = StudyResource.createWithItems("LEC", 15);

		sr.resizeTo(10);
		assertThat(sr.getTotalItemCount(), is(10));
		assertThat(sr.getItemsRemainingIds(),
				is(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
	}

	@After
	public void tearDown() throws Exception
	{
	}

}
