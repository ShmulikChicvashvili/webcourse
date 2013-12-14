package com.technion.coolie.studybuddy.Models;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.studybuddy.utils.Utils.Mapper;
import com.technion.coolie.studybuddy.utils.Utils.Matcher;

@DatabaseTable
public class StudyItem implements Comparable<StudyItem> {

	@DatabaseField(generatedId = true)
	private UUID id;
	@DatabaseField
	private int num;
	@DatabaseField
	private String label;
	@DatabaseField
	private boolean done;

	public StudyItem() {

	}

	public StudyItem(int num) {
		this(num, String.valueOf(num), false);
	}

	public StudyItem(int num, String label) {
		this(num, label, false);
	}

	public StudyItem(int num, String label, boolean done) {
		this.num = num;
		this.label = label;
		this.done = done;
	}

	@Override
	public int compareTo(StudyItem another) {
		return num - another.num;
	}

	public String getLabel() {
		return label;
	}

	public int getNum() {
		return num;
	}

	public boolean isDone() {
		return (done == true);
	}

	public void markDone() {
		done = true;
	}

	public void markUndone() {
		done = false;
	}

	public void toggleDone() {
		done = !done;
	}

	private static Matcher<StudyItem> doneMatcher = new Matcher<StudyItem>() {

		@Override
		public boolean matches(StudyItem item) {
			return item.isDone();
		}

	};

	public static Matcher<StudyItem> doneMatcher() {
		return doneMatcher;
	}

	private static Matcher<StudyItem> notDoneMathcer = new Matcher<StudyItem>() {

		@Override
		public boolean matches(StudyItem item) {
			return !item.isDone();
		}

	};

	public static Matcher<StudyItem> notDoneMatcher() {
		return notDoneMathcer;
	}

	private static Mapper<StudyItem, Integer> mapToNum = new Mapper<StudyItem, Integer>() {

		@Override
		public Integer map(StudyItem item) {
			return item.getNum();
		}

	};
	private static Mapper<StudyItem, String> mapToLabel = new Mapper<StudyItem, String>() {

		@Override
		public String map(StudyItem item) {
			return item.getLabel();
		}

	};

	public static Mapper<StudyItem, Integer> getNumMapper() {
		return mapToNum;
	}

	public static Mapper<StudyItem, String> getLabelMapper() {
		return mapToLabel;
	}

	public static Matcher<StudyItem> getNumFilter(final int num) {
		return new Matcher<StudyItem>() {

			@Override
			public boolean matches(StudyItem item) {
				return item.getNum() == num;
			}
		};
	}
}
