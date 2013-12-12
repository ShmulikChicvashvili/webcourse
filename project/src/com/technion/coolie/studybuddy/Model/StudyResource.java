package com.technion.coolie.studybuddy.Model;

import static com.technion.coolie.studybuddy.Model.Technion.*;
import java.util.ArrayList;
import java.util.List;

public class StudyResource {

	private final List<StudyTask> tasksRemaining = new ArrayList<StudyTask>(
			WEEKS_IN_SEMESTER);
	private final List<StudyTask> tasksDone = new ArrayList<StudyTask>(
			WEEKS_IN_SEMESTER);

	public StudyResource() {
		this(WEEKS_IN_SEMESTER);
	}

	public StudyResource(int num) {
		for (int i = 0; i < num; ++i) {
			this.tasksRemaining.add(new StudyTask(i + 1));
		}
	}

	public StudyResource(List<String> list) {
		int i = 0;
		for (String str : list) {
			this.tasksRemaining.add(new StudyTask(++i, str));
		}
	}

	public int getTasksTotal() {
		return this.tasksRemaining.size() + this.tasksDone.size();
	}

	public int getNumTasksRemaining() {
		return this.tasksRemaining.size();
	}

	public int getNumTasksDone() {
		return this.tasksDone.size();
	}

	public List<String> getTasksDoneLabels() {
		List<String> labels = new ArrayList<String>();
		for (StudyTask t : tasksDone)
			labels.add(t.getLabel());
		return labels;
	}

	public List<Integer> getTasksDoneIds() {
		List<Integer> ids = new ArrayList<Integer>();
		for (StudyTask t : tasksDone)
			ids.add(t.getNum());
		return ids;
	}

	public List<String> getTasksRemainingLabels() {
		List<String> labels = new ArrayList<String>();
		for (StudyTask t : tasksRemaining)
			labels.add(t.getLabel());
		return labels;
	}

	public List<Integer> getTasksRemainingIds() {
		List<Integer> ids = new ArrayList<Integer>();
		for (StudyTask t : tasksRemaining)
			ids.add(t.getNum());
		return ids;
	}

}
