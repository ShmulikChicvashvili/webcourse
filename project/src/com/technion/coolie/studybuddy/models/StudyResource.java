package com.technion.coolie.studybuddy.models;

import static com.technion.coolie.studybuddy.models.Semester.WEEKS_IN_SEMESTER;
import static com.technion.coolie.studybuddy.utils.Utils.asSortedList;
import static com.technion.coolie.studybuddy.utils.Utils.filter;
import static com.technion.coolie.studybuddy.utils.Utils.map;

import java.util.ArrayList;
import java.util.List;

public class StudyResource
{
	private static final String	DEFAULT_NAME	= "DEFAULT";
	public static final String	LECTURES		= "Lectures";
	public static final String	TUTORIALS		= "Tutorials";

	private String				name;
	private List<StudyItem>		items			= new ArrayList<StudyItem>();

	public static StudyResource fromItemList(List<String> list)
	{
		return fromItemList(DEFAULT_NAME, list);
	}

	public static StudyResource fromItemList(String name, List<String> list)
	{
		StudyResource sr = new StudyResource();
		sr.setName(name);
		sr.addItems(list);
		return sr;
	}

	public static StudyResource createWithItems(Integer num)
	{
		return createWithItems(DEFAULT_NAME, num);
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

	public static StudyResource createWithDefaultItems()
	{
		return createWithItems(WEEKS_IN_SEMESTER);
	}

	public void addItem(StudyItem item)
	{
		items.add(item);
	}

	public StudyResource()
	{
	}

	private void addItems(List<String> list)
	{
		int i = items.size();
		for (String str : list)
		{
			items.add(new StudyItem(++i, str));
		}
	}

	public List<StudyItem> getItemsDone()
	{
		return filter(items, StudyItem.doneMatcher());
	}

	public List<StudyItem> getItemsRemaining()
	{
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
		List<StudyItem> result = filter(items, StudyItem.getNumFilter(id));
		if (result.size() < 1)
			return;
		result.get(0).toggleDone();

	}

	public List<StudyItem> getAllItems()
	{
		return items;
	}

	public String getName()
	{
		return name;
	}
}
