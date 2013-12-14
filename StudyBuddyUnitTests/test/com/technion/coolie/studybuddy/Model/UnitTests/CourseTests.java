package com.technion.coolie.studybuddy.Model.UnitTests;

import static com.technion.coolie.studybuddy.Models.Technion.WEEKS_IN_SEMESTER;
import static com.technion.coolie.studybuddy.utils.Utils.asSet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.Models.Course;
import com.technion.coolie.studybuddy.Models.Exam;
import com.technion.coolie.studybuddy.Models.StudyItem;
import com.technion.coolie.studybuddy.Models.StudyResource;

//@RunWith(RobolectricTestRunner.class)
public class CourseTests {

	@Test
	public void addingAnExamMustReturnIt() {
		Course s = new Course();
		Exam e = mock(Exam.class);

		s.addExam(e);

		assertThat(s.getExams(), is(Arrays.asList(e)));
	}

	@Test
	public void addingExamListReturnsIt() {
		Course s = new Course();
		List<Exam> list = Arrays.asList(mock(Exam.class), mock(Exam.class),
				mock(Exam.class));
		s.addExams(list);
		assertThat(s.getExams(), is(list));

	}

	@Test
	public void addingResourceShouldReturnResource() throws Exception {
		Course s = new Course();

		StudyResource r = new StudyResource();
		s.addStudyResource(r);
		assertThat(s.getStudyResources(), is(asSet(r)));
	}

	@Test
	public void getExamsOnNewSubjectShouldReturnNil() {
		Course s = new Course();
		assertThat(s.getExams(), is(Collections.<Exam> emptyList()));
	}

	@Test
	public void gettingStudyResourcesWithoutAllocationShouldBeEmpty() {
		Course s = new Course();
		assertThat(s.getStudyResources(),
				is(Collections.<StudyResource> emptySet()));
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void subjectReturnsCorrectHebrewName() throws Exception {
		Course s = new Course(234123, "מערכות הפעלה");
		assertThat(s.getName(), is("מערכות הפעלה"));
	}

	@Test
	public void subjectReturnsCorrectNameAndCourseNumber() throws Exception {
		Course s = new Course(123, "name");

		assertThat(s.getId(), is(123));
		assertThat(s.getName(), is("name"));

	}

	@Test
	public void subjectWithoutResourceReturnsZeroTasks() throws Exception {
		Course c = new Course(123, "name");

		assertThat(c.getStudyItemsTotal(), is(0));
		assertThat(c.getStudyItems(), is(Collections.<StudyItem> emptyList()));

	}

	@Test
	public void subjectWithResourceReturnsItsTasks() throws Exception {
		Course c = new Course(123, "name");
		StudyResource sr = mock(StudyResource.class);
		c.addStudyResource(sr);

		List<StudyItem> items = Arrays.asList(mock(StudyItem.class),
				mock(StudyItem.class), mock(StudyItem.class));

		when(sr.getItemsTotal()).thenReturn(3);
		when(sr.getAllItems()).thenReturn(items);

		assertThat(c.getStudyItemsTotal(), is(3));
		assertThat(c.getStudyItems(), is(items));

	}

	@Test
	public void subjectWithDefaultResourceReturnsItsTasks() throws Exception {
		Course c = new Course(123, "name");
		c.addStudyResource(StudyResource.createWithDefaultItems());

		assertThat(c.getStudyItemsTotal(), is(WEEKS_IN_SEMESTER));
		assertThat(c.getStudyItems().size(), is(WEEKS_IN_SEMESTER));

	}

	@Test
	public void subjectWithResourceReturnsRemainingTasks() throws Exception {
		Course c = new Course(123, "name");
		StudyResource sr = mock(StudyResource.class);
		c.addStudyResource(sr);

		List<StudyItem> items = Arrays.asList(mock(StudyItem.class),
				mock(StudyItem.class), mock(StudyItem.class));

		when(sr.getNumItemsRemaining()).thenReturn(3);
		when(sr.getItemsRemaining()).thenReturn(items);

		assertThat(c.getNumStudyItemsRemaining(), is(3));
		assertThat(c.getStudyItemsRemaining(), is(items));

	}

	@After
	public void tearDown() throws Exception {
	}
}
