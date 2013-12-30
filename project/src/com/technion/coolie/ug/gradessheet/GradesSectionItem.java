package com.technion.coolie.ug.gradessheet;

public class GradesSectionItem implements SectionedListItem{

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

	@Override
	public boolean isFooter() {
		return false;
	}

}
