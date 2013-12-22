package com.technion.coolie.assignmentor;

import android.text.SpannableString;
import android.text.style.StrikethroughSpan;

public class TasksInfo {

	int id;
	
	SpannableString taskName;
	String courseId, courseName, dueDate;
	Boolean isDone = false;
	int difficulty, importance, progress;
	// Used to store the task's event id on google calendar.
	long eventID;
	String url;
	
	public TasksInfo(SpannableString taskName, String courseName, String courseId, String dueDate) {
		this.taskName = taskName;
		this.courseName = courseName;
		this.courseId = courseId;
		this.dueDate = dueDate;
	}
	
	public TasksInfo(SpannableString taskName, String courseName, String courseId, String dueDate, 
			boolean isDone, int difficulty, int importance, int progress, String url, int eventID) {
		this.taskName = taskName;
		this.courseName = courseName;
		this.courseId = courseId;
		this.dueDate = dueDate;
		this.isDone = isDone;
		this.difficulty = difficulty;
		this.importance = importance;
		this.progress = progress;
		this.url = url;
		this.eventID = eventID;
		this.changeStatus(this.isDone);
	}
	
	public TasksInfo(SpannableString taskName, String courseName, String courseId, String dueDate, 
			boolean isDone, int difficulty, int importance, int progress, String url) {
		this.taskName = taskName;
		this.courseName = courseName;
		this.courseId = courseId;
		this.dueDate = dueDate;
		this.isDone = isDone;
		this.difficulty = difficulty;
		this.importance = importance;
		this.progress = progress;
		this.url = url;
		this.changeStatus(this.isDone);
	}
	
	public String[] getStringArrInfo() {
		return new String[] { taskName.toString(), courseName, courseId, dueDate };
	}
	
	public int[] getIntArrInfo() {
		return new int[] { difficulty, importance, progress };
	}
	
	// Eclipse Auto-Generated functions hashCode() and equals()
	// Used to compare new fetched tasks against tasks in the list.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((courseId == null) ? 0 : courseId.hashCode());
		result = prime * result
				+ ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result
				+ ((taskName == null) ? 0 : taskName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TasksInfo other = (TasksInfo) obj;
		if (courseId == null) {
			if (other.courseId != null)
				return false;
		} else if (!courseId.equals(other.courseId))
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!(taskName.toString()).equals(other.taskName.toString()))
			return false;
		return true;
	}
	
	public void changeStatus(boolean done) {
		if (done) {
			this.isDone = true;
			this.taskName.setSpan(new StrikethroughSpan(), 0, 
					this.taskName.length(), 0);
		} else {
			this.isDone = false;
			StrikethroughSpan[] stspans = this.taskName.getSpans(0, 
					this.taskName.length(), StrikethroughSpan.class);
			for(StrikethroughSpan st : stspans) {
				this.taskName.removeSpan(st);
			}
		}
	}
}
