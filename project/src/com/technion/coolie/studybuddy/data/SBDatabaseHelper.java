package com.technion.coolie.studybuddy.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class SBDatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "stb_data.db";
	private static final int DATABASE_VERSION = 1;

	public SBDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
		// TableUtils.createTable(cs, null);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource cs,
			int oldVersion, int newVersion) {
	}

}
