package com.technion.coolie.studybuddy.data;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.types.UuidType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import com.technion.coolie.studybuddy.models.*;

public class SBDatabaseHelper extends OrmLiteSqliteOpenHelper
{

	private static final String				DATABASE_NAME		= "stb_data.db";
	private static final int				DATABASE_VERSION	= 1;
	private Dao<Course, Integer>			courseDao;
	private Dao<Semester, UuidType>			semesterDao;
	private Dao<StudyResource, UuidType>	resourceDao;
	private Dao<StudyItem, UuidType>		itemDao;
	private Dao<Exam, UuidType>				examDao;

	public SBDatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs)
	{
		// TableUtils.createTable(cs, null);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource cs,
			int oldVersion, int newVersion)
	{
	}

	public Dao<Course, Integer> getCourseDao() throws SQLException
	{
		if (courseDao == null)
		{
			courseDao = getDao(Course.class);
		}

		return courseDao;
	}

	public Dao<Semester, UuidType> getSemesterDao() throws SQLException
	{
		if (semesterDao == null)
		{
			semesterDao = getDao(Semester.class);
		}

		return semesterDao;
	}

	public Dao<StudyResource, UuidType> getStudyResourceDao()
			throws SQLException
	{
		if (resourceDao == null)
		{
			resourceDao = getDao(StudyResource.class);
		}

		return resourceDao;
	}

	public Dao<StudyItem, UuidType> getStudyItemsDao() throws SQLException
	{
		if (itemDao == null)
		{
			itemDao = getDao(StudyItem.class);
		}

		return itemDao;
	}

	public Dao<Exam, UuidType> getExamDao() throws SQLException
	{
		if (examDao == null)
		{
			examDao = getDao(Exam.class);
		}

		return examDao;
	}

}
