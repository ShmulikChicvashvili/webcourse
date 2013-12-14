package com.technion.coolie.studybuddy.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.technion.coolie.studybuddy.utils.Utils;

public class Course implements Comparable<Course> {

	private final List<Exam> exams = new ArrayList<Exam>();
	private final Set<StudyResource> trackedResouces = new HashSet<StudyResource>();
	private final int id;
	private final String name;

	public Course() {
		id = radomId();
		name = String.valueOf(id);
	}

	private static int radomId() {
		return Utils.randomInt(999999);

	}

	public Course(int id, String name) {
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
		trackedResouces.addAll(Arrays.asList(r));
	}

	public void addStudyResources(Collection<StudyResource> r) {
		trackedResouces.addAll(r);
	}

	public int getStudyItemsTotal() {
		int sum = 0;
		for (StudyResource sr : trackedResouces) {
			sum += sr.getItemsTotal();
		}
		return sum;
	}

	public List<StudyItem> getStudyItems() {
		List<StudyItem> list = new ArrayList<StudyItem>();
		for (StudyResource sr : trackedResouces) {
			list.addAll(sr.getAllItems());
		}
		return list;
	}

	public int getNumStudyItemsRemaining() {
		int total = 0;
		for (StudyResource sr : trackedResouces) {
			total += sr.getNumItemsRemaining();
		}
		return total;

	}

	public List<StudyItem> getStudyItemsRemaining() {

		List<StudyItem> list = new ArrayList<StudyItem>();
		for (StudyResource sr : trackedResouces) {
			list.addAll(sr.getItemsRemaining());
		}
		return list;

	}

	@Override
	public int compareTo(Course another) {
		return another.getNumStudyItemsRemaining()
				- getNumStudyItemsRemaining();
	}

	@Override
	public String toString() {
		return String.valueOf(id) + " " + name;
	}
}
