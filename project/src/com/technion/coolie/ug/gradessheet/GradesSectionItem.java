package com.technion.coolie.ug.gradessheet;

public class GradesSectionItem implements Item{

	private final String title;
	
	public GradesSectionItem(String title) {
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	@Override
	public boolean isSection() {
		return true;
	}

}
