package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.technion.coolie.ug.gradessheet.GradesSheetActivity;
import com.technion.coolie.ug.model.AccomplishedCourse;

public class GradesSheetListFragment extends ListFragment {

	List<AccomplishedCourse> coursesList = new ArrayList<AccomplishedCourse>() {
		{

			add(new AccomplishedCourse("234123", "׳�׳¢׳¨׳›׳•׳× ׳”׳₪׳¢׳�׳”",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳�׳ ׳�׳™׳–׳” ׳ ׳•׳�׳¨׳™׳×",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳’׳ ׳˜׳™׳§׳”", "3.5", null,
					"98"));
			add(new AccomplishedCourse("234123", "׳�׳ ׳�׳™׳–׳” ׳ ׳•׳�׳¨׳™׳×",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳�׳¢׳¨׳›׳•׳× ׳”׳₪׳¢׳�׳”",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳�׳ ׳�׳™׳–׳” ׳ ׳•׳�׳¨׳™׳×",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳�׳¢׳¨׳›׳•׳× ׳”׳₪׳¢׳�׳”",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳�׳ ׳�׳™׳–׳” ׳ ׳•׳�׳¨׳™׳×",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳�׳¢׳¨׳›׳•׳× ׳”׳₪׳¢׳�׳”",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳�׳ ׳�׳™׳–׳” ׳ ׳•׳�׳¨׳™׳×",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳�׳¢׳¨׳›׳•׳× ׳”׳₪׳¢׳�׳”",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳�׳ ׳�׳™׳–׳” ׳ ׳•׳�׳¨׳™׳×",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳�׳¢׳¨׳›׳•׳× ׳”׳₪׳¢׳�׳”",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳�׳ ׳�׳™׳–׳” ׳ ׳•׳�׳¨׳™׳×",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳�׳¢׳¨׳›׳•׳× ׳”׳₪׳¢׳�׳”",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳�׳ ׳�׳™׳–׳” ׳ ׳•׳�׳¨׳™׳×",
					"4.0", null, "85"));
			add(new AccomplishedCourse("234123", "׳�׳¢׳¨׳›׳•׳× ׳”׳₪׳¢׳�׳”",
					"4.5", null, "95"));
			add(new AccomplishedCourse("234123", "׳�׳ ׳�׳™׳–׳” ׳ ׳•׳�׳¨׳™׳×",
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
//		finish();
//		startActivity(new Intent(getActivity(), TransparentActivity.class));
		super.onListItemClick(l, v, position, id);
	}
}