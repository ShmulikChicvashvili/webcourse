package com.technion.coolie.studybuddy.models;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.studybuddy.utils.Utils.Mapper;
import com.technion.coolie.studybuddy.utils.Utils.Matcher;

@DatabaseTable
public class StudyItem implements Comparable<StudyItem>
{

	@DatabaseField(generatedId = true)
	private UUID								id;

	@DatabaseField
	private int									num;

	@DatabaseField
	private String								label;

	@DatabaseField
	private boolean								done;

	@DatabaseField(foreign = true, canBeNull = false, index = true)
	private StudyResource						studyResource;

	public static Matcher<StudyItem>			doneMatcher		= new Matcher<StudyItem>()
																{

																	@Override
																	public boolean matches(StudyItem item)
																	{
																		return item.isDone();
																	}

																};

	public static Matcher<StudyItem>			notDoneMathcer	= new Matcher<StudyItem>()
																{

																	@Override
																	public boolean matches(StudyItem item)
																	{
																		return !item.isDone();
																	}

																};

	public static Mapper<StudyItem, Integer>	mapToNum		= new Mapper<StudyItem, Integer>()
																{

																	@Override
																	public Integer map(StudyItem item)
																	{
																		return item.getNum();
																	}

																};

	public static Mapper<StudyItem, String>		mapToLabel		= new Mapper<StudyItem, String>()
																{

																	@Override
																	public String map(StudyItem item)
																	{
																		return item.getLabel();
																	}

																};

	public static Matcher<StudyItem> getMaxIdFilter(final int max)
	{
		return new Matcher<StudyItem>()
		{

			@Override
			public boolean matches(StudyItem item)
			{
				return item.getNum() > max;
			}
		};
	}

	public static Matcher<StudyItem> getNumFilter(final int num)
	{
		return new Matcher<StudyItem>()
		{

			@Override
			public boolean matches(StudyItem item)
			{
				return item.getNum() == num;
			}
		};
	}

	public StudyItem()
	{

	}

	public StudyItem(int num)
	{
		this(num, String.valueOf(num), false);
	}

	public StudyItem(int num, String label)
	{
		this(num, label, false);
	}

	public StudyItem(int num, String label, boolean done)
	{
		this.num = num;
		this.label = label;
		this.done = done;
	}

	@Override
	public int compareTo(StudyItem another)
	{
		return num - another.num;
	}

	public String getLabel()
	{
		return label;
	}

	public int getNum()
	{
		return num;
	}

	public boolean isDone()
	{
		return (done == true);
	}

	public void markDone()
	{
		done = true;
	}

	public void markUndone()
	{
		done = false;
	}

	public void toggleDone()
	{
		done = !done;
	}
}
