package com.example.gr_plusplus;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CoursesListAdapter extends BaseAdapter
{
	LayoutInflater inflater;
	Context mContext;
	ArrayList<CourseData> mCoursesList;
	
	private class ViewHolder {
		
		public TextView CourseName;
		public TextView CourseDesc;

	}
	
	public CoursesListAdapter(Context context, ArrayList<CourseData> courses) {  
		
		mContext = context;
        inflater = LayoutInflater.from(mContext); 
        mCoursesList = courses;
    }  
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCoursesList.size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mCoursesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder holder;
		
		if (null == convertView) {
			view = inflater.inflate(R.layout.courses_list_item, null);
			
			// Create the view holder
			holder = new ViewHolder();
			holder.CourseName = (TextView) view.findViewById(R.id.course_name);
			holder.CourseDesc = (TextView) view.findViewById(R.id.course_desc);
			view.setTag(holder);
		}
		else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}
		
		CourseData current = mCoursesList.get(position);
		holder.CourseName.setText(current.getName());
		holder.CourseDesc.setText(current.getDesc());
		
		return view;		
	}
}

