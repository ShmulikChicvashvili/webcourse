package com.technion.coolie.studybuddy.models;

import static com.technion.coolie.studybuddy.utils.Utils.randomInt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.studybuddy.data.CompositeVisitor;

@DatabaseTable
public class Course implements Comparable<Course>, CompositeElement
{

	// private List<Exam> exams = new ArrayList<Exam>();

	@DatabaseField(id = true)
	private String				id;

	@DatabaseField
	private String				name;

	private List<StudyResource>	trackedResouces	= new ArrayList<StudyResource>();

	public static void addStudyResources(Course c, List<StudyResource> resources)
	{
		c.addStudyResources(resources);
	}

	private static String radomId()
	{
		return String.valueOf(randomInt(999999));

	}

	public Course()
	{
		id = radomId();
		name = String.valueOf(id);
	}

	public Course(int id, String name)
	{
		this.id = String.valueOf(id);
		this.name = name;
	}

	public Course(String id, String name)
	{
		this.id = id;
		this.name = name;
	}

	// public List<Exam> getExams()
	// {
	// return exams;
	// }
	//
	// public void addExam(Exam e)
	// {
	// exams.add(e);
	// }
	//
	// public void addExams(Exam... exams)
	// {
	// this.exams.addAll(Arrays.asList(exams));
	// }
	//
	// public void addExams(Collection<Exam> exams)
	// {
	// this.exams.addAll(exams);
	// }

	@Override
	public void accept(CompositeVisitor cv)
	{
		for (StudyResource r : trackedResouces)
		{
			cv.visit(r);
		}

	}

	public void addStudyResource(StudyResource r)
	{
		trackedResouces.add(r);
		r.setCourse(this);
	}

	@Override
	public int compareTo(Course another)
	{
		return another.getNumStudyItemsRemaining()
						- getNumStudyItemsRemaining();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Collection<StudyResource> getAllStudyResources()
	{
		return trackedResouces;
	}

	public String getId()
	{
		return id;
	}

	public String getIdAsString()
	{
		return String.valueOf(id);
	}

	public String getName()
	{
		return name;
	}

	public int getNumStudyItemsRemaining()
	{
		int total = 0;
		for (StudyResource sr : trackedResouces)
		{
			total += sr.getRemainingItemsCount();
		}
		return total;

	}

	public Map<String, Integer> getProgressMap()
	{
		Map<String, Integer> map = new HashMap<String, Integer>(
						trackedResouces.size());

		for (StudyResource r : trackedResouces)
		{
			map.put(r.getName(), r.getDoneItemsCount());
		}
		return map;
	}

	public String getResourceName(int itemPosition)
	{
		if (itemPosition >= trackedResouces.size())
			return "";

		return trackedResouces.get(itemPosition).getName();
	}

	public int getResourceTotalItemCount(String name)
	{
		if (null == getResourceByName(name))
			return 0;

		return getResourceByName(name).getTotalItemCount();
	}

	public List<String> getStudyItemsDone(String resourceName)
	{
		if (null == getResourceByName(resourceName))
			return Collections.<String> emptyList();

		return getResourceByName(resourceName).getItemsDoneLabels();
	}

	public List<String> getStudyItemsLabels(String resourceName)
	{
		if (null == getResourceByName(resourceName))
			return Collections.<String> emptyList();

		return getResourceByName(resourceName).getAllItemsLabels();
	}

	public List<String> getStudyItemsRemaining(String resourceName)
	{
		if (null == getResourceByName(resourceName))
			return Collections.<String> emptyList();

		return getResourceByName(resourceName).getItemsRemainingLabels();
	}

	public int getStudyItemsTotal()
	{
		int sum = 0;
		for (StudyResource sr : trackedResouces)
		{
			sum += sr.getTotalItemCount();
		}
		return sum;
	}

	@Override
	public int hashCode()
	{
		return id.hashCode();
	}

	public void initResources()
	{
		trackedResouces = new ArrayList<StudyResource>();
		;
	}

	public void resizeStudyResource(String name, int newSize)
	{
		StudyResource r = getResourceByName(name);

		if (null == r)
		{
			addStudyResource(StudyResource.createWithItems(name, newSize));
			return;
		}
		r.resizeTo(newSize);
	}

	public void setID(String newCourseId)
	{
		id = newCourseId;
	}

	public void setName(String courseName)
	{
		name = courseName;
	}

	@Override
	public String toString()
	{
		return String.valueOf(id) + " " + name;
	}

	private void addStudyResources(Collection<StudyResource> list)
	{
		trackedResouces.addAll(list);
		for (StudyResource studyResource : list)
		{
			studyResource.setCourse(this);
		}
	}

	private StudyResource getResourceByName(String name)
	{

		for (StudyResource sr : trackedResouces)
		{
			if (sr.getName() == name)
				return sr;
		}

		return null;
	}

	public void toggleTask(String resourceName, int position)
	{
		StudyResource sr = getResourceByName(resourceName);
		sr.toggleTask(position);
	}

	public boolean isTaskDone(String resourceName, int position)
	{
		StudyResource sr = getResourceByName(resourceName);

		if (sr == null)
			return false;

		return sr.isTaskDone(position);
	}
}
