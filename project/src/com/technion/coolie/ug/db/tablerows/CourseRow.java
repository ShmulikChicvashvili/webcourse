package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.db.UGDBTables;
import com.technion.coolie.ug.model.Course;

@DatabaseTable(tableName = UGDBTables.CourseTable.TABLENAME)
public class CourseRow {

	@DatabaseField(id = true)
	// @DefaultSortOrder
	private String key;

	@DatabaseField(useGetSet = true, dataType = DataType.SERIALIZABLE)
	private Course course;

	public enum Status {
		CREATED, SENT
	}

	public CourseRow(Course course) {
		this.course = course;
		this.key = course.getCourseKey().toKeyString();
	}

	// MUST HAVE EMPTY CONSTRUCTOR!
	public CourseRow() {

	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	// MUST HAVE THE SAME NAME AS THE FIELD NAME
	public String getKey() {
		return key;
	}

	public void setKey(String course) {
		this.key = course;
	}

}
