package com.technion.coolie.ug.model;

import java.util.List;

public class RegistrationGroup {

	public RegistrationGroup(String groupId, List<GroupInfo> lectures,
			List<GroupInfo> tutorials, int freePlaces) {
		super();
		this.groupId = groupId;
		this.lectures = lectures;
		this.tutorials = tutorials;
		this.freePlaces = freePlaces;
	}

	private String groupId;
	private List<GroupInfo> lectures;
	private List<GroupInfo> tutorials;
	private int freePlaces;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<GroupInfo> getLectures() {
		return lectures;
	}

	public void setLectures(List<GroupInfo> lectures) {
		this.lectures = lectures;
	}

	public List<GroupInfo> getTutorials() {
		return tutorials;
	}

	public void setTutorials(List<GroupInfo> tutorials) {
		this.tutorials = tutorials;
	}

	public int getFreePlaces() {
		return freePlaces;
	}

	public void setFreePlaces(int freePlaces) {
		this.freePlaces = freePlaces;
	}

}
