package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.db.UGDBTables;
import com.technion.coolie.ug.model.AccomplishedCourse;

@DatabaseTable(tableName = UGDBTables.AccomplishedTable.TABLENAME)
public class AccomplishedCourseRow {

	@DatabaseField(id = true, generatedId = true)
	// @DefaultSortOrder
	private String key;

	@DatabaseField(useGetSet = true, dataType = DataType.SERIALIZABLE)
	private AccomplishedCourse course;

	public AccomplishedCourseRow(AccomplishedCourse course) {
		this.course = course;
	}

	// MUST HAVE EMPTY CONSTRUCTOR!
	public AccomplishedCourseRow() {

	}

	public AccomplishedCourse getCourse() {
		return course;
	}

	public void setCourse(AccomplishedCourse course) {
		this.course = course;
	}

	// MUST HAVE THE SAME NAME AS THE FIELD NAME
	public String getKey() {
		return key;
	}

	public void setKey(String coursekey) {
		this.key = coursekey;
	}

}
