package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.model.Student;

@DatabaseTable(tableName = StudentRow.TABLENAME)
public class StudentRow {

	public static final String TABLENAME = "StudentInfoTable";

	@DatabaseField(id = true)
	private String id;

	@DatabaseField(useGetSet = true, dataType = DataType.SERIALIZABLE)
	private Student student;

	public StudentRow(Student coursekey, String studentId) {
		this.student = coursekey;
		this.id = studentId;
	}

	// MUST HAVE EMPTY CONSTRUCTOR!
	public StudentRow() {
	}

	public String getId() {
		return id;
	}

	public void setId(String studentId) {
		this.id = studentId;
	}

	// MUST HAVE THE SAME NAME AS THE FIELD NAME
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
