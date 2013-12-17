package com.technion.coolie.studybuddy.data;

import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Semester;
import com.technion.coolie.studybuddy.models.Stats;
import com.technion.coolie.studybuddy.models.StudyItem;
import com.technion.coolie.studybuddy.models.StudyResource;
import com.technion.coolie.studybuddy.models.WorkStats;

public interface CompositeVisitor
{
	public void visit(Course c);

	public void visit(DataStore ds);

	public void visit(Semester s);

	public void visit(Stats s);

	public void visit(StudyItem it);

	public void visit(StudyResource sr);

	public void visit(WorkStats ws);

}
