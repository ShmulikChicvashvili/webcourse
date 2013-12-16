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

public class StudyResource
{
	public static final String		LECTURES	= "Lectures";
	public static final String		TUTORIALS	= "Tutorials";

	@DatabaseField(generatedId = true)
	private UUID					id;
	@DatabaseField
	private String					name;
	@ForeignCollectionField(eager = true)
	private Collection<StudyItem>	items;
	private Course					parent;

	public Course getParent()
	{
		return parent;
	}

	public void setParent(Course parent)
	{
		this.parent = parent;
	}

	public static StudyResource fromItemList(String name, List<String> list)
	{
		StudyResource sr = new StudyResource();
		sr.setName(name);
		sr.addItems(list);
		return sr;
	}

	private void allocateItemsCollection()
	{
		if (null == items)
		{
			items = new ArrayList<StudyItem>();
		}
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

	public void addItem(StudyItem item)
	{
		if (null == items)
		{
			allocateItemsCollection();
		}

		items.add(item);
	}

	public StudyResource()
	{
	}

	private void addItems(List<String> list)
	{
		if (null == items)
		{
			allocateItemsCollection();
		}

		int i = items.size();
		for (String str : list)
		{
			items.add(new StudyItem(++i, str));
		}
	}

	public List<StudyItem> getItemsDone()
	{
		if (null == items)
		{
			allocateItemsCollection();
		}

		return filter(items, StudyItem.doneMatcher());
	}

	public List<StudyItem> getItemsRemaining()
	{
		if (null == items)
		{
			allocateItemsCollection();
		}
		return filter(items, StudyItem.notDoneMatcher());
	}

	public int getDoneItemsCount()
	{
		return getItemsDone().size();
	}

	public int getRemainingItemsCount()
	{
		return getItemsRemaining().size();
	}

	public List<Integer> getItemsDoneIds()
	{
		return asSortedList(map(getItemsDone(), StudyItem.getNumMapper()));
	}

	public List<String> getItemsDoneLabels()
	{

		return asSortedList(map(getItemsDone(), StudyItem.getLabelMapper()));
	}

	public List<Integer> getItemsRemainingIds()
	{
		return asSortedList(map(getItemsRemaining(), StudyItem.getNumMapper()));
	}

	public List<String> getItemsRemainingLabels()
	{
		return asSortedList(map(getItemsRemaining(), StudyItem.getLabelMapper()));

	}

	public int getTotalItemCount()
	{
		if (null == items)
		{
			allocateItemsCollection();
		}
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

	private void setName(String name)
	{
		this.name = name;

	}

	private void toggleDone(int id)
	{
		if (null == items)
		{
			allocateItemsCollection();
		}

		List<StudyItem> result = filter(items, StudyItem.getNumFilter(id));
		if (result.size() < 1)
			return;
		result.get(0).toggleDone();

	}

	public List<StudyItem> getAllItems()
	{
		if (null == items)
		{
			allocateItemsCollection();
		}

		List<StudyItem> list = new ArrayList<StudyItem>();
		list.addAll(items);
		Collections.sort(list);
		return list;
	}

	public String getName()
	{
		return name;
	}

	public void resizeTo(int newSize)
	{
		if (null == items)
		{
			allocateItemsCollection();
		}

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
}
