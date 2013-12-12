package com.technion.coolie.studybuddy.Model.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.technion.coolie.studybuddy.Model.Exam;
import com.technion.coolie.studybuddy.Model.ExamType;
import com.technion.coolie.studybuddy.Model.Subject;

public class ExamTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void newExamShouldReturnItsValues() {
		Date d = new Date();
		Subject s = new Subject();
		Exam e = new Exam(d, ExamType.FINAL, s);

		assertThat(e.getDate(), is(d));
		assertThat(e.getType(), is(ExamType.FINAL));
		assertThat(e.getSubject(), is(s));
	}

	@Test
	public void twoExamsOnSameDayInSameSubjectAreEqual() throws Exception {
		Date d = new Date();
		Subject s = new Subject();
		Exam e1 = new Exam(d, ExamType.FINAL, s);
		Exam e2 = new Exam((Date) d.clone(), ExamType.FINAL, s);

		assertThat(e1, equalTo(e2));
	}

}
