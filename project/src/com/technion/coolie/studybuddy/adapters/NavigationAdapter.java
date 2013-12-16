package com.technion.coolie.studybuddy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.studybuddy.data.DataStore;
import com.technion.coolie.studybuddy.views.CourseActivity;
import com.technion.coolie.studybuddy.views.TasksActivity;

public class NavigationAdapter extends BaseExpandableListAdapter
{
	public static final String	course	= "course";
	private LayoutInflater		mInflater;
	/**
	 * 
	 */

	private Context				context;

	public NavigationAdapter(Context context)
	{
		super();
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		switch (groupPosition)
		{
		case 0:
		case 2:
		case 3:
			return null;
		case 1:
			return DataStore.getMainPresenter().getCourseNameByPosition(
					childPosition);

		default:
			break;
		}
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent)
	{
		TextView childText = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.stb_view_list_item, null);
			childText = (TextView) convertView
					.findViewById(R.id.drawer_list_item_text);
			childText.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// fragment.selectItem(childPosition);
					Intent intent = new Intent(context, CourseActivity.class);
					intent.putExtra(course, childPosition);
					context.startActivity(intent);
				}
			});
			convertView.setTag(new ViewHolder(childText));
		}
		childText = ((ViewHolder) convertView.getTag()).getTextView();
		childText
				.setText("	" + (String) getChild(groupPosition, childPosition));
		convertView.setTag(new ViewHolder(childText));
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		switch (groupPosition)
		{
		case 0:
		case 2:
		case 3:
			return 0;
		case 1:
			return DataStore.getMainPresenter().getCoursesCount();

		default:
			break;
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return DataStore.getMenu(groupPosition);
	}

	@Override
	public int getGroupCount()
	{
		return DataStore.getMenuSize();
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent)
	{
		TextView groupText = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.stb_view_list_item, null);
			groupText = (TextView) convertView
					.findViewById(R.id.drawer_list_item_text);
			convertView.setTag(new ViewHolder(groupText));
			if (groupPosition == 0)
			{
				groupText.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(context, TasksActivity.class);
						context.startActivity(intent);
					}
				});
			}
		}
		groupText = ((ViewHolder) convertView.getTag()).getTextView();
		groupText.setText((String) getGroup(groupPosition));
		convertView.setTag(new ViewHolder(groupText));
		return convertView;
	}

	@Override
	public boolean hasStableIds()
	{

		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{

		return false;
	}

	private class ViewHolder
	{
		private TextView	textView;

		/**
		 * @param textView
		 */
		public ViewHolder(TextView textView)
		{
			super();
			this.textView = textView;
		}

		/**
		 * @return the textView
		 */
		public synchronized TextView getTextView()
		{
			return textView;
		}
	}

}
