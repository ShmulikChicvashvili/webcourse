package com.technion.coolie.ug.calendar;

import com.technion.coolie.ug.gradessheet.Item;

public class CalendarSectionItem implements Item {

	private final String month;

	public CalendarSectionItem(final String month) {
		this.month = month;
	}

	public String getMonth() {
		return month;
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
