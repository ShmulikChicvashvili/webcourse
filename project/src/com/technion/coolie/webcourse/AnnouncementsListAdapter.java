package com.technion.coolie.webcourse;


import java.util.List;

import com.technion.coolie.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class AnnouncementsListAdapter extends BaseAdapter
{
	LayoutInflater inflater;
	Context mContext;
	List<AnnouncementsData> mAnnouncementsList;
	
	private class ViewHolder {
		
		public TextView mAnnouncementTitle;
		public TextView mAnnouncementTimeStamp;

	}
	
	public AnnouncementsListAdapter(Context context, List<AnnouncementsData> announcements) {  
		
		mContext = context;
        inflater = LayoutInflater.from(mContext); 
        mAnnouncementsList = announcements;
    }  
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAnnouncementsList.size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAnnouncementsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder holder;
		
		if (null == convertView) {
			view = inflater.inflate(R.layout.web_announcements_list_item, null);
			
			// Create the view holder
			holder = new ViewHolder();
			holder.mAnnouncementTitle = (TextView) view.findViewById(R.id.announcements_title);
			holder.mAnnouncementTimeStamp = (TextView) view.findViewById(R.id.announcements_time_stamp);
			view.setTag(holder);
		}
		else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}
		
		AnnouncementsData current = mAnnouncementsList.get(position);
		holder.mAnnouncementTitle.setText(current.getTitle());
		holder.mAnnouncementTimeStamp.setText(current.getTimeStamp());
		int color = ((position % 2) == 0)? Color.WHITE : Color.GRAY;
		view.setBackgroundColor(color);
		return view;		
	}
}

