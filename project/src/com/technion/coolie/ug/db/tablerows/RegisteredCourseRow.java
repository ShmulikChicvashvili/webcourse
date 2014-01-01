package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.model.CourseItem;

@DatabaseTable(tableName = RegisteredCourseRow.TABLENAME)
public class RegisteredCourseRow {

	public static final String TABLENAME = "RegisteredCoursesTable";

	@DatabaseField(generatedId = true)
	private long key;

	@DatabaseField(useGetSet = true, dataType = DataType.SERIALIZABLE)
	private CourseItem course;

	@DatabaseField(useGetSet = true)
	private String studentId;

	public RegisteredCourseRow(CourseItem course, String studentId) {
		this.course = course;
		this.studentId = studentId;
	}

	// MUST HAVE EMPTY CONSTRUCTOR!
	public RegisteredCourseRow() {

	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public CourseItem getCourse() {
		return course;
	}

	public void setCourse(CourseItem course) {
		this.course = course;
	}

}
