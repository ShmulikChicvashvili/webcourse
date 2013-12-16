package com.technion.coolie.joinin.gui;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.joinin.data.ClientEvent;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context _context;
    private Activity _activity;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<ClientEvent>> _listDataChild;
    private boolean _showHours; 
 
    public ExpandableListAdapter(Context context, Activity act, List<String> listDataHeader,
            HashMap<String, List<ClientEvent>> listChildData, boolean showHours) {
        _context = context;
        _listDataHeader = listDataHeader;
        _listDataChild = listChildData;
        _activity = act;
        _showHours =showHours; 
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final ClientEvent eventDetails = (ClientEvent) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.ji__event_list_item, null);
        }
        
        setEventView(convertView,eventDetails);
        
        return convertView;
    }
    
    private void setEventView(View convertView, ClientEvent eventDetails){
    
    	ImageView img = (ImageView)convertView.findViewById(R.id.eventImageView);
	    TextView name = (TextView)convertView.findViewById(R.id.eventNameText);
		TextView location = (TextView)convertView.findViewById(R.id.eventLocation);
		TextView date = (TextView)convertView.findViewById(R.id.eventDate);
		
		//img.setImageResource(R.drawable.skel_module2);
		name.setText(eventDetails.getName());
		location.setText(eventDetails.getAddress());
		if (_showHours){
			date.setText(eventDetails.getWhen().printTime());
		}
		else{
			date.setText(eventDetails.getWhen().toString());	
		}
		Drawable icon = eventDetails.getEventType().getDrawable(_activity);
		img.setImageDrawable(icon);
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.ji__list_group_string_heade, null);
        }
 
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

