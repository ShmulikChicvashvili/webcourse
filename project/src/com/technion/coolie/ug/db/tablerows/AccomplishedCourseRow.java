package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.model.AccomplishedCourse;

@DatabaseTable(tableName = AccomplishedCourseRow.TABLENAME)
public class AccomplishedCourseRow {

	public static final String TABLENAME = "AccomplishedCoursesTable";

	@DatabaseField(generatedId = true)
	// @DefaultSortOrder
	private long key;

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
	public long getKey() {
		return key;
	}

	public void setKey(long coursekey) {
		this.key = coursekey;
	}

}
