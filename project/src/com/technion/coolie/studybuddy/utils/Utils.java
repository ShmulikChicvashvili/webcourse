package com.technion.coolie.studybuddy.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public enum Utils
{
	INSTANCE;

	public static class ListAttacher<E> implements Reducer<List<E>, E>
	{

		@Override
		public List<E> op(List<E> target, E element)
		{
			target.add(element);
			return target;
		}

	}

	public interface Mapper<E, K>
	{

		public K map(E item);

	}

	public interface Matcher<E>
	{

		boolean matches(E item);
	}

	public interface Reducer<T, E>
	{

		public T op(T target, E element);

	}

	public static class SumReducer<E> implements Reducer<Integer, E>
	{

		private final Mapper<E, Integer>	mapper;

		public SumReducer(Mapper<E, Integer> mapper)
		{
			this.mapper = mapper;
		}

		@Override
		public Integer op(Integer target, E element)
		{
			return target + mapper.map(element);
		}
	}

	private final static Random	r	= new Random((new Date()).getTime());

	public static <T> Set<T> asSet(T... args)
	{
		return new HashSet<T>(Arrays.asList(args));
	}

	public static <T extends Comparable<T>> List<T> asSortedList(T... args)
	{
		return sorted(Arrays.asList(args));
	}

	public static <E> List<E> filter(Collection<E> list, Matcher<E> match)
	{
		List<E> filtered = new ArrayList<E>();

		for (E item : list)
		{
			if (match.matches(item))
			{
				filtered.add(item);
			}
		}

		return filtered;

	}

	public static <E> E findFirst(Collection<E> list, Matcher<E> match)
	{
		for (E item : list)
		{
			if (match.matches(item))
				return item;
		}

		return null;

	}

	public static <E, K> List<K> map(List<E> list, Mapper<E, K> mapper)
	{
		List<K> $ = new ArrayList<K>(list.size());

		for (E item : list)
		{
			$.add(mapper.map(item));
		}

		return $;

	}

	public static int randomInt(int num)
	{
		return r.nextInt(num);
	}

	public static <T, E> T reduce(	Collection<E> list,
									T target,
									Reducer<T, E> reducer)
	{
		T $ = target;

		for (E e : list)
		{
			reducer.op($, e);
		}

		return $;
	}

	public static <T extends Comparable<T>> List<T> sorted(Collection<T> collection)
	{
		List<T> list = new ArrayList<T>();
		list.addAll(collection);
		Collections.sort(list);
		return list;
	}

}
