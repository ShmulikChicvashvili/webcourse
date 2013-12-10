package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.technion.coolie.ug.model.Course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CoursesAndExamsListFragment extends ListFragment {

	List<Course> coursesList = new ArrayList<Course>() {
		{
			add(new Course("234123", "׳³ן¿½׳³ֲ¢׳³ֲ¨׳³ג€÷׳³ג€¢׳³ֳ— ׳³ג€�׳³ג‚×׳³ֲ¢׳³ן¿½׳³ג€�", 4.5f, null,
					null, null, new GregorianCalendar(2014, 2, 11),
					new GregorianCalendar(2014, 3, 13), null, null, null));
			add(new Course("234123", "׳³ן¿½׳³ֲ ׳³ן¿½׳³ג„¢׳³ג€“׳³ג€� ׳³ֲ ׳³ג€¢׳³ן¿½׳³ֲ¨׳³ג„¢׳³ֳ—", 4.0f, null,
					null, null, new GregorianCalendar(2014, 2, 12),
					new GregorianCalendar(2014, 3, 14), null, null, null));
			add(new Course("234123", "׳³ג€™׳³ֲ ׳³ֻ�׳³ג„¢׳³ֲ§׳³ג€�", 3.5f, null, null, null,
					new GregorianCalendar(2014, 2, 13), new GregorianCalendar(
							2014, 3, 15), null, null, null));
			add(new Course("234141", "׳³ֲ§׳³ג€¢׳³ן¿½׳³ג€˜׳³ג„¢׳³ֲ ׳³ֻ�׳³ג€¢׳³ֲ¨׳³ג„¢׳³ֲ§׳³ג€�", 4.5f, null,
					null, null, new GregorianCalendar(2014, 2, 14),
					new GregorianCalendar(2014, 3, 16), null, null, null));
			add(new Course("234122", "׳³ג‚×׳³ג€¢׳³ֲ ׳³ֲ§׳³ֲ¦׳³ג„¢׳³ג€¢׳³ֳ— ׳³ן¿½׳³ֲ¨׳³ג€¢׳³ג€÷׳³ג€˜׳³ג€¢׳³ֳ—", 4.0f,
					null, null, null, new GregorianCalendar(2014, 2, 15),
					new GregorianCalendar(2014, 3, 17), null, null, null));
			add(new Course("999999", "׳³ן¿½׳³ֳ—'׳³ן¿½ 1", 3.5f, null, null, null,
					new GregorianCalendar(2014, 2, 16), new GregorianCalendar(
							2014, 3, 18), null, null, null));
			add(new Course("234141", "׳³ן¿½׳³ג€˜׳³ג€¢׳³ן¿½ ׳³ן¿½׳³ן¿½׳³ֲ¡׳³ג€�׳³ג„¢ ׳³ֲ ׳³ֳ—׳³ג€¢׳³ֲ ׳³ג„¢׳³ן¿½", 4.5f,
					null, null, null, new GregorianCalendar(2014, 2, 17),
					new GregorianCalendar(2014, 3, 19), null, null, null));
			add(new Course("234122", "׳³ג‚×׳³ג€¢׳³ֲ ׳³ֲ§׳³ֲ¦׳³ג„¢׳³ג€¢׳³ֳ— ׳³ן¿½׳³ֲ¨׳³ג€¢׳³ג€÷׳³ג€˜׳³ג€¢׳³ֳ—", 4.0f,
					null, null, null, new GregorianCalendar(2014, 2, 18),
					new GregorianCalendar(2014, 3, 20), null, null, null));
			add(new Course("999999", "׳³ג€˜׳³ג„¢׳³ג€¢׳³ן¿½׳³ג€¢׳³ג€™׳³ג„¢׳³ג€� 1", 3.5f, null, null,
					null, new GregorianCalendar(2014, 2, 19),
					new GregorianCalendar(2014, 3, 21), null, null, null));
			add(new Course("094412", "׳³ֲ§׳³ג€¢׳³ן¿½׳³ג€˜׳³ג„¢׳³ֲ ׳³ֻ�׳³ג€¢׳³ֲ¨׳³ג„¢׳³ֲ§׳³ג€�", 4.5f, null,
					null, null, new GregorianCalendar(2014, 2, 20),
					new GregorianCalendar(2014, 3, 21), null, null, null));
			add(new Course("104215", "׳³ג€�׳³ֲ¡׳³ֳ—׳³ג€˜׳³ֲ¨׳³ג€¢׳³ֳ—", 4.0f, null, null, null,
					new GregorianCalendar(2014, 2, 21), new GregorianCalendar(
							2014, 3, 22), null, null, null));
			add(new Course("236363", "׳³ן¿½׳³ג€˜׳³ג€¢׳³ן¿½ ׳³ן¿½׳³ן¿½׳³ֲ¡׳³ג€�׳³ג„¢ ׳³ֲ ׳³ֳ—׳³ג€¢׳³ֲ ׳³ג„¢׳³ן¿½", 3.5f,
					null, null, null, new GregorianCalendar(2014, 2, 22),
					new GregorianCalendar(2014, 3, 23), null, null, null));
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/** Creating an array adapter to store the list of countries **/
		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(inflater.getContext(),
		// android.R.layout.simple_list_item_1,countries);

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
//		finish();
//		startActivity(new Intent(getActivity(), TransparentActivity.class));
		super.onListItemClick(l, v, position, id);
	}
}