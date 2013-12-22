package com.technion.coolie.webcourse.gr_plusplus;


import java.util.ArrayList;

import com.technion.coolie.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class AssignmentListAdapter extends BaseAdapter
{
	LayoutInflater inflater;
	Context mContext;
	ArrayList<AssignmentData> mAssignmentList;
	
	private class ViewHolder {
		
		public CheckBox mAssignmentDone;
		public TextView mAssignmentName;
		public TextView mAssignmentDesc;
		public TextView mAssignmentDueDate;
	}
	
	public AssignmentListAdapter(Context context, ArrayList<AssignmentData> assignments) {  
		
		mContext = context;
        inflater = LayoutInflater.from(mContext); 
        mAssignmentList = assignments;
    }  
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAssignmentList.size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAssignmentList.get(position);
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
			view = inflater.inflate(R.layout.web_assignments_list_item, null);
			
			// Create the view holder
			holder = new ViewHolder();
			holder.mAssignmentDone = (CheckBox) view.findViewById(R.id.assignment_check_box);
			holder.mAssignmentName = (TextView) view.findViewById(R.id.assignment_name);
			holder.mAssignmentDesc = (TextView) view.findViewById(R.id.assignment_desc);
			holder.mAssignmentDueDate = (TextView) view.findViewById(R.id.assignment_due_date);
			
			// Set position tag for checkbox
			Integer positionTag = Integer.valueOf(position);
			holder.mAssignmentDone.setTag(positionTag);
			
	        OnCheckedChangeListener checkBoxChanged = new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					
					Toast.makeText(mContext, "Clicked on Checkbox: " + String.valueOf((Integer)buttonView.getTag()) + ", Selected: " + buttonView.isChecked(), Toast.LENGTH_LONG).show();
				}
			};
			
	        holder.mAssignmentDone.setOnCheckedChangeListener(checkBoxChanged);	 
			view.setTag(holder);
		}
		else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}
		
		AssignmentData current = mAssignmentList.get(position);
		
		holder.mAssignmentName.setText(current.getName());
		holder.mAssignmentDesc.setText(current.getDescription());
		holder.mAssignmentDueDate.setText("Due Date: " + current.getDueDate());
		holder.mAssignmentDone.setChecked(current.getIsDone());
		
		return view;		
	}
}

