package com.technion.coolie.studybuddy.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.technion.coolie.studybuddy.utils.Utils;

public class Subject {

	private final List<Exam> exams = new ArrayList<Exam>();
	private final Set<StudyResource> trackedResouces = new HashSet<StudyResource>();
	private final int id;
	private final String name;

	public Subject() {
		this.id = radomId();
		this.name = String.valueOf(id);
	}

	private static int radomId() {
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

	public Set<StudyResource> getStudyResources() {
		return trackedResouces;
	}

	public void addStudyResource(StudyResource r) {
		trackedResouces.add(r);
	}

	public void addStudyResources(StudyResource... r) {
		this.trackedResouces.addAll(Arrays.asList(r));
	}

	public void addStudyResources(Collection<StudyResource> r) {
		this.trackedResouces.addAll(r);
	}
}
