package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.technion.coolie.ug.TransparentActivity;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.CourseItem;

public class CoursesAndExamsListFragment extends ListFragment {

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		final ArrayList<CourseItem> coursesList = UGDatabase.INSTANCE
				.getStudentCourses(UGDatabase.INSTANCE.getCurrentSemester()
						.getSs());
		final CoursesAndExamsFragmentListAdapter adapter = new CoursesAndExamsFragmentListAdapter(
				inflater.getContext(), coursesList);
		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		final Intent intent = new Intent(getActivity(),
				TransparentActivity.class);
		final Bundle b = new Bundle();
		b.putString("key", CoursesAndExamsFragment.class.toString());
		intent.putExtras(b);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}
}