package com.technion.coolie.ug.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.technion.coolie.ug.db.tablerows.AcademicEventRow;
import com.technion.coolie.ug.db.tablerows.AccomplishedCourseRow;
import com.technion.coolie.ug.db.tablerows.CourseRow;
import com.technion.coolie.ug.db.tablerows.TrackRow;

public class UGSqlHelper extends OrmLiteSqliteOpenHelper {
	private final String LOG_TAG = "com.technion.coolie.ug.db";

	// for each table - The DAO objects we use to access the invitations table
	private Dao<CourseRow, String> coursesDao = null;

	public UGSqlHelper(final Context context) {
		super(context, UGDBTables.DATABASE_NAME, null,
				UGDBTables.DATABASE_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase db,
			final ConnectionSource connectionSource) {
		try {
			Log.i(LOG_TAG, "onCreate(): Creating database");
			// create all tables
			TableUtils
					.createTableIfNotExists(connectionSource, CourseRow.class);
			TableUtils.createTableIfNotExists(connectionSource, TrackRow.class);
			TableUtils.createTableIfNotExists(connectionSource,
					AcademicEventRow.class);
			TableUtils.createTableIfNotExists(connectionSource,
					AccomplishedCourseRow.class);
		} catch (final SQLException e) {
			Log.e(LOG_TAG, "onCreate(): Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db,
			final ConnectionSource connectionSource, final int oldVersion,
			final int newVersion) {
		try {
			Log.i(LOG_TAG, "onUpgrade(): Upgrading database");
			// drop all tables
			TableUtils.dropTable(connectionSource, CourseRow.class, true);

			// After we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (final SQLException e) {
			Log.e(LOG_TAG, "onUpgrade(): Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the Database Access Object (DAO). It will create it or just give
	 * the cached value.
	 * 
	 * @throws SQLException
	 */
	public Dao<CourseRow, String> getCoursesDao() throws SQLException {
		if (coursesDao == null)
			coursesDao = getDao(CourseRow.class);

		return coursesDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		// nullify all dao TODO
		coursesDao = null;
	}
}
