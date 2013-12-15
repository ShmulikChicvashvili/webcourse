package com.technion.coolie.studybuddy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.studybuddy.Views.StrikeThrowView;
import com.technion.coolie.studybuddy.data.DataStore;
import com.technion.coolie.studybuddy.data.Task;

public class TaskAdapter extends BaseAdapter
{
	private LayoutInflater	mInflater;

	// private boolean selectMode;
	// private boolean[] selected;

	/**
	 * @param context
	 */
	public TaskAdapter(Context context)
	{
		super();
		// selected = new boolean[DataStore.getTaskSize()];
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		// return DataStore.getTaskSize();
		return 0;
	}

	@Override
	public Object getItem(int position)
	{
		// return DataStore.getTask(position);
		return null;
	}

	@Override
	public long getItemId(int position)
	{

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TextView course = null;
		TextView taskDesc = null;
		CheckBox done = null;
		if (convertView == null)
		{
			final StrikeThrowView view = (StrikeThrowView) mInflater.inflate(
					R.layout.stb_view_task, null);
			course = (TextView) view.findViewById(R.id.course);
			taskDesc = (TextView) view.findViewById(R.id.task_desc);
			done = (CheckBox) view.findViewById(R.id.task_done);
			done.setOnCheckedChangeListener(new OnCheckedChangeListener()
			{
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked)
				{
					view.setStrike(isChecked);
				}
			});
			convertView = view;
			convertView.setTag(new ViewHolder(course, taskDesc, done));

		} else
		{
			ViewHolder holder = (ViewHolder) convertView.getTag();
			course = holder.getCourseName();
			taskDesc = holder.getTaskDesc();
			done = holder.getDone();
		}
		
		// Task task = DataStore.getTask(position);
		// course.setText(task.getCourseName());
		// taskDesc.setText(task.getType() + " number " + task.getNumber());

		return convertView;
	}

	private class ViewHolder
	{
		private TextView	courseName;
		private TextView	taskDesc;
		private CheckBox	done;

		/**
		 * @param courseName
		 * @param taskDesc
		 * @param done
		 */
		public ViewHolder(TextView courseName, TextView taskDesc, CheckBox done)
		{
			super();
			this.courseName = courseName;
			this.taskDesc = taskDesc;
			this.done = done;
		}

		/**
		 * @return the courseName
		 */
		public synchronized TextView getCourseName()
		{
			return courseName;
		}

		/**
		 * @return the taskDesc
		 */
		public synchronized TextView getTaskDesc()
		{
			return taskDesc;
		}

		/**
		 * @return the done
		 */
		public synchronized CheckBox getDone()
		{
			return done;
		}

	}

	public void remove(int i)
	{
		// DataStore.removeTask(i);
	}
}
