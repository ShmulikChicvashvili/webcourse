package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.db.UGDBTables;
import com.technion.coolie.ug.db.UGDBTables.TrackingTable;
import com.technion.coolie.ug.model.CourseKey;

@DatabaseTable(tableName = UGDBTables.TrackingTable.TABLENAME)
public class TrackRow {

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
