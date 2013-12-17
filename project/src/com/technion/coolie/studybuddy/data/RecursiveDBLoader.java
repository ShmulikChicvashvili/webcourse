package com.technion.coolie.studybuddy.data;

import static com.technion.coolie.studybuddy.data.DataStore.getHelper;

import java.sql.SQLException;
import java.util.List;

import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Semester;
import com.technion.coolie.studybuddy.models.Stat;
import com.technion.coolie.studybuddy.models.StudyItem;
import com.technion.coolie.studybuddy.models.StudyResource;
import com.technion.coolie.studybuddy.models.WorkStats;

public enum RecursiveDBLoader implements CompositeVisitor
{
	dbLoader;

	public static RecursiveDBLoader getInstance()
	{
		return dbLoader;
	}

	@Override
	public void visit(Course c)
	{
		try
		{
			List<StudyResource> results = getHelper().getStudyResourceDao()
							.queryBuilder().where()
							.eq(StudyResource.COURSE_ID, c).query();

			Course.addStudyResources(c, results);

			for (StudyResource r : results)
			{
				r.setCourse(c);
				visit(r);
			}

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void visit(DataStore ds)
	{
		List<Course> results = getHelper().getCourseDao().queryForAll();
		for (Course c : results)
		{
			ds.addCourse(c);
			visit(c);
		}

	}

	@Override
	public void visit(Semester s)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Stat s)
	{
		return;
	}

	@Override
	public void visit(StudyItem it)
	{
		return;

	}

	@Override
	public void visit(StudyResource sr)
	{
		try
		{
			List<StudyItem> results = getHelper().getStudyItemsDao()
							.queryBuilder().where()
							.eq(StudyItem.STUDYRESOURCE_ID, sr).query();

			StudyResource.attachItemsList(sr, results);

			for (StudyItem s : results)
			{
				s.setStudyResource(sr);
			}

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void visit(WorkStats ws)
	{
		List<Stat> results = getHelper().getStatDao().queryForAll();

		WorkStats.loadStats(results);

	}
}
