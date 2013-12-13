package com.technion.coolie.studybuddy.Model;

public class StudyTask implements Comparable<StudyTask> {
	private final int num;
	private final String label;
	private boolean done;

	public StudyTask(int num, String label) {
		this.num = num;
		this.label = label;
		this.done = false;
	}

	public StudyTask(int num) {
		this(num, String.valueOf(num));
	}

	public int getNum() {
		return num;
	}

	public String getLabel() {
		return label;
	}

	public void markDone() {
		done = true;
	}

	public boolean isDone() {
		return (done == true);
	}

	public void markUndone() {
		done = false;
	}

	public void mark() {
		done = !done;
	}

	@Override
	public int compareTo(StudyTask another) {
		return this.num - another.num;
	}
}
