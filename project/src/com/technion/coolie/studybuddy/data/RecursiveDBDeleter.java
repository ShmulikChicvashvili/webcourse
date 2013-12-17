package com.technion.coolie.studybuddy.data;

import static com.technion.coolie.studybuddy.data.DataStore.getHelper;

import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Semester;
import com.technion.coolie.studybuddy.models.Stats;
import com.technion.coolie.studybuddy.models.StudyItem;
import com.technion.coolie.studybuddy.models.StudyResource;
import com.technion.coolie.studybuddy.models.WorkStats;

public enum RecursiveDBDeleter implements CompositeVisitor
{
	dbDeleter;

	public static RecursiveDBDeleter getInstance()
	{
		return dbDeleter;

	}

	@Override
	public void visit(Course c)
	{
		c.accept(this);
		getHelper().getCourseDao().delete(c);
	}

	@Override
	public void visit(DataStore ds)
	{
		ds.accept(this);
	}

	@Override
	public void visit(Semester s)
	{
		getHelper().getSemesterDao().delete(s);
	}

	@Override
	public void visit(Stats s)
	{
		getHelper().getStatDao().delete(s);
	}

	@Override
	public void visit(StudyItem it)
	{
		getHelper().getStudyItemsDao().delete(it);

	}

	@Override
	public void visit(StudyResource sr)
	{
		sr.accept(this);
		getHelper().getStudyResourceDao().createOrUpdate(sr);
	}

	@Override
	public void visit(WorkStats ws)
	{
		ws.accept(this);
	}
}
