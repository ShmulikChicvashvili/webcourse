package com.technion.coolie.teletech;

import java.util.Date;

public class OfficeHour {
	private final String day;
	private final Date from;
	private final Date to;

	public OfficeHour() {
		// this is so that this C'tor would compile.
		day = null;
		from = null;
		to = null;
	}

	public OfficeHour(String dayOfTheWeek, Date fromHour, Date toHour) {
		day = dayOfTheWeek;
		from = fromHour;
		to = toHour;
	}

	@Override
	public String toString() {
		if (day == null || from == null || to == null)
			return "TD";
		return day + " " + from.toString() + "-" + to.toString();
	}

}
