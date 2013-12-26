package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.model.CourseKey;

@DatabaseTable(tableName = TrackRow.TABLENAME)
public class TrackRow {

	public static final String TABLENAME = "TrackingCoursesTable";

	@DatabaseField(id = true)
	// @DefaultSortOrder
	private String key;

	public enum Status {
		CREATED, SENT
	}

	public TrackRow(CourseKey key) {
		this.key = key.toKeyString();
	}

	// MUST HAVE EMPTY CONSTRUCTOR!
	public TrackRow() {

	}

	// MUST HAVE THE SAME NAME AS THE FIELD NAME
	public String getKey() {
		return key;
	}

	public void setKey(String coursekey) {
		this.key = coursekey;
	}

}
