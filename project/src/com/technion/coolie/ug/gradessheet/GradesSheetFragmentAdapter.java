package com.technion.coolie.ug.gradessheet;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.AccomplishedCourse;

public class GradesSheetFragmentAdapter extends ArrayAdapter<AccomplishedCourse> {

	private final List<AccomplishedCourse> items;
	private final LayoutInflater inflater;

	public GradesSheetFragmentAdapter(final Context context,
			final List<AccomplishedCourse> items) {
		super(context, 0, items);
		this.items = items;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = inflater.inflate(
					R.layout.ug_list_item_grades_sheet_fragment, parent, false);
		}

		final AccomplishedCourse i = items.get(position);
		if (i != null)
			if (i.getSection()) {
				v = inflater.inflate(R.layout.ug_grades_list_item_section, null);

				final TextView sectionView = (TextView) v
						.findViewById(R.id.list_item_section_text);
				sectionView.setText(i.getSemester());
				sectionView.setTextColor(Color.parseColor("#FFCC00"));
				sectionView.setBackgroundColor(Color.parseColor("#0099b3"));

			} else if (i.getAvg()!=null) {
				v = inflater.inflate(R.layout.ug_grades_list_item_footer, null);

				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);

				final TextView footerAvgView = (TextView) v
						.findViewById(R.id.list_item_footer_average);
				final TextView footerTotPointsView = (TextView) v
						.findViewById(R.id.list_item_footer_num_of_point);
				footerAvgView.setText(i.getAvg());
				footerTotPointsView.setText(i.getPoints());

			} else {
				v = inflater.inflate(R.layout.ug_grades_list_item_entry, null);
				final TextView courseNumber = (TextView) v
						.findViewById(R.id.list_item_entry_course_number);
				final TextView courseName = (TextView) v
						.findViewById(R.id.list_item_entry_course_name);
				final TextView points = (TextView) v
						.findViewById(R.id.list_item_entry_points);
				final TextView grade = (TextView) v
						.findViewById(R.id.list_item_entry_grade);

				if (courseNumber != null)
					courseNumber.setText(i.getCourseNumber());
				if (courseName != null)
					courseName.setText(i.getName());
				if (points != null)
					points.setText(i.getPoints());
				if (grade != null)
					grade.setText(i.getGrade());

			}
		return v;
	}
}
