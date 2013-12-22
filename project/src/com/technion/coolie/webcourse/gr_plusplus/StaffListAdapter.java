package com.technion.coolie.webcourse.gr_plusplus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.technion.coolie.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class StaffListAdapter extends BaseExpandableListAdapter
{
	private LayoutInflater inflater;
	private Context mContext;
	private List<StaffData> mStaffList;
	final int SubDataSize = 4;
	
	private class ViewGroupHolder {
		
		public TextView mStaffName;
		public TextView mStaffMail;
		public TextView mStaffPosition;
	}
	
	private class ViewBaseChildHolder {
		
	}
	private class ViewChildPhoneHolder extends ViewBaseChildHolder {
		
		public TextView mStaffPhone;
	}
	
	private class ViewChildOfficeLocHolder extends ViewBaseChildHolder {
		
		public TextView mOfficeLocation;
	}

	private class ViewChildOfficeInfoHolder extends ViewBaseChildHolder {
		
		public TextView mStaffOfficeHoursTitle;
		public TextView mStaffOfficeHoursContent;
	}

	private class ViewChildLectureInfoHolder extends ViewBaseChildHolder {
	
		public TextView mStaffLecturesTitle;
		public TextView mStaffLecturesContent;
	}
	
	private View InflateChildView(int childPosition, ViewBaseChildHolder holder) {
		
		// Get expanded item
		View tempView = inflater.inflate(R.layout.web_staff_exp_item, null);
		View view;
		
		switch (childPosition)
		{
			case (0):
			{
				view = (RelativeLayout) tempView.findViewById(R.id.staff_phone_id);
				((ViewChildPhoneHolder)holder).mStaffPhone = (TextView) view.findViewById(R.id.staff_phone);
				break;
			}
			case (1):
			{
				view = (RelativeLayout) tempView.findViewById(R.id.staff_office_loc_id);
				((ViewChildOfficeLocHolder)holder).mOfficeLocation = (TextView) view.findViewById(R.id.staff_office_loc);
				break;
			}
			case (2):
			{
				view = (RelativeLayout) tempView.findViewById(R.id.staff_office_hours_id);
				((ViewChildOfficeInfoHolder)holder).mStaffOfficeHoursTitle = (TextView) view.findViewById(R.id.staff_office_hours_title);
				((ViewChildOfficeInfoHolder)holder).mStaffOfficeHoursContent = (TextView) view.findViewById(R.id.staff_office_hours_content);
				break;
			}
			case (3):
			{
				view = (RelativeLayout) tempView.findViewById(R.id.staff_lectures_id);
				((ViewChildLectureInfoHolder)holder).mStaffLecturesTitle = (TextView) view.findViewById(R.id.staff_lectures_title);
				((ViewChildLectureInfoHolder)holder).mStaffLecturesContent = (TextView) view.findViewById(R.id.staff_lectures_content);
				break;
			}
			default:
			{
				view = (RelativeLayout) tempView.findViewById(R.id.staff_phone_id);
				break;
			}
		}
		
		return view;
	}
	
	private ViewBaseChildHolder CreateChildHolder(int childPosition) {
		
		ViewBaseChildHolder holder;
		
		switch (childPosition)
		{
			case (0):
			{
				holder = new ViewChildPhoneHolder();
				break;
			}
			case (1):
			{
				holder = new ViewChildOfficeLocHolder();
				break;
			}
			case (2):
			{
				holder = new ViewChildOfficeInfoHolder();
				break;
			}
			case (3):
			{
				holder = new ViewChildLectureInfoHolder();
				break;
			}
			default:
			{
				holder = new ViewChildPhoneHolder();
				break;
			}
		}
		
		return holder;
	}
	
	private void UpdateChildrenViews (ViewBaseChildHolder holder, int childPosition, int groupPosition) {
		
		StaffSubData current = mStaffList.get(groupPosition).getSubData();
		int itemType = getChildType(groupPosition,childPosition);
		switch(itemType)
		{
			case (0):
			{				
				((ViewChildPhoneHolder)holder).mStaffPhone.setText("Phone: " + current.getPhone());
				break;
			}
			case (1):
			{
				((ViewChildOfficeLocHolder)holder).mOfficeLocation.setText("Office Location: " + current.getOfficeLocation());
				break;
			}
			case (2):
			{
				((ViewChildOfficeInfoHolder)holder).mStaffOfficeHoursTitle.setText("Office Hours: ");
			
				String text = "";
				int numOfOfficeHours = current.getOfficeHours().size();
				for (int index = 0; index < numOfOfficeHours; ++index)
				{
					text += current.getOfficeHours().get(index).toString();
					if (index != (numOfOfficeHours - 1))
					{
						text += "\n";
					}
				}
				
				((ViewChildOfficeInfoHolder)holder).mStaffOfficeHoursContent.setText(text);
				
				break;
			}
			case (3):
			{
				((ViewChildLectureInfoHolder)holder).mStaffLecturesTitle.setText("Lectures: ");
				
				String text = "";
				int numOfLectures = current.getLectures().size();
				for (int index = 0; index < numOfLectures; ++index)
				{
					text += current.getLectures().get(index).toString();
					if (index != (numOfLectures - 1))
					{
						text += "\n";
					}
				}
				
				((ViewChildLectureInfoHolder)holder).mStaffLecturesContent.setText(text);

				break;
			}
			default:
				break;
		}
	}
	
	public StaffListAdapter(Context context, List<StaffData> staff) {  
		
		mContext = context;
        inflater = LayoutInflater.from(context); 
        mStaffList = staff;
        
    }  
	
	@Override
    public Object getChild(int groupPosition, int childPosititon) {
        
		return mStaffList.get(groupPosition).mStaffSubData;
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return ((groupPosition * 100) + childPosition);
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
    	View view;
		ViewBaseChildHolder holder;
		
		if (null == convertView) {
			
			// Create the view holder
			holder = CreateChildHolder(childPosition);
			
			// Get the current child view
			view = InflateChildView (childPosition, holder);
			
			view.setTag(holder);
		}
		else {
			view = convertView;
			holder = (ViewBaseChildHolder)view.getTag();
		}

		UpdateChildrenViews(holder, childPosition, groupPosition);
		
		return view;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        
    	return SubDataSize;
    }
    
    @Override
    public int getChildType (int groupPosition, int childPosition) {
    	return childPosition;
    }
    
    @Override
    public int getChildTypeCount () {
    	return SubDataSize;
    }
    
    @Override
    public Object getGroup(int groupPosition) {
    	return mStaffList.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
    	return mStaffList.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
    
    	View view = new View(mContext);
		ViewGroupHolder holder;
		
		if (null == convertView) {
			view = inflater.inflate(R.layout.web_staff_group_item, null);
			
			// Create the view holder
			holder = new ViewGroupHolder();
			holder.mStaffName = (TextView) view.findViewById(R.id.staff_name);
			holder.mStaffPosition = (TextView) view.findViewById(R.id.staff_position);
			holder.mStaffMail = (TextView) view.findViewById(R.id.staff_mail);
			view.setTag(holder);
		}
		else {
			view = convertView;
			holder = (ViewGroupHolder)view.getTag();
		}
		
		StaffData current = mStaffList.get(groupPosition);
		holder.mStaffName.setText(current.getName());
		holder.mStaffPosition.setText(current.getPosition());
		holder.mStaffMail.setText(current.getMail());
		
		return view;
    }
    
    @Override
    public boolean hasStableIds() {
        return true;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

