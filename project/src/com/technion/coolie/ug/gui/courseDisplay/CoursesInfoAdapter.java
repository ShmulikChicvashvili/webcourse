package com.technion.coolie.ug.gui.courseDisplay;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.GroupOfCourses;

public class CoursesInfoAdapter extends BaseExpandableListAdapter {

	private final Context context;
	private LayoutInflater inflater;
	private final List<GroupOfCourses> parentItems;
	private String groupName;

	public CoursesInfoAdapter(final List<GroupOfCourses> parents,
			final Context context, String groupName) {
		parentItems = parents;
		this.context = context;
		this.groupName = groupName;
	}

	public void setInflater(final LayoutInflater inflater,
			final Activity activity) {
		this.inflater = inflater;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			final boolean isLastChild, View convertView, final ViewGroup parent) {

		if (convertView == null)
			convertView = inflater.inflate(
					R.layout.ug_courses_group_info_child_item, null);

		final TextView names = (TextView) convertView
				.findViewById(R.id.courses_info_names);
		names.setText(calcStringFromCourses(groupPosition, childPosition));
		return convertView;
	}

	private CharSequence calcStringFromCourses(int groupPosition,
			int childPosition) {
		String names = "";

		for (String str : parentItems.get(childPosition).getCourses()) {
			names += str
					+ context.getString(R.string.ug_course_screen_group_info);
		}
		return names;
	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, final ViewGroup parent) {

		if (convertView == null)
			convertView = inflater.inflate(
					R.layout.ug_course_screen_group_info_group_item, null);
		final TextView name = (TextView) convertView
				.findViewById(R.id.course_screen_group_info);
		name.setText(groupName);

		return convertView;
	}

	@Override
	public Object getChild(final int groupPosition, final int childPosition) {
		return null;
	}

	@Override
	public long getChildId(final int groupPosition, final int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(final int groupPosition) {
		return parentItems.size();
	}

	@Override
	public Object getGroup(final int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		if (parentItems.isEmpty())
			return 0;
		return 1;
	}

	@Override
	public void onGroupCollapsed(final int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(final int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(final int groupPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(final int groupPosition,
			final int childPosition) {
		return false;
	}

}
