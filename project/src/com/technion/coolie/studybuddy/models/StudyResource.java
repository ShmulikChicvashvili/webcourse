package com.technion.coolie.studybuddy.models;

import static com.technion.coolie.studybuddy.utils.Utils.filter;
import static com.technion.coolie.studybuddy.utils.Utils.findFirst;
import static com.technion.coolie.studybuddy.utils.Utils.map;
import static com.technion.coolie.studybuddy.utils.Utils.sorted;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.studybuddy.data.CompositeVisitor;

@DatabaseTable
public class StudyResource implements CompositeElement
{
	public static final String	LECTURES	= "Lectures";
	public static final String	TUTORIALS	= "Tutorials";
	// public static final String VIDEO_LECTURES = "Video Lectures";
	// public static final String VIDEO_TUTORIALS = "Video Tutorials";
	// public static final String SYLLABUS_TOPICS = "Syllabus Topics";
	public static final String	COURSE_ID	= "course_id";

	@DatabaseField(generatedId = true)
	private UUID				id;

	@DatabaseField
	private String				name;

	private List<StudyItem>		items		= new ArrayList<StudyItem>();

	@DatabaseField(foreign = true, canBeNull = false, index = true, columnName = COURSE_ID)
	private Course				course;

	public static void attachItemsList(StudyResource sr, List<StudyItem> list)
	{
		sr.setStudyItems(list);
	}

	public static StudyResource createWithItems(String label, Integer num)
	{
		StudyResource sr = new StudyResource();
		sr.setName(label);

		sr.addRange(1, num);
		return sr;
	}

	public static StudyResource fromItemList(String name, List<String> list)
	{
		StudyResource sr = new StudyResource();
		sr.setName(name);
		sr.addItems(list);
		return sr;
	}

	public StudyResource()
	{
	}

	@Override
	public void accept(CompositeVisitor cv)
	{
		for (StudyItem item : items)
		{
			cv.visit(item);
		}

	}

	public void addItem(StudyItem item)
	{
		items.add(item);
		item.setStudyResource(this);
	}

	public List<String> getAllItemsLabels()
	{

		List<String> list = new ArrayList<String>(items.size());
		for (StudyItem item : items)
		{
			list.add(item.getLabel());
		}
		return list;
	}

	public Course getCourse()
	{
		return course;
	}

	public int getDoneItemsCount()
	{
		return getItemsDone().size();
	}

	public List<StudyItem> getItemsDone()
	{
		return filter(items, StudyItem.doneMatcher);
	}

	public List<Integer> getItemsDoneIds()
	{
		return (map(sorted(getItemsDone()), StudyItem.mapToNum));
	}

	public List<String> getItemsDoneLabels()
	{

		return (map(sorted(getItemsDone()), StudyItem.mapToLabel));
	}

	public List<StudyItem> getItemsRemaining()
	{
		return filter(items, StudyItem.notDoneMathcer);
	}

	public List<Integer> getItemsRemainingIds()
	{
		return (map((sorted(getItemsRemaining())), StudyItem.mapToNum));
	}

	public List<String> getItemsRemainingLabels()
	{
		return (map(sorted(getItemsRemaining()), StudyItem.mapToLabel));

	}

	public String getName()
	{
		return name;
	}

	public int getRemainingItemsCount()
	{
		return getItemsRemaining().size();
	}

	public int getTotalItemCount()
	{
		return items.size();
	}

	public void markDone(int id)
	{
		toggleDone(id);
	}

	public void markUnDone(int id)
	{
		toggleDone(id);
	}

	public void resizeTo(int newSize)
	{

		if (newSize < items.size())
		{
			items.removeAll(filter(items, StudyItem.getMaxIdFilter(newSize)));
		}
		if (newSize > items.size())
		{
			addRange(items.size() + 1, newSize - items.size());
		}

	}

	public void setCourse(Course parent)
	{
		course = parent;
	}

	private void addItems(List<String> list)
	{
		int i = items.size();
		for (String str : list)
		{
			items.add(new StudyItem(++i, str));
		}
	}

	private void addRange(int from, int amount)
	{
		for (int i = from; i < from + amount; i++)
		{
			addItem(new StudyItem(i));
		}
	}

	private void setName(String name)
	{
		this.name = name;

	}

	private void setStudyItems(List<StudyItem> list)
	{
		items.addAll(list);
		for (StudyItem studyItem : list)
		{
			studyItem.setStudyResource(this);
		}
	}

	private void toggleDone(int id)
	{
		StudyItem item = findFirst(items, StudyItem.getNumFilter(id));
		if (item == null)
			return;
		item.toggleDone();
	}

	public boolean isTaskDone(int position)
	{
		if (position > items.size())
			return false;
		return items.get(position).isDone();
	}
}
