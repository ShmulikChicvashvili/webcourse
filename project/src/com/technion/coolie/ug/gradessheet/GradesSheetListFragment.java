package com.technion.coolie.ug.gradessheet;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.technion.coolie.ug.TransparentActivity;
import com.technion.coolie.ug.model.AccomplishedCourse;

public class GradesSheetListFragment extends ListFragment {

	List<AccomplishedCourse> coursesList = new ArrayList<AccomplishedCourse>() {
		{

			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ¢׳³ֲ¨׳³ג€÷׳³ג€¢׳³ֳ— ׳³ג€�׳³ג‚×׳³ֲ¢׳³ן¿½׳³ג€�",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ ׳³ן¿½׳³ג„¢׳³ג€“׳³ג€� ׳³ֲ ׳³ג€¢׳³ן¿½׳³ֲ¨׳³ג„¢׳³ֳ—",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳³ג€™׳³ֲ ׳³ֻ�׳³ג„¢׳³ֲ§׳³ג€�", "3.5", null,
					"98"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ ׳³ן¿½׳³ג„¢׳³ג€“׳³ג€� ׳³ֲ ׳³ג€¢׳³ן¿½׳³ֲ¨׳³ג„¢׳³ֳ—",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ¢׳³ֲ¨׳³ג€÷׳³ג€¢׳³ֳ— ׳³ג€�׳³ג‚×׳³ֲ¢׳³ן¿½׳³ג€�",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ ׳³ן¿½׳³ג„¢׳³ג€“׳³ג€� ׳³ֲ ׳³ג€¢׳³ן¿½׳³ֲ¨׳³ג„¢׳³ֳ—",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ¢׳³ֲ¨׳³ג€÷׳³ג€¢׳³ֳ— ׳³ג€�׳³ג‚×׳³ֲ¢׳³ן¿½׳³ג€�",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ ׳³ן¿½׳³ג„¢׳³ג€“׳³ג€� ׳³ֲ ׳³ג€¢׳³ן¿½׳³ֲ¨׳³ג„¢׳³ֳ—",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ¢׳³ֲ¨׳³ג€÷׳³ג€¢׳³ֳ— ׳³ג€�׳³ג‚×׳³ֲ¢׳³ן¿½׳³ג€�",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ ׳³ן¿½׳³ג„¢׳³ג€“׳³ג€� ׳³ֲ ׳³ג€¢׳³ן¿½׳³ֲ¨׳³ג„¢׳³ֳ—",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ¢׳³ֲ¨׳³ג€÷׳³ג€¢׳³ֳ— ׳³ג€�׳³ג‚×׳³ֲ¢׳³ן¿½׳³ג€�",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ ׳³ן¿½׳³ג„¢׳³ג€“׳³ג€� ׳³ֲ ׳³ג€¢׳³ן¿½׳³ֲ¨׳³ג„¢׳³ֳ—",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ¢׳³ֲ¨׳³ג€÷׳³ג€¢׳³ֳ— ׳³ג€�׳³ג‚×׳³ֲ¢׳³ן¿½׳³ג€�",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ ׳³ן¿½׳³ג„¢׳³ג€“׳³ג€� ׳³ֲ ׳³ג€¢׳³ן¿½׳³ֲ¨׳³ג„¢׳³ֳ—",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ¢׳³ֲ¨׳³ג€÷׳³ג€¢׳³ֳ— ׳³ג€�׳³ג‚×׳³ֲ¢׳³ן¿½׳³ג€�",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ ׳³ן¿½׳³ג„¢׳³ג€“׳³ג€� ׳³ֲ ׳³ג€¢׳³ן¿½׳³ֲ¨׳³ג„¢׳³ֳ—",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ¢׳³ֲ¨׳³ג€÷׳³ג€¢׳³ֳ— ׳³ג€�׳³ג‚×׳³ֲ¢׳³ן¿½׳³ג€�",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳³ן¿½׳³ֲ ׳³ן¿½׳³ג„¢׳³ג€“׳³ג€� ׳³ֲ ׳³ג€¢׳³ן¿½׳³ֲ¨׳³ג„¢׳³ֳ—",
					"4.0", null, "85"));
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final GradesSheetFragmentListAdapter adapter = new GradesSheetFragmentListAdapter(
				inflater.getContext(), coursesList);
		setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), TransparentActivity.class);
		Bundle b = new Bundle();
		b.putString("key", "gradesSheetLayout");
		intent.putExtras(b);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}
}