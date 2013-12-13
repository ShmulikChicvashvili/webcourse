package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.technion.coolie.ug.Enums.DayOfWeek;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.AcademicCalendarEvent;

public class AcademicCalendarListFragment extends ListFragment {

	List<AcademicCalendarEvent> coursesList = new ArrayList<AcademicCalendarEvent>() {
		{

			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 1),
					"׳“' ׳—׳ ׳•׳›׳” - ׳�׳™׳� ׳�׳™׳�׳•׳“׳™׳�"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 2),
					"׳”' ׳—׳ ׳•׳›׳” - ׳�׳™׳� ׳�׳™׳�׳•׳“׳™׳�"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 3),
					"׳˜׳§׳¡ ׳§׳‘׳�׳× ׳₪׳ ׳™׳� - ׳¡׳’׳� ׳—׳“׳©"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 4),
					"׳•' ׳—׳ ׳•׳›׳” - ׳�׳™׳� ׳�׳™׳�׳•׳“׳™׳�"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 5),
					"׳–' ׳—׳ ׳•׳›׳” - ׳�׳™׳� ׳�׳™׳�׳•׳“׳™׳�"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 6),
					"׳—' ׳—׳ ׳•׳›׳” - ׳�׳™׳� ׳�׳™׳�׳•׳“׳™׳�"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 7),
					"׳˜׳§׳¡ ׳§׳‘׳�׳× ׳₪׳ ׳™׳� - ׳¡׳’׳� ׳—׳“׳©"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 8),
					"׳¦׳•׳� ׳™' ׳‘׳˜׳‘׳× - ׳�׳™׳�  ׳‘׳—׳™׳ ׳•׳×"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 9),
					"׳˜׳§׳¡ ׳₪׳¨׳¡ ׳�׳©׳” ׳™׳ ׳�׳™"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 10),
					"׳™׳¨׳™׳“ ׳×׳¢׳¡׳•׳§׳”"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 11),
					"׳˜׳§׳¡ ׳�׳¨׳¦׳™׳� ׳�׳¦׳˜׳™׳™׳ ׳™׳�"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 12),
					"׳˜׳§׳¡ ׳�׳¦׳˜׳™׳™׳ ׳™ ׳ ׳©׳™׳�"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 13),
					"׳§׳•׳ ׳¦׳¨׳˜ ׳—׳’׳™׳’׳™ ׳�׳¡׳™׳•׳�"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 14),
					"׳™׳•׳� ׳�׳—׳¨׳•׳� ׳�׳�׳™׳�׳•׳“׳™ ׳¡' ׳—׳•׳¨׳£"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 15),
					"׳�׳™׳� ׳�׳™׳�׳•׳“׳™׳�"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 16),
					"׳�׳™׳� ׳�׳™׳�׳•׳“׳™׳�"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 17),
					"׳×׳—׳™׳�׳× ׳‘׳—׳™׳ ׳•׳× ׳¡' ׳—׳•׳¨׳£"));
			add(new AcademicCalendarEvent(DayOfWeek.SUNDAY,
					new GregorianCalendar(2013, 11, 18),
					"׳¡׳™׳•׳� ׳‘׳—׳™׳ ׳•׳× ׳¡' ׳—׳•׳¨׳£"));
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final AcademicCalendarFragmentListAdapter adapter = new AcademicCalendarFragmentListAdapter(
				inflater.getContext(), coursesList);
		setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		/*Intent intent = new Intent(getActivity(), TransparentActivity.class);
		Bundle b = new Bundle();
		b.putString("key", "gradesSheetLayout");
		intent.putExtras(b);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);*/
		
//		new CalendarAsync(getActivity()).execute();
		
		UGDatabase.INSTANCE.getStudentCourses(SemesterSeason.WINTER);
	}

}