package com.technion.coolie.ug.gradessheet;

public class GradesFooterItem implements SectionedListItem {

	public final String semesterAvg;
	public final String semesterTotalPoints;

	public GradesFooterItem(String semesterAvg, String semesterTotalPoints) {
		this.semesterAvg = semesterAvg;
		this.semesterTotalPoints = semesterTotalPoints;
	}

	public String getSemesterAvg() {
		return semesterAvg;
	}

	public String getSemesterTotalPoints() {
		return semesterTotalPoints;
	}

	@Override
	public boolean isSection() {
		return false;
	}

	@Override
	public boolean isFooter() {
		return true;
	}

}
