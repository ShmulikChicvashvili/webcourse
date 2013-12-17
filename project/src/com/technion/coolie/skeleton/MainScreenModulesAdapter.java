package com.technion.coolie.skeleton;

import java.util.Arrays;
import java.util.Comparator;

import com.technion.coolie.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class MainScreenModulesAdapter extends BaseAdapter {

	CoolieModule[] modules;
	Context mContext;
	Comparator<CoolieModule> comp;
	
	private class ViewHolder
	{
		TextView title;
		TextView desc;
		ImageView icon;
	}
	
	abstract int compareModules(CoolieModule m1, CoolieModule m2);
	
	public MainScreenModulesAdapter(Context c)
	{
		mContext = c;
		modules = CoolieModule.values();
		comp = new Comparator<CoolieModule>()
		{

			@Override
			public int compare(CoolieModule lhs, CoolieModule rhs) {
				return compareModules(lhs, rhs);
			}
	
		};
		Arrays.sort(modules, comp);
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
			v = inf.inflate(R.layout.skel_module_button_layout, null);
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

		final CoolieModule curr = modules[position];
		holder.title.setText(curr.getName(mContext));
		holder.icon.setImageResource(curr.getPhotoRes());
		
		holder.desc.setText(curr.getDescription(mContext));

		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				curr.addUsage();
		        Intent intent = new Intent(mContext, curr.getActivity());
		        mContext.startActivity(intent);				
			}
		});
		return v;
	}
	
	public void sortAgain()
	{
		Arrays.sort(modules, comp);
		notifyDataSetChanged();
	}
	
	

}
