package com.technion.coolie.studybuddy.models;

import static com.technion.coolie.studybuddy.utils.Utils.asSortedList;
import static com.technion.coolie.studybuddy.utils.Utils.filter;
import static com.technion.coolie.studybuddy.utils.Utils.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class StudyResource
{
	public static final String	LECTURES		= "Lectures";
	public static final String	TUTORIALS		= "Tutorials";
	public static final String	VIDEO_LECTURES	= "Video Lectures";
	public static final String	VIDEO_TUTORIALS	= "Video Tutorials";
	public static final String	SYLLABUS_TOPICS	= "Syllabus Topics";

	public static void attachItemsToResource(	StudyResource sr,
												List<StudyItem> list)
	{
		sr.setStudyItems(list);
	}

	public static StudyResource createWithItems(String label, Integer num)
	{
		StudyResource sr = new StudyResource();
		sr.setName(label);

		for (int i = 0; i < num; i++)
		{
			sr.addItem(new StudyItem(i + 1));
		}
		return sr;
	}

	public static StudyResource fromItemList(String name, List<String> list)
	{
		StudyResource sr = new StudyResource();
		sr.setName(name);
		sr.addItems(list);
		return sr;
	}

	@DatabaseField(generatedId = true)
	private UUID			id;

	@DatabaseField
	private String			name;

	// @ForeignCollectionField(eager = true)
	private List<StudyItem>	items	= new ArrayList<StudyItem>();

	@DatabaseField
	private Course			course;

	public StudyResource()
	{
	}

	public void addItem(StudyItem item)
	{
		items.add(item);
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

	public int getDoneItemsCount()
	{
		return getItemsDone().size();
	}

	public List<StudyItem> getItemsDone()
	{
		return filter(items, StudyItem.doneMatcher());
	}

	public List<Integer> getItemsDoneIds()
	{
		return asSortedList(map(getItemsDone(), StudyItem.getNumMapper()));
	}

	public List<String> getItemsDoneLabels()
	{

		return asSortedList(map(getItemsDone(), StudyItem.getLabelMapper()));
	}

	public List<StudyItem> getItemsRemaining()
	{
		return filter(items, StudyItem.notDoneMatcher());
	}

	public List<Integer> getItemsRemainingIds()
	{
		return asSortedList(map(getItemsRemaining(), StudyItem.getNumMapper()));
	}

	public List<String> getItemsRemainingLabels()
	{
		return asSortedList(map(getItemsRemaining(), StudyItem.getLabelMapper()));

	}

	public String getName()
	{
		return name;
	}

	public Course getParent()
	{
		return course;
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
			Collection<StudyItem> toRemove = new ArrayList<StudyItem>();

			for (StudyItem i : items)
			{
				if (i.getNum() > newSize)
				{
					toRemove.add(i);
				}
			}

			items.removeAll(toRemove);
		}
		if (newSize > items.size())
		{
			for (int i = items.size() + 1; i <= newSize; ++i)
			{
				items.add(new StudyItem(i));
			}
		}

	}

	public void setParent(Course parent)
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

	private void setName(String name)
	{
		this.name = name;

	}

	private void setStudyItems(List<StudyItem> list)
	{
		items.addAll(list);
	}

	private void toggleDone(int id)
	{
		List<StudyItem> result = filter(items, StudyItem.getNumFilter(id));
		if (result.size() < 1)
			return;
		result.get(0).toggleDone();

	}
}
