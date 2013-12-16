package com.technion.coolie.joinin.gui;

import com.technion.coolie.joinin.data.CategoryItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.technion.coolie.R;

public class CategoryListAdapter extends ArrayAdapter<CategoryItem> {
	Context context; 
	int layoutResourceId;    
	CategoryItem data[] = null;

	public CategoryListAdapter(Context context, int layoutResourceId, CategoryItem[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CategoryItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new CategoryItemHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.categoryImageView);
            holder.txtTitle = (TextView)row.findViewById(R.id.categoryNameText);
            holder.imgCircle = (ImageView)row.findViewById(R.id.CategoryCircle);
            holder.txtNumEvents = (TextView)row.findViewById(R.id.categoryNumEvents);
            
            row.setTag(holder);
        }
        else
        {
            holder = (CategoryItemHolder)row.getTag();
        }
        
        CategoryItem item = data[position];
        holder.txtTitle.setText(item.title);
        holder.imgIcon.setImageResource(item.icon);
        holder.txtNumEvents.setText(item.num);
        holder.imgCircle.setImageResource(item.icon2);
        
        return row;
    }
    
    static class CategoryItemHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        ImageView imgCircle;
        TextView txtNumEvents;
    }
	
}


