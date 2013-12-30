package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.model.CourseKey;

@DatabaseTable(tableName = TrackRow.TABLENAME)
public class TrackRow {

	public static final String TABLENAME = "TrackingCoursesTable";

	@DatabaseField(generatedId = true)
	private long key;

	@DatabaseField(useGetSet = true, dataType = DataType.SERIALIZABLE)
	private CourseKey courseKey;

	@DatabaseField(useGetSet = true)
	private String studentId;

	public TrackRow(CourseKey coursekey, String studentId) {
		this.courseKey = coursekey;
		this.studentId = studentId;
	}

	// MUST HAVE EMPTY CONSTRUCTOR!
	public TrackRow() {

	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	// MUST HAVE THE SAME NAME AS THE FIELD NAME
	public CourseKey getCourseKey() {
		return courseKey;
	}

	public void setCourseKey(CourseKey coursekey) {
		this.courseKey = coursekey;
	}

}
