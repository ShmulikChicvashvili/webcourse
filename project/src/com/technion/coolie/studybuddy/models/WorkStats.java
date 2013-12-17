package com.technion.coolie.studybuddy.models;

import static com.technion.coolie.studybuddy.models.Stat.nullifyDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WorkStats
{

	class DateRange implements Iterable<Date>
	{

		private final Date	d1;
		private final Date	d2;

		public DateRange(Date d1, Date d2)
		{
			this.d1 = (d1);
			this.d2 = (d2);
		}

		@Override
		public Iterator<Date> iterator()
		{
			return new Iterator<Date>()
			{
				private final Calendar	cal;
				{
					cal = new GregorianCalendar();
					cal.setTime(d1);
				}

				@Override
				public boolean hasNext()
				{
					return !cal.getTime().after(d2);
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

	Map<Date, Stat>	stats	= new HashMap<Date, Stat>();

	public void decreaseDoneForDate(Date d)
	{
		Date n = nullifyDate(d);
		if (!stats.containsKey(n))
			return;

		stats.get(n).decreaseDone();

	}

	public int getStatsForDate(Date date)
	{
		Date n = nullifyDate(date);
		if (stats.containsKey(n))
			return stats.get(n).getAmountDone();

		return 0;
	}

	public Integer[] getStatsForRange(Date d1, Date d2)
	{
		Date n1 = nullifyDate(d1);
		Date n2 = nullifyDate(d2);
		List<Integer> values = new ArrayList<Integer>();

		for (Date d : range(n1, n2))
		{
			if (stats.containsKey(d))
			{
				values.add(stats.get(d).getAmountDone());
			} else
			{
				values.add(0);
			}

		}

		return values.toArray(new Integer[values.size()]);
	}

	public Integer[] getStatsLastXDays(Date today, int days)
	{
		Date last = nullifyDate(today);

		Calendar cal = Calendar.getInstance();
		cal.setTime(last);
		cal.add(Calendar.DAY_OF_MONTH, 1 - days);

		Date first = cal.getTime();

		return getStatsForRange(first, last);

	}

	public void increaseDoneForDate(Date d)
	{
		Stat s = findOrAllocateStat(d);
		s.addDone();
	}

	private Stat findOrAllocateStat(Date d)
	{
		Date n = nullifyDate(d);
		if (stats.containsKey(n))
			return stats.get(n);

		Stat s = new Stat(d);
		stats.put(s.getDate(), s);
		return s;
	}

	private Iterable<Date> range(Date n1, Date n2)
	{
		return new DateRange(n1, n2);
	}
}
