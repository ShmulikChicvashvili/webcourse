package com.technion.coolie.ug.gradessheet;


public class GradesEntryItem implements Item{

	public final String courseName;
	public final String points;
	public final String grade;

	public GradesEntryItem(String name, String points,String grade) {
		this.courseName = name;
		this.points = points;
		this.grade = grade;
	}
	
	@Override
	public boolean isSection() {
		return false;
	}

}
