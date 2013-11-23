package com.technion.coolie.ug.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CoursesDB extends SQLiteOpenHelper {

	
	
	final String COURSES_TABLE_NAME = "UGCourses";
	
	public CoursesDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//bring from ug the tables!
		//create table of courses
//		db.rawQuery("CREATE TABLE " + COURSES_TABLE_NAME + " ("
//                + NoteColumns._ID + " INTEGER PRIMARY KEY,"
//                + NoteColumns.TITLE + " TEXT,"
//                + NoteColumns.NOTE + " TEXT,"
//                + NoteColumns.CREATED_DATE + " INTEGER,"
//                + NoteColumns.MODIFIED_DATE + " INTEGER"
//                + ");", null)
		}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//drop courses from the removed semester
		onCreate(db);
	}
	

}
