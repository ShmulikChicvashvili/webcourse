package com.technion.coolie.ug.db.tablerows;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.model.Semester;

@DatabaseTable(tableName = SemestersRow.TABLENAME)
public class SemestersRow {

	public static final String TABLENAME = "SemesterTable";

	@DatabaseField(generatedId = true)
	private Long id;

	@DatabaseField(useGetSet = true, dataType = DataType.SERIALIZABLE)
	private Semester[] semesters;

	public SemestersRow(Semester[] semesters) {
		this.semesters = semesters;
	}

	// MUST HAVE EMPTY CONSTRUCTOR!
	public SemestersRow() {
	}

	public Semester[] getSemesters() {
		return semesters;
	}

	public void setSemesters(Semester[] semesters) {
		this.semesters = semesters;
	}

}
