package com.technion.coolie.studybuddy.Model.test;

import static com.technion.coolie.studybuddy.utils.Utils.asSet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.technion.coolie.studybuddy.Model.Exam;
import com.technion.coolie.studybuddy.Model.StudyResource;
import com.technion.coolie.studybuddy.Model.Subject;

@RunWith(RobolectricTestRunner.class)
public class SubjectTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getExamsOnNewSubjectShouldReturnNil() {
		Subject s = new Subject();
		assertThat(s.getExams(), is(Collections.<Exam> emptyList()));
	}

	@Test
	public void addingAnExamMustReturnIt() {
		Subject s = new Subject();
		Exam e = mock(Exam.class);

		s.addExam(e);

		assertThat(s.getExams(), is(Arrays.asList(e)));
	}

	@Test
	public void addingExamListReturnsIt() {

		Subject s = new Subject();
		List<Exam> list = Arrays.asList(mock(Exam.class), mock(Exam.class),
				mock(Exam.class));

		s.addExams(list);
		assertThat(s.getExams(), is(list));

	}

	@Test
	public void subjectReturnsCorrectNameAndCourseNumber() throws Exception {
		Subject s = new Subject(123, "name");

		assertThat(s.getId(), is(123));
		assertThat(s.getName(), is("name"));

	}

	@Test
	public void subjectReturnsCorrectHebrewName() throws Exception {
		Subject s = new Subject(234123, "������ �����");
		assertThat(s.getName(), is("������ �����"));
	}

	@Test
	public void gettingStudyResourcesWithoutAllocationShouldBeEmpty() {
		Subject s = new Subject();

		assertThat(s.getStudyResources(),
				is(Collections.<StudyResource> emptySet()));

	}

	@Test
	public void addingResourceShouldReturnResource() throws Exception {
		Subject s = new Subject();

		StudyResource r = new StudyResource();
		s.addStudyResource(r);
		assertThat(s.getStudyResources(), is(asSet(r)));

	}
}
