package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.model.AcademicCalendarEvent;

@DatabaseTable(tableName = AcademicEventRow.TABLENAME)
public class AcademicEventRow {

	public static final String TABLENAME = "AcademicEventsTable";
	@DatabaseField(generatedId = true)
	// @DefaultSortOrder
	private long key;

	@DatabaseField(useGetSet = true, dataType = DataType.SERIALIZABLE)
	private AcademicCalendarEvent event;

	public AcademicEventRow(AcademicCalendarEvent event) {
		this.event = event;
	}

	// MUST HAVE EMPTY CONSTRUCTOR!
	public AcademicEventRow() {

	}

	// MUST HAVE THE SAME NAME AS THE FIELD NAME
	public long getKey() {
		return key;
	}

	public void setKey(long key) {
		this.key = key;
	}

	public AcademicCalendarEvent getEvent() {
		return event;
	}

	public void setEvent(AcademicCalendarEvent event) {
		this.event = event;
	}

}
