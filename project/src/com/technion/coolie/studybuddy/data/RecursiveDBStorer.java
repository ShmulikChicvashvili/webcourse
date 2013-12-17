package com.technion.coolie.studybuddy.data;

import static com.technion.coolie.studybuddy.data.DataStore.getHelper;

import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Semester;
import com.technion.coolie.studybuddy.models.Stat;
import com.technion.coolie.studybuddy.models.StudyItem;
import com.technion.coolie.studybuddy.models.StudyResource;
import com.technion.coolie.studybuddy.models.WorkStats;

public enum RecursiveDBStorer implements CompositeVisitor
{
	dbStorer;

	public static RecursiveDBStorer getInstance()
	{
		return dbStorer;

	}

	@Override
	public void visit(Course c)
	{
		getHelper().getCourseDao().createOrUpdate(c);
		c.accept(this);
	}

	@Override
	public void visit(DataStore ds)
	{
		ds.accept(this);
	}

	@Override
	public void visit(Semester s)
	{
		getHelper().getSemesterDao().createOrUpdate(s);
	}

	@Override
	public void visit(Stat s)
	{
		getHelper().getStatDao().createOrUpdate(s);
	}

	@Override
	public void visit(StudyItem it)
	{
		getHelper().getStudyItemsDao().createOrUpdate(it);

	}

	@Override
	public void visit(StudyResource sr)
	{
		getHelper().getStudyResourceDao().createOrUpdate(sr);
		sr.accept(this);
	}

	@Override
	public void visit(WorkStats ws)
	{
		ws.accept(this);
	}
}
