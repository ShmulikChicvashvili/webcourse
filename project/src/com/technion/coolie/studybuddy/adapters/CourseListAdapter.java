package com.technion.coolie.studybuddy.adapters;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.studybuddy.data.DataStore;
import com.technion.coolie.studybuddy.presenters.CourseListPresenter;
import com.technion.coolie.studybuddy.views.CourseActivity;

public class CourseListAdapter extends BaseAdapter implements Observer
{
	private LayoutInflater mInflater;
	private CourseListPresenter presenter;

	/**
	 * 
	 */
	public CourseListAdapter(Context context)
	{
		super();
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		presenter = DataStore.getMainPresenter();
	}

	@Override
	public int getCount()
	{
		return DataStore.getMainPresenter().getCount();
	}

	@Override
	public Object getItem(int position)
	{
		return presenter.getNameByPosition(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View view = null;
		if (convertView == null)
		{
			view = createView();
		} else
		{
			view = convertView;
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		String courseName = presenter.getNameByPosition(position);
		if (courseName.length() > 12)
			courseName = courseName.substring(0, 12) + "...";
		holder.courseName.setText(courseName);
		holder.courseNumber.setText(presenter.getIdByPosition(position));
		view.setOnClickListener(new OnClickListenerImplementation(position));
		return view;
	}

	private View createView()
	{
		View view;
		view = mInflater.inflate(R.layout.stb_view_course_item, null);
		ViewHolder holder = new ViewHolder();
		holder.courseName = (TextView) view.findViewById(R.id.course_name);
		holder.courseNumber = (TextView) view.findViewById(R.id.course_number);
		view.setTag(holder);
		return view;
	}

	private final class OnClickListenerImplementation implements
			OnClickListener
	{
		private final int position;

		private OnClickListenerImplementation(int position)
		{
			this.position = position;
		}

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(v.getContext(), CourseActivity.class);
			intent.putExtra(CourseActivity.COURSE_ID,
					presenter.getIdByPosition(position));
			v.getContext().startActivity(intent);

		}
	}

	private class ViewHolder
	{
		public TextView courseName;
		public TextView courseNumber;
	}

	@Override
	public void update(Observable observable, Object data)
	{
		String change = (String) data;
		if (change != DataStore.CLASS_LIST)
			return;

		notifyDataSetChanged();

	}
}
