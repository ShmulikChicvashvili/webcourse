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
import com.technion.coolie.ug.db.tablerows.RegisteredCourseRow;
import com.technion.coolie.ug.db.tablerows.SemestersRow;
import com.technion.coolie.ug.db.tablerows.StudentRow;
import com.technion.coolie.ug.db.tablerows.TrackRow;

public class UGSqlHelper extends OrmLiteSqliteOpenHelper {
	private final String LOG_TAG = "com.technion.coolie.ug.db";
	public static final String DATABASE_NAME = "com.technion.coolie.ug";
	public static final int DATABASE_VERSION = 1;
	public static final String AUTHORITY = "com.technion.coolie.ug";

	// for each table - The DAO objects we use to access the invitations table
	private Dao<CourseRow, String> coursesDao = null;
	private Dao<TrackRow, String> trackRowDao = null;
	private Dao<AcademicEventRow, Long> academicEventDao = null;
	private Dao<AccomplishedCourseRow, Long> accomplishedDao = null;
	private Dao<RegisteredCourseRow, Long> registeredDao = null;
	private Dao<StudentRow, String> studentDao = null;
	private Dao<SemestersRow, Long> semesterDao = null;

	public UGSqlHelper(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
			TableUtils.createTableIfNotExists(connectionSource,
					RegisteredCourseRow.class);
			TableUtils.createTableIfNotExists(connectionSource,
					StudentRow.class);
			TableUtils.createTableIfNotExists(connectionSource,
					SemestersRow.class);
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
			TableUtils.dropTable(connectionSource, TrackRow.class, true);
			TableUtils
					.dropTable(connectionSource, AcademicEventRow.class, true);
			TableUtils.dropTable(connectionSource, AccomplishedCourseRow.class,
					true);
			TableUtils.dropTable(connectionSource, RegisteredCourseRow.class,
					true);
			TableUtils.dropTable(connectionSource, StudentRow.class, true);
			TableUtils.dropTable(connectionSource, SemestersRow.class, true);

			// After we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (final SQLException e) {
			Log.e(LOG_TAG, "onUpgrade(): Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	public Dao<CourseRow, String> getCoursesDao() throws SQLException {
		if (coursesDao == null)
			coursesDao = getDao(CourseRow.class);

		return coursesDao;
	}

	public Dao<TrackRow, String> getTrackingDao() throws SQLException {
		if (trackRowDao == null)
			trackRowDao = getDao(TrackRow.class);

		return trackRowDao;
	}

	public Dao<AcademicEventRow, Long> getAcademicEventsDao()
			throws SQLException {
		if (academicEventDao == null)
			academicEventDao = getDao(AcademicEventRow.class);

		return academicEventDao;
	}

	public Dao<AccomplishedCourseRow, Long> getAccopmlishedCoursesDao()
			throws SQLException {
		if (accomplishedDao == null)
			accomplishedDao = getDao(AccomplishedCourseRow.class);

		return accomplishedDao;
	}

	public Dao<RegisteredCourseRow, Long> getRegisteredCoursesDao()
			throws SQLException {
		if (registeredDao == null)
			registeredDao = getDao(RegisteredCourseRow.class);

		return registeredDao;
	}

	public Dao<StudentRow, String> getStudentInfoDao() throws SQLException {
		if (studentDao == null)
			studentDao = getDao(StudentRow.class);
		return studentDao;
	}

	public Dao<SemestersRow, Long> getSemesterDao() throws SQLException {
		if (semesterDao == null)
			semesterDao = getDao(SemestersRow.class);
		return semesterDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		// nullify all dao
		coursesDao = null;
		accomplishedDao = null;
		academicEventDao = null;
		trackRowDao = null;
		registeredDao = null;
		semesterDao = null;
	}

}
