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

public enum Utils {
	INSTANCE;

	private final static Random r = new Random((new Date()).getTime());

	public static int randomInt(int num) {
		return r.nextInt(num);
	}

	public static <T> Set<T> asSet(T... args) {
		return new HashSet<T>(Arrays.asList(args));
	}

	public static <T extends Comparable<T>> List<T> asSortedList(
			Collection<T> set) {
		List<T> list = new ArrayList<T>();
		list.addAll(set);
		Collections.sort(list);
		return list;
	}

	public static <T extends Comparable<T>> List<T> asSortedList(T... args) {
		return asSortedList(Arrays.asList(args));
	}

}
