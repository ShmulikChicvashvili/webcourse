package com.technion.coolie.studybuddy.models;

import static com.technion.coolie.studybuddy.models.GeneralInfo.WEEKS_IN_SEMESTER;
import static com.technion.coolie.studybuddy.utils.Utils.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.technion.coolie.studybuddy.utils.SparseArrayMap;

public class StudyResource {
	private static final String DEFAULT_LABEL = "DEFAULT";
	private String label;
	private List<StudyItem> items = new ArrayList<StudyItem>();

	public static StudyResource fromItemList(List<String> list) {
		return fromItemList(DEFAULT_LABEL, list);
	}

	public static StudyResource fromItemList(String label, List<String> list) {
		StudyResource sr = new StudyResource();
		sr.setLabel(label);
		sr.addItems(list);
		return sr;
	}

	public static StudyResource createWithItems(Integer num) {
		return createWithItems(DEFAULT_LABEL, num);
	}

	public static StudyResource createWithItems(String label, Integer num) {
		StudyResource sr = new StudyResource();
		sr.setLabel(label);

		for (int i = 0; i < num; i++) {
			sr.addItem(new StudyItem(i + 1));
		}
		return sr;
	}

	public static StudyResource createWithDefaultItems() {
		return createWithItems(WEEKS_IN_SEMESTER);
	}

	public void addItem(StudyItem item) {
		items.add(item);
	}

	public StudyResource() {
	}

	private void addItems(List<String> list) {
		int i = items.size();
		for (String str : list) {
			items.add(new StudyItem(++i, str));
		}
	}

	public List<StudyItem> getItemsDone() {
		return filter(items, StudyItem.doneMatcher());
	}

	public List<StudyItem> getItemsRemaining() {
		return filter(items, StudyItem.notDoneMatcher());
	}

	public int getNumItemsDone() {
		return getItemsDone().size();
	}

	public int getNumItemsRemaining() {
		return filter(items, StudyItem.notDoneMatcher()).size();
	}

	public List<Integer> getItemsDoneIds() {
		return asSortedList(map(getItemsDone(), StudyItem.getNumMapper()));
	}

	public List<String> getItemsDoneLabels() {

		return asSortedList(map(getItemsDone(), StudyItem.getLabelMapper()));
	}

	public List<Integer> getItemsRemainingIds() {
		return asSortedList(map(getItemsRemaining(), StudyItem.getNumMapper()));
	}

	public List<String> getItemsRemainingLabels() {
		return asSortedList(map(getItemsRemaining(), StudyItem.getLabelMapper()));

	}

	public int getItemsTotal() {
		return items.size();
	}

	public void markDone(int id) {
		toggleDone(id);
	}

	public void markUnDone(int id) {
		toggleDone(id);
	}

	private void setLabel(String label) {
		this.label = label;

	}

	private void toggleDone(int id) {
		List<StudyItem> result = filter(items, StudyItem.getNumFilter(id));
		if (result.size() < 1)
			return;
		result.get(0).toggleDone();

	}

	public List<StudyItem> getAllItems() {
		return items;
	}

}
