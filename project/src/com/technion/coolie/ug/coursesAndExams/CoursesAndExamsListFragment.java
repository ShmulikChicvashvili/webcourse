package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.technion.coolie.ug.TransparentActivity;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.Course;

public class CoursesAndExamsListFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//coursesList = new ArrayList<CourseItem>(UGDatabase.INSTANCE.getStudentCourses(SemesterSeason.SUMMER));
		ArrayList<CourseItem> coursesList = UGDatabase.INSTANCE.getStudentCourses(UGDatabase.INSTANCE.getCurrentSemester().getSs());
		final CoursesAndExamsFragmentListAdapter adapter = new CoursesAndExamsFragmentListAdapter(
				inflater.getContext(), coursesList);

		/** Setting the list adapter for the ListFragment */
		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), TransparentActivity.class);
		Bundle b = new Bundle();
		b.putString("key", "coursesAndExamsLayout");
		intent.putExtras(b);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}
}