package com.technion.coolie.ug.coursesAndExams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.ExamItem;

public class CoursesAndExamsFragmentListAdapter extends BaseAdapter {
	class Exam implements Comparable<Exam> {
		public Exam(final String courseName, final String moed,
				final Calendar date) {
			super();
			this.courseName = courseName;
			this.moed = moed;
			this.date = date;
		}

		public String courseName;
		public String moed;
		public Calendar date;

		@Override
		public int compareTo(final Exam another) {
			if (another == null || date == null || another.date == null)
				return 0;
			return date.compareTo(another.date);
		}

	}

	private final Context context;
	private final List<Exam> values = new ArrayList<Exam>();

	public CoursesAndExamsFragmentListAdapter(final Context context,
			final List<CourseItem> coursesList) {
		this.context = context;
		for (final CourseItem c : coursesList) {
			final List<ExamItem> exams = c.getExams();
			boolean alreadyAdded = false;
			if (exams.size() >= 1 && exams.get(0).getDate() != null)
			{
				values.add(new Exam(c.getCoursName(), "A", exams.get(0)
						.getDate()));
				alreadyAdded = true;
			}
			if (exams.size() >= 2 && exams.get(1).getDate() != null)
			{
				values.add(new Exam(c.getCoursName(), "B", exams.get(1)
						.getDate()));
				alreadyAdded = true;
			}
			if (!alreadyAdded)
			{
				values.add(new Exam(c.getCoursName(), "-", null));
				continue;
			}

		}
		// sort the list
		Collections.sort(values);

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
		if (convertView == null) {
			final LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(
					R.layout.ug_list_item_courses_and_exams_fragment, parent,
					false);
		}
		final Exam exam = (Exam) getItem(position);
		final TextView courseNameTextView = (TextView) convertView
				.findViewById(R.id.ug_courses_and_exams_fragment_course_name);
		courseNameTextView.setText(exam.courseName);

		final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy",
				Locale.getDefault());
		String dd = context.getString(R.string.no_exam);
		if (exam.date != null) {
			dd = formatter.format(exam.date.getTime());

			dd += exam.moed.equals("A") ? " ("
					+ context.getString(R.string.moedA) + ")  " + "" : " ("
					+ context.getString(R.string.moedB) + ")   ";
		}

		final TextView dateTextView = (TextView) convertView
				.findViewById(R.id.ug_courses_and_exams_fragment_date);
		dateTextView.setText(dd);
		return convertView;
	}
}
