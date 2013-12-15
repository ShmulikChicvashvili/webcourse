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
import com.technion.coolie.studybuddy.Models.Course;
import com.technion.coolie.studybuddy.PresenterModels.MainPresenterModel;
import com.technion.coolie.studybuddy.Views.CourseActivity;
import com.technion.coolie.studybuddy.data.DataStore;

public class CourseAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private MainPresenterModel pModel;

	/**
	 * 
	 */
	public CourseAdapter(Context context) {
		super();
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
		pModel = DataStore.getMainPresenterModel();
	}

	@Override
	public int getCount() {
		return DataStore.getMainPresenterModel().getCoursesCount();
	}

	@Override
	public Object getItem(int position) {
		return DataStore.getMainPresenterModel().getCourseByPosition(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			view = createView();
		} else {
			view = convertView;
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.courseName.setText(pModel.getCourseNameByPosition(position));
		holder.courseNumber.setText(pModel.getCourseIdByPosition(position));
		view.setOnClickListener(new OnClickListenerImplementation(position));
		return view;
	}

	private View createView() {
		View view;
		view = mInflater.inflate(R.layout.stb_view_course_item, null);
		ViewHolder holder = new ViewHolder();
		holder.courseName = (TextView) view.findViewById(R.id.course_name);
		holder.courseNumber = (TextView) view.findViewById(R.id.course_number);
		view.setTag(holder);
		return view;
	}

	private final class OnClickListenerImplementation implements
			OnClickListener {
		private final int position;

		private OnClickListenerImplementation(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(v.getContext(), CourseActivity.class);
			intent.putExtra(CourseActivity.COURSE_ID,
					pModel.getCourseIdByPosition(position));
			v.getContext().startActivity(intent);

		}
	}

	private class ViewHolder {
		public TextView courseName;
		public TextView courseNumber;
	}
}
