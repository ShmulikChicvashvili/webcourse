package com.technion.coolie.studybuddy.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Subject {

	private final List<Exam> exams = new ArrayList<Exam>();
	private final List<StudyResource> studResources = new ArrayList<StudyResource>();
	private final int id;
	private final String name;

	public Subject() {
		this.id = radomId();
		this.name = "";
	}

	public static int radomId() {
		return Utils.randomInt(999999);

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

	public List<StudyResource> getStudyResources() {
		return studResources;
	}

	public void addStudyResource(StudyResource r) {
		studResources.add(r);
	}

	public void addStudyResources(StudyResource... r) {
		this.studResources.addAll(Arrays.asList(r));
	}

	public void addStudyResources(Collection<StudyResource> r) {
		this.studResources.addAll(r);
	}
}
