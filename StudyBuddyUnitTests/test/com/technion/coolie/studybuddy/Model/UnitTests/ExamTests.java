package com.technion.coolie.studybuddy.Model.UnitTests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.lessThan;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Exam;
import com.technion.coolie.studybuddy.models.ExamType;

public class ExamTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void newExamShouldReturnItsValues() {
		Date d = new Date();
		Course s = new Course();
		Exam e = new Exam(d, ExamType.FINAL, s);

		assertThat(e.getDate(), is(d));
		assertThat(e.getType(), is(ExamType.FINAL));
		assertThat(e.getSubject(), is(s));
	}

	@Test
	public void twoExamsOnSameDayInSameSubjectAreEqual() throws Exception {
		Date d = new Date();
		Course s = new Course();
		Exam e1 = new Exam(d, ExamType.FINAL, s);
		Exam e2 = new Exam((Date) d.clone(), ExamType.FINAL, s);

		assertThat(e1, equalTo(e2));
	}

	@Test
	public void examOrderShouldBeByDate() {
		Calendar calendar = Calendar.getInstance();
		Date first = calendar.getTime();
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		Date second = calendar.getTime();

		assertThat(first, lessThan(second));

	}
}
