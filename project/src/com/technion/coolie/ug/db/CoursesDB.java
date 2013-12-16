package com.technion.coolie.ug.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CoursesDB extends SQLiteOpenHelper {

	final String COURSES_TABLE_NAME = "UGCourses";

	public CoursesDB(final Context context, final String name,
			final CursorFactory factory, final int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		// bring from ug the tables!
		// create table of courses
		// db.rawQuery("CREATE TABLE " + COURSES_TABLE_NAME + " ("
		// + NoteColumns._ID + " INTEGER PRIMARY KEY,"
		// + NoteColumns.TITLE + " TEXT,"
		// + NoteColumns.NOTE + " TEXT,"
		// + NoteColumns.CREATED_DATE + " INTEGER,"
		// + NoteColumns.MODIFIED_DATE + " INTEGER"
		// + ");", null)
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
			final int newVersion) {
		// drop courses from the removed semester
		onCreate(db);
	}

}
