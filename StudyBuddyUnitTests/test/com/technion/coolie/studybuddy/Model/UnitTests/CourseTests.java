package com.technion.coolie.studybuddy.Model.UnitTests;

import static com.technion.coolie.studybuddy.models.Semester.WEEKS_IN_SEMESTER;
import static com.technion.coolie.studybuddy.utils.Utils.asSet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Exam;
import com.technion.coolie.studybuddy.models.StudyItem;
import com.technion.coolie.studybuddy.models.StudyResource;

//@RunWith(RobolectricTestRunner.class)
public class CourseTests
{

	// @Test
	// public void addingAnExamMustReturnIt()
	// {
	// Course s = new Course();
	// Exam e = mock(Exam.class);
	//
	// s.addExam(e);
	//
	// assertThat(s.getExams(), is(Arrays.asList(e)));
	// }
	//
	// @Test
	// public void addingExamListReturnsIt()
	// {
	// Course s = new Course();
	// List<Exam> list = Arrays.asList(mock(Exam.class), mock(Exam.class),
	// mock(Exam.class));
	// s.addExams(list);
	// assertThat(s.getExams(), is(list));
	//
	// }

	@Test
	public void addingResourceShouldReturnResource() throws Exception
	{
		Course s = new Course();

		StudyResource r = new StudyResource();
		s.addStudyResource(r);
		assertThat(s.getAllStudyResources(), is((Collection) Arrays.asList(r)));
	}

	// @Test
	// public void getExamsOnNewSubjectShouldReturnNil()
	// {
	// Course s = new Course();
	// assertThat(s.getExams(), is(Collections.<Exam> emptyList()));
	// }

	@Test
	public void courseOrderingByUndoneTasksWorks() throws Exception
	{
		Course c1 = new Course();
		Course c2 = new Course();
		Course c3 = new Course();
		c1.addStudyResource(StudyResource.createWithItems("LEC", 3));
		c2.addStudyResource(StudyResource.createWithItems("LEC", 4));
		c3.addStudyResource(StudyResource.createWithItems("LEC", 4));

		assertThat(c2, lessThan(c1));
		assertThat(c2, comparesEqualTo(c3));

	}

	@Test
	public void gettingStudyResourcesWithoutAllocationShouldBeEmpty()
	{
		Course s = new Course();
		assertThat(s.getAllStudyResources(),
						is((Collection) Collections.<StudyResource> emptyList()));
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void subjectReturnsCorrectHebrewName() throws Exception
	{
		Course s = new Course(234123, "מערכות הפעלה");
		assertThat(s.getName(), is("מערכות הפעלה"));
	}

	@Test
	public void subjectReturnsCorrectNameAndCourseNumber() throws Exception
	{
		Course s = new Course("123", "name");

		assertThat(s.getId(), is("123"));
		assertThat(s.getName(), is("name"));

	}

	@Test
	public void subjectWithDefaultResourceReturnsItsTasks() throws Exception
	{
		Course c = new Course(123, "name");
		c.addStudyResource(StudyResource.createWithItems("LEC",
						WEEKS_IN_SEMESTER));

		assertThat(c.getStudyItemsTotal(), is(WEEKS_IN_SEMESTER));
		assertThat(c.getStudyItemsLabels("LEC").size(), is(WEEKS_IN_SEMESTER));

	}

	@Test
	public void subjectWithoutResourceReturnsZeroTasks() throws Exception
	{
		Course c = new Course("123", "name");

		assertThat(c.getStudyItemsTotal(), is(0));

	}

	@Test
	public void subjectWithResourceReturnsItsTasks() throws Exception
	{
		Course c = new Course(123, "name");
		StudyResource sr = mock(StudyResource.class);
		c.addStudyResource(sr);

		List<String> items = Arrays.asList("1", "2", "3");

		when(sr.getTotalItemCount()).thenReturn(3);
		when(sr.getAllItemsLabels()).thenReturn(items);
		when(sr.getName()).thenReturn("LEC");

		assertThat(c.getStudyItemsTotal(), is(3));
		assertThat(c.getStudyItemsLabels("LEC"), is(items));

	}

	@Test
	public void subjectWithResourceReturnsRemainingTasks() throws Exception
	{
		Course c = new Course(123, "name");
		StudyResource sr = mock(StudyResource.class);
		c.addStudyResource(sr);

		List<String> items = Arrays.asList("1", "2", "3");

		String name = "name";
		when(sr.getRemainingItemsCount()).thenReturn(3);
		when(sr.getItemsRemainingLabels()).thenReturn(items);
		when(sr.getName()).thenReturn(name);

		assertThat(c.getNumStudyItemsRemaining(), is(3));
		assertThat(c.getStudyItemsRemaining(name), is(items));

	}

	@After
	public void tearDown() throws Exception
	{
	}
}
