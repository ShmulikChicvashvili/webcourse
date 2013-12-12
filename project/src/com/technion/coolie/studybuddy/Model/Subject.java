package com.technion.coolie.studybuddy.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Subject {

	private final List<Exam> exams = new ArrayList<Exam>();
	private final int id;
	private final String name;
	private static Random g = new Random((new Date()).getTime());

	public Subject() {
		this.id = radomId();
		this.name = "";
	}

	public static int radomId() {
		return g.nextInt(999999);

	}

	public Subject(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void addExam(Exam e) {
		exams.add(e);
	}

	public void addExams(Exam... exams) {
		this.exams.addAll(Arrays.asList(exams));
	}

	public void addExams(Collection<Exam> exams) {
		this.exams.addAll(exams);
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
}
