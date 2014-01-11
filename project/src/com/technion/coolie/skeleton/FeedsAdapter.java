package com.technion.coolie.skeleton;

import java.util.Date;
import java.util.List;

import com.technion.coolie.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedsAdapter extends BaseAdapter {

	Context mContext;
	List<CoolieNotificationManager.Notif> feeds;
	
	private class ViewHolder
	{
		ImageView image;
		TextView title;
		TextView text;
		TextView time;
	}
	public FeedsAdapter(Context c) {
		mContext = c;
		feeds = CoolieNotificationManager.getFeedList();
	}
	@Override
	public int getCount() {
		return feeds.size();
	}

	@Override
	public Object getItem(int position) {
		return feeds.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		ViewHolder holder = null;
		
		if(convertView == null)
		{
			LayoutInflater inf = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(R.layout.skel_single_feed, null);
			holder = new ViewHolder();
			
			holder.title = (TextView) v.findViewById(R.id.skel_feed_title);
			holder.text = (TextView) v.findViewById(R.id.skel_feed_text);
			holder.image = (ImageView) v.findViewById(R.id.skel_feed_image);
			holder.time = (TextView) v.findViewById(R.id.skel_feed_time);
			
			v.setTag(holder);
		}
		else
		{
			v = convertView;
			holder = (ViewHolder) v.getTag();
		}

		final CoolieNotificationManager.Notif curr = feeds.get(position);
		holder.title.setText(curr.title);
		holder.text.setText(curr.text);
		holder.image.setImageResource(curr.module.getPhotoRes());
		holder.time.setText(curr.day+"/"+curr.month+"/"+curr.year+" "+curr.hour+":"+curr.minutes);
		
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				curr.module.addUsage();
		        Intent intent = new Intent(mContext, curr.module.getActivity());
		        mContext.startActivity(intent);	
		        feeds.remove(curr);
			}
		});
		return v;
	}

}
