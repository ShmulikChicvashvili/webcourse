package com.technion.coolie.ug.db;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;

@DatabaseTable(tableName = UGContract.Course.TABLENAME)
//@DefaultContentUri(authority = Contract.AUTHORITY, path = Contract.Invitation.CONTENT_URI_PATH)
//@DefaultContentMimeTypeVnd(name = Contract.Invitation.MIMETYPE_NAME, type = Contract.Invitation.MIMETYPE_TYPE)
public class CourseRow {

	@DatabaseField( id = true/*,useGetSet = true*/)
//	@DefaultSortOrder
	private String key;

	@DatabaseField(useGetSet = true,dataType = DataType.SERIALIZABLE)
	private Course course;

	public enum Status {
		CREATED, SENT
	}

	public CourseRow(Course course,CourseKey key) {
		this.course = course;
		this.key = key.toKeyString();
	}
	
	//MUST HAVE EMPTY CONSTRUCTOR!
	public CourseRow()
	{
		
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	//MUST HAVE THE SAME NAME AS THE FIELD NAME
	public String getKey() {
		return key;
	}

	public void setKey(String course) {
		this.key = course;
	}
	

}
