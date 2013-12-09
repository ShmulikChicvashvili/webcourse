package com.technion.coolie.skeleton;

import java.util.Arrays;
import java.util.Comparator;

import com.technion.coolie.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecentlyUsedAdapter extends BaseAdapter {

	CoolieModule[] modules;
	Context mContext;
	
	private class ViewHolder
	{
		TextView title;
		TextView desc;
		ImageView icon;
	}
	public RecentlyUsedAdapter(Context c)
	{
		mContext = c;
		modules = CoolieModule.values();
		Arrays.sort(modules, new Comparator<CoolieModule>()
		{

			@Override
			public int compare(CoolieModule arg0, CoolieModule arg1) {
				return arg0.getLastUsed().compareTo(arg1.getLastUsed());
			}
	
		});
	}
	@Override
	public int getCount() {
		return CoolieModule.values().length;
	}

	@Override
	public Object getItem(int pos) {
		return modules[pos];
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		ViewHolder holder = null;
		
		if(convertView == null)
		{
			LayoutInflater inf = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(R.layout.skel_big_button_layout, null);
			
			holder = new ViewHolder();
			
			holder.title = (TextView) v.findViewById(R.id.skel_title);
			holder.desc = (TextView) v.findViewById(R.id.skel_desc);
			holder.icon = (ImageView) v.findViewById(R.id.skel_image);
			
			v.setTag(holder);
		}
		else
		{
			v = convertView;
			holder = (ViewHolder) v.getTag();
		}

		CoolieModule curr = modules[position];
		holder.title.setText(curr.getName(mContext));
		holder.desc.setText(curr.getDescription(mContext));
		holder.icon.setImageResource(curr.getPhotoRes());
		return v;
	}

}
