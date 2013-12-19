package com.technion.coolie.studybuddy.models;

import static com.technion.coolie.studybuddy.models.DailyStatistic.setMidnight;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.field.ForeignCollectionField;
import com.technion.coolie.studybuddy.data.CompositeVisitor;

public enum WorkStats implements CompositeElement
{
	workStats;

	private class DaysRange implements Iterable<Date>
	{

		private final Date	startDate;
		private final Date	endDate;

		public DaysRange(Date startDate, Date endDate)
		{
			this.startDate = (startDate);
			this.endDate = (endDate);
		}

		@Override
		public Iterator<Date> iterator()
		{
			return new Iterator<Date>()
			{
				private final Calendar	cal;
				{
					cal = new GregorianCalendar();
					cal.setTime(startDate);
				}

				@Override
				public boolean hasNext()
				{
					return !cal.getTime().after(endDate);
				}

				@Override
				public Date next()
				{
					Date date = cal.getTime();
					cal.add(Calendar.DAY_OF_MONTH, 1);
					return date;
				}

				@Override
				public void remove()
				{
					throw new UnsupportedOperationException();

				}

			};
		}
	}

	private static Map<Date, DailyStatistic>	statsMap	= new HashMap<Date, DailyStatistic>();

	public static WorkStats getInstance()
	{
		return workStats;
	}

	public void loadStats(List<DailyStatistic> stats)
	{
		for (DailyStatistic s : stats)
		{
			statsMap.put(s.getDate(), s);
		}
	}

	@Override
	public void accept(CompositeVisitor cv)
	{
		for (DailyStatistic s : statsMap.values())
		{
			cv.visit(s);
		}

	}

	public void decreaseDoneForDate(Date d)
	{
		Date n = setMidnight(d);
		if (!statsMap.containsKey(n))
			return;

		statsMap.get(n).decreaseDone();

	}

	public int getStatsForDate(Date date)
	{
		Date n = setMidnight(date);
		if (statsMap.containsKey(n))
			return statsMap.get(n).getAmountDone();

		return 0;
	}

	public Integer[] getStatsForRange(Date d1, Date d2)
	{
		Date n1 = setMidnight(d1);
		Date n2 = setMidnight(d2);
		List<Integer> values = new ArrayList<Integer>();

		for (Date d : daysInRange(n1, n2))
		{
			if (statsMap.containsKey(d))
			{
				values.add(statsMap.get(d).getAmountDone());
			} else
			{
				values.add(0);
			}

		}

		return values.toArray(new Integer[values.size()]);
	}

	public Integer[] getStatsLastXDays(Date today, int days)
	{
		Date last = setMidnight(today);

		Calendar cal = Calendar.getInstance();
		cal.setTime(last);
		cal.add(Calendar.DAY_OF_MONTH, 1 - days);

		Date first = cal.getTime();

		return getStatsForRange(first, last);

	}

	public void increaseDoneForDate(Date d)
	{
		DailyStatistic s = findOrAllocateStat(d);
		s.addDone();
	}

	private DailyStatistic findOrAllocateStat(Date d)
	{
		Date n = setMidnight(d);
		if (statsMap.containsKey(n))
			return statsMap.get(n);

		DailyStatistic s = new DailyStatistic(d);
		statsMap.put(s.getDate(), s);
		return s;
	}

	private Iterable<Date> daysInRange(Date n1, Date n2)
	{
		return new DaysRange(n1, n2);
	}

	public static void clear()
	{
		statsMap.clear();

	}

	public void insert(DailyStatistic dailyStatistic)
	{
		statsMap.put(dailyStatistic.getDate(), dailyStatistic);
		dailyStatistic.setParent(this);

	}
}
