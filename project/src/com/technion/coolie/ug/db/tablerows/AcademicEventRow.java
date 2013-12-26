package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.db.UGDBTables;
import com.technion.coolie.ug.db.UGDBTables.AcademicEvents;
import com.technion.coolie.ug.model.AcademicCalendarEvent;

@DatabaseTable(tableName = UGDBTables.AcademicEvents.TABLENAME)
public class AcademicEventRow {

	@DatabaseField(id = true, generatedId = true)
	// @DefaultSortOrder
	private String key;

	private AcademicCalendarEvent event;

	public enum Status {
		CREATED, SENT
	}

	public AcademicEventRow(AcademicCalendarEvent event) {
		this.event = event;
	}

	// MUST HAVE EMPTY CONSTRUCTOR!
	public AcademicEventRow() {

	}

	// MUST HAVE THE SAME NAME AS THE FIELD NAME
	public String getKey() {
		return key;
	}

	public void setKey(String coursekey) {
		this.key = coursekey;
	}

	public AcademicCalendarEvent getEvent() {
		return event;
	}

	public void setEvent(AcademicCalendarEvent event) {
		this.event = event;
	}

}
