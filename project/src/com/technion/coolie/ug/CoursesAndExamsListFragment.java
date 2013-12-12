package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.technion.coolie.ug.model.Course;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CoursesAndExamsListFragment extends ListFragment {

	List<Course> coursesList = new ArrayList<Course>(){{
 	   add(new Course("234123","מערכות הפעלה",4.5f,null, null, null, new GregorianCalendar(2014, 2, 11), new GregorianCalendar(2014,3,13),null,null,null));
 	   add(new Course("234123","אנליזה נומרית",4.0f,null, null, null, new GregorianCalendar(2014,2,12), new GregorianCalendar(2014,3,14),null,null,null));
 	   add(new Course("234123","גנטיקה",3.5f,null, null, null, new GregorianCalendar(2014,2,13), new GregorianCalendar(2014,3,15),null,null,null));
 	   add(new Course("234141","קומבינטוריקה",4.5f,null, null, null, new GregorianCalendar(2014,2,14), new GregorianCalendar(2014,3,16),null,null,null));
 	   add(new Course("234122","פונקציות מרוכבות",4.0f,null, null, null, new GregorianCalendar(2014,2,15), new GregorianCalendar(2014,3,17),null,null,null));
 	   add(new Course("999999","את'מ 1",3.5f,null, null, null, new GregorianCalendar(2014,2,16), new GregorianCalendar(2014,3,18),null,null,null));
 	   add(new Course("234141","מבוא למסדי נתונים",4.5f,null, null, null, new GregorianCalendar(2014,2,17), new GregorianCalendar(2014,3,19),null,null,null));
 	   add(new Course("234122","פונקציות מרוכבות",4.0f,null, null, null, new GregorianCalendar(2014,2,18), new GregorianCalendar(2014,3,20),null,null,null));
 	   add(new Course("999999","ביולוגיה 1",3.5f,null, null, null, new GregorianCalendar(2014,2,19), new GregorianCalendar(2014,3,21),null,null,null));
 	   add(new Course("094412","קומבינטוריקה",4.5f,null, null, null, new GregorianCalendar(2014,2,20), new GregorianCalendar(2014,3,21),null,null,null));
 	   add(new Course("104215","הסתברות",4.0f,null, null, null, new GregorianCalendar(2014,2,21), new GregorianCalendar(2014,3,22),null,null,null));
 	   add(new Course("236363","מבוא למסדי נתונים",3.5f,null, null, null, new GregorianCalendar(2014,2,22), new GregorianCalendar(2014,3,23),null,null,null));
 	}};

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
}