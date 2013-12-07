package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.Course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CoursesAndExamsFragmentListAdapter extends BaseAdapter {
	class Exam implements Comparable<Exam> {
		public Exam(String courseName, String moed, Calendar date) {
			super();
			this.courseName = courseName;
			this.moed = moed;
			this.date = date;
		}

		public String courseName;
		public String moed;
		public Calendar date;

		@Override
		public int compareTo(Exam another) {
			if (another == null || this.date == null || another.date == null)
				return 0;
			return this.date.compareTo(another.date);
		}

	}

	private final Context context;
	private final List<Exam> values = new ArrayList<Exam>();

	public CoursesAndExamsFragmentListAdapter(Context context, List<Course> list) {
		this.context = context;
		for (Course c : list) {
			values.add(new Exam(c.getName(), "A", c.getMoedA()));
			values.add(new Exam(c.getName(), "B", c.getMoedB()));
		}
		Collections.sort(values);
		// sort the list
	}

	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public Object getItem(int position) {
		return values.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.ug_list_item_courses_and_exams_fragment, parent, false);
		}
		Exam exam = (Exam) getItem(position);
		TextView courseNameTextView = (TextView) convertView
				.findViewById(R.id.ug_courses_and_exams_fragment_course_name);
		courseNameTextView.setText(exam.courseName);

		String d = exam.date.get(Calendar.DAY_OF_MONTH) + "/" + exam.date.get(Calendar.MONTH) + "/"
				+ exam.date.get(Calendar.YEAR);
		d += exam.moed.equals("A") ? " (מועד א)" : " (מועד ב)";

		TextView dateTextView = (TextView) convertView.findViewById(R.id.ug_courses_and_exams_fragment_date);
		dateTextView.setText(d);
		return convertView;
	}
}
