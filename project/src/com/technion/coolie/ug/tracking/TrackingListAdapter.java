package com.technion.coolie.ug.tracking;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;

public class TrackingListAdapter extends BaseAdapter {

	private final LayoutInflater vi;
	private final List<CourseKey> values;
	private final Context context;

	static class ViewHolder {
		TextView courseNumberTextView, courseNameTextView,
				vacantPlacesTextView;
	}

	public TrackingListAdapter(final Context context, final List<CourseKey> list) {
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.values = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public Object getItem(final int position) {
		return values.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = vi.inflate(R.layout.ug_list_item_tracking_list,
					parent, false);
			viewHolder = new ViewHolder();

			viewHolder.courseNumberTextView = (TextView) convertView
					.findViewById(R.id.ug_trackinglist_item_course_number);
			viewHolder.courseNameTextView = (TextView) convertView
					.findViewById(R.id.ug_trackinglist_item_course_name);
			viewHolder.vacantPlacesTextView = (TextView) convertView
					.findViewById(R.id.ug_trackinglist_item_available_places);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final CourseKey ck = (CourseKey) getItem(position);
		if (ck != null) {
			viewHolder.courseNumberTextView.setText(ck.getNumber());
			Course course = UGDatabase.getInstance(context).getCourseByKey(ck);
			// get course name from local database
			viewHolder.courseNameTextView.setText(course.getName()); 
			viewHolder.vacantPlacesTextView.setText(String.valueOf(course
					.getFreePlaces())); // get number of vacant places from
			// local database
		}
		
		/*final OnClickListener makeListener = new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            LinearLayout ll = (LinearLayout)v.getParent();
	            ImageButton tv = (ImageButton)ll.getChildAt(0);
	            Integer pos = (Integer) tv.getTag();
	            //main.makeInfo(pos);
	            ((Main)activity).makeInfo(pos);
	        }
	    };
	    viewHolder.carMake.setOnClickListener(makeListener);*/
		return convertView;
	}

	public void remove(int position) {
		values.remove(position);
		UGDatabase.getInstance(context).setTrackingCourses(values);
		notifyDataSetChanged();

	}

	public void insert(int position, CourseKey item) {
		values.add(position, item);
		UGDatabase.getInstance(context).setTrackingCourses(values);
		notifyDataSetChanged();
	}

}
