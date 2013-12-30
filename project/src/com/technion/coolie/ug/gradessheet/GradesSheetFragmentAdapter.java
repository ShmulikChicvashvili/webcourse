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

public class GradesSheetFragmentAdapter extends ArrayAdapter<SectionedListItem> {

	private final List<SectionedListItem> items;
	private final LayoutInflater vi;

	public GradesSheetFragmentAdapter(final Context context,
			final List<SectionedListItem> items) {
		super(context, 0, items);
		this.items = items;
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View v = convertView;

		final SectionedListItem i = items.get(position);
		if (i != null)
			if (i.isSection()) {
				final GradesSectionItem si = (GradesSectionItem) i;
				v = vi.inflate(R.layout.ug_grades_list_item_section, null);

				final TextView sectionView = (TextView) v
						.findViewById(R.id.list_item_section_text);
				sectionView.setText(si.getTitle());
				sectionView.setTextColor(Color.parseColor("#FFCC00"));
				sectionView.setBackgroundColor(Color.parseColor("#0099b3"));

			} else if (i.isFooter()) {
				final GradesFooterItem si = (GradesFooterItem) i;
				v = vi.inflate(R.layout.ug_grades_list_item_footer, null);

				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);

				final TextView footerAvgView = (TextView) v
						.findViewById(R.id.list_item_footer_average);
				final TextView footerTotPointsView = (TextView) v
						.findViewById(R.id.list_item_footer_num_of_point);
				footerAvgView.setText(si.getSemesterAvg());
				footerTotPointsView.setText(si.getSemesterTotalPoints());

			} else {
				final AccomplishedCourse ei = (AccomplishedCourse) i;
				v = vi.inflate(R.layout.ug_grades_list_item_entry, null);
				final TextView courseNumber = (TextView) v
						.findViewById(R.id.list_item_entry_course_number);
				final TextView courseName = (TextView) v
						.findViewById(R.id.list_item_entry_course_name);
				final TextView points = (TextView) v
						.findViewById(R.id.list_item_entry_points);
				final TextView grade = (TextView) v
						.findViewById(R.id.list_item_entry_grade);

				if (courseNumber != null)
					courseNumber.setText(ei.getCourseNumber());
				if (courseName != null)
					courseName.setText(ei.getName());
				if (points != null)
					points.setText(ei.getPoints());
				if (grade != null)
					grade.setText(ei.getGrade());

			}
		return v;
	}
}
