package com.technion.coolie.studybuddy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.studybuddy.Views.CourseActivity;
import com.technion.coolie.studybuddy.data.DataStore;

public class CourseAdapter extends BaseAdapter
{
	private LayoutInflater mInflater;

	/**
	 * 
	 */
	public CourseAdapter(Context context)
	{
		super();
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount()
	{
		return DataStore.getCourcesSize();
	}

	@Override
	public Object getItem(int position)
	{
		return DataStore.getCourse(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		TextView courseName = null;
		TextView courseNumber = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.stb_view_course_item, null);
			convertView.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(v.getContext(),
							CourseActivity.class);
					intent.putExtra(CourseActivity.courseNameArgs,
							DataStore.getCourse(position));
					v.getContext().startActivity(intent);

				}
			});
			courseName = (TextView) convertView.findViewById(R.id.course_name);
			courseNumber = (TextView) convertView
					.findViewById(R.id.course_number);
			ViewHolder holder = new ViewHolder(courseName, courseNumber);
			convertView.setTag(holder);
		} else
		{
			ViewHolder holder = (ViewHolder) convertView.getTag();
			courseName = holder.courseName;
			courseNumber = holder.courseNumber;
		}
		courseName.setText(DataStore.getCourse(position));
		courseNumber.setText(DataStore.getCourseNumber(position));
		return convertView;
	}

	private class ViewHolder
	{
		public TextView courseName;
		public TextView courseNumber;

		/**
		 * @param courseName
		 * @param courseNumber
		 */
		public ViewHolder(TextView courseName, TextView courseNumber)
		{
			super();
			this.courseName = courseName;
			this.courseNumber = courseNumber;
		}
	}
}
