package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.model.AccomplishedCourse;

@DatabaseTable(tableName = AccomplishedCourseRow.TABLENAME)
public class AccomplishedCourseRow {

	public static final String TABLENAME = "AccomplishedCoursesTable";

	@DatabaseField(generatedId = true)
	private long key;

	@DatabaseField(useGetSet = true, dataType = DataType.SERIALIZABLE)
	private AccomplishedCourse course;

	@DatabaseField(useGetSet = true)
	private String studentId;

	public AccomplishedCourseRow(AccomplishedCourse course, String studentId) {
		this.course = course;
		this.studentId = studentId;
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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

}
