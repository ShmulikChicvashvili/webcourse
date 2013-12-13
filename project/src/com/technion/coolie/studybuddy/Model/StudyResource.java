package com.technion.coolie.studybuddy.Model;

import static com.technion.coolie.studybuddy.Model.Technion.WEEKS_IN_SEMESTER;
import static com.technion.coolie.studybuddy.utils.Utils.asSortedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.technion.coolie.studybuddy.utils.SparseArrayMap;

public class StudyResource {

	private final Map<Integer, StudyTask> tasksRemaining = new SparseArrayMap<StudyTask>(
			WEEKS_IN_SEMESTER);
	private final Map<Integer, StudyTask> tasksDone = new SparseArrayMap<StudyTask>(
			WEEKS_IN_SEMESTER);
	private final String label;

	public StudyResource() {
		this("DEFAULT", WEEKS_IN_SEMESTER);
	}

	public StudyResource(int num) {
		this("DEFAULT", num);
	}

	public StudyResource(String label, int num) {
		for (int i = 0; i < num; ++i) {
			this.tasksRemaining.put(i + 1, new StudyTask(i + 1));
		}

		this.label = label;
	}

	public StudyResource(List<String> list) {
		this("DEFAULT", list);
	}

	public StudyResource(String label, List<String> list) {
		int i = 0;
		for (String str : list) {
			this.tasksRemaining.put(i + 1, new StudyTask(++i, str));
		}
		this.label = label;
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
		for (StudyTask task : tasksDone.values())
			labels.add(task.getLabel());

		return labels;
	}

	public List<Integer> getTasksDoneIds() {
		return asSortedList(tasksDone.keySet());
	}

	public List<String> getTasksRemainingLabels() {
		List<String> labels = new ArrayList<String>();
		for (StudyTask t : asSortedList(tasksRemaining.values()))
			labels.add(t.getLabel());
		return labels;
	}

	public List<Integer> getTasksRemainingIds() {
		return asSortedList(tasksRemaining.keySet());
	}

	public void markDone(int id) {
		toggleDone(id, tasksRemaining, tasksDone);
	}

	private void toggleDone(int id, Map<Integer, StudyTask> source,
			Map<Integer, StudyTask> target) {
		if (!source.containsKey(id)) {
			return;
		}

		StudyTask task = source.get(id);
		source.remove(id);
		task.mark();
		target.put(id, task);
	}

	public void markUnDone(int id) {
		toggleDone(id, tasksDone, tasksRemaining);
	}

}
