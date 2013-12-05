package com.technion.coolie.ug.gradessheet;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.technion.coolie.R;

public class EntryAdapter extends ArrayAdapter<Item> {

	private Context context;
	private ArrayList<Item> items;
	private LayoutInflater vi;

	public EntryAdapter(Context context,ArrayList<Item> items) {
		super(context,0, items);
		this.context = context;
		this.items = items;
		vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		final Item i = items.get(position);
		if (i != null) {
			if(i.isSection()){
				GradesSectionItem si = (GradesSectionItem)i;
				v = vi.inflate(R.layout.ug_grades_list_item_section, null);

				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);
				
				final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
				sectionView.setText(si.getTitle());
				sectionView.setBackgroundColor(Color.CYAN);
//				sectionView.setBackgroundColor(context.getResources().getColor(R.color.purple));
				
			}else{
				GradesEntryItem ei = (GradesEntryItem)i;
				v = vi.inflate(R.layout.ug_grades_list_item_entry, null);
				final TextView courseName = (TextView)v.findViewById(R.id.list_item_entry_course_name);
				final TextView points = (TextView)v.findViewById(R.id.list_item_entry_points);
				final TextView grade = (TextView)v.findViewById(R.id.list_item_entry_grade);
				
				
				if (courseName != null) 
					courseName.setText(ei.courseName);
				if(points != null)
					points.setText(ei.points);
				if(grade != null)
					grade.setText(ei.grade);
				
			}
		}
		return v;
	}

}
