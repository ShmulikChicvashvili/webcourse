package com.technion.coolie.letmein.model;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.technion.coolie.letmein.Consts;

public class InvitationDatabaseHelper extends OrmLiteSqliteOpenHelper {
	private final String LOG_TAG = Consts.LOG_PREFIX + getClass().getSimpleName();

	// The DAO objects we use to access the invitations table
	private Dao<Invitation, Long> dao = null;
	private RuntimeExceptionDao<Invitation, Long> runtimeDao = null;

	public InvitationDatabaseHelper(final Context context) {
		super(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase db, final ConnectionSource connectionSource) {
		try {
			Log.i(LOG_TAG, "onCreate(): Creating database");
			TableUtils.createTableIfNotExists(connectionSource, Invitation.class);
		} catch (final SQLException e) {
			Log.e(LOG_TAG, "onCreate(): Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final ConnectionSource connectionSource,
			final int oldVersion, final int newVersion) {
		try {
			Log.i(LOG_TAG, "onUpgrade(): Upgrading database");
			TableUtils.dropTable(connectionSource, Invitation.class, true);
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
	public Dao<Invitation, Long> getDao() throws SQLException {
		if (dao == null)
			dao = getDao(Invitation.class);

		return dao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a
	 * Dao. It will create it or just give the cached value. RuntimeExceptionDao
	 * only throws RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Invitation, Long> getDataDao() {
		if (runtimeDao == null)
			runtimeDao = getRuntimeExceptionDao(Invitation.class);

		return runtimeDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		dao = null;
		runtimeDao = null;
	}
}
