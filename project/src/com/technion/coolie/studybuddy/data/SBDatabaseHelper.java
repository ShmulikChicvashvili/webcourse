package com.technion.coolie.studybuddy.data;

import java.sql.SQLException;
import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.field.types.DateType;
import com.j256.ormlite.field.types.UuidType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Semester;
import com.technion.coolie.studybuddy.models.DailyStatistic;
import com.technion.coolie.studybuddy.models.StudyItem;
import com.technion.coolie.studybuddy.models.StudyResource;
import com.technion.coolie.studybuddy.models.WorkStats;

public class SBDatabaseHelper extends OrmLiteSqliteOpenHelper
{

	private static final String								DATABASE_NAME		= "stb_data.db";
	private static final int								DATABASE_VERSION	= 1;
	private RuntimeExceptionDao<Course, String>				courseDao;
	private RuntimeExceptionDao<Semester, UuidType>			semesterDao;
	private RuntimeExceptionDao<StudyResource, UuidType>	resourceDao;
	private RuntimeExceptionDao<StudyItem, UuidType>		itemDao;
	private RuntimeExceptionDao<DailyStatistic, UuidType>			statDao;

	// private Dao<Exam, UuidType> examDao;

	public SBDatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public RuntimeExceptionDao<Course, String> getCourseDao()
	{
		if (courseDao == null)
		{
			courseDao = getRuntimeExceptionDao(Course.class);
		}

		return courseDao;
	}

	// public Dao<Exam, UuidType> getExamDao() throws SQLException
	// {
	// if (examDao == null)
	// {
	// examDao = getDao(Exam.class);
	// }
	//
	// return examDao;
	// }

	public RuntimeExceptionDao<Semester, UuidType> getSemesterDao()
	{
		if (semesterDao == null)
		{
			semesterDao = getRuntimeExceptionDao(Semester.class);
		}

		return semesterDao;
	}

	public RuntimeExceptionDao<DailyStatistic, UuidType> getStatDao()
	{
		if (statDao == null)
		{
			statDao = getRuntimeExceptionDao(DailyStatistic.class);
		}

		return statDao;
	}

	public RuntimeExceptionDao<StudyItem, UuidType> getStudyItemsDao()
	{
		if (itemDao == null)
		{
			itemDao = getRuntimeExceptionDao(StudyItem.class);
		}

		return itemDao;
	}

	public RuntimeExceptionDao<StudyResource, UuidType> getStudyResourceDao()
	{
		if (resourceDao == null)
		{
			resourceDao = getRuntimeExceptionDao(StudyResource.class);
		}

		return resourceDao;
	}

	public RuntimeExceptionDao<WorkStats, String> getWorkStatsDao()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs)
	{
		try
		{
			TableUtils.createTable(cs, Course.class);
			TableUtils.createTable(cs, StudyResource.class);
			TableUtils.createTable(cs, StudyItem.class);
			TableUtils.createTable(cs, DailyStatistic.class);
			TableUtils.createTable(cs, Semester.class);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(	SQLiteDatabase db,
							ConnectionSource cs,
							int oldVersion,
							int newVersion)
	{

	}

	@Override
	public void close()
	{
		super.close();
		courseDao = null;
		resourceDao = null;
		itemDao = null;
		semesterDao = null;
		statDao = null;
	}
}
