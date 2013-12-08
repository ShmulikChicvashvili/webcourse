package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.technion.coolie.ug.Enums.DayOfWeek;
import com.technion.coolie.ug.model.AcademicCalendarEvent;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class AcademicCalendarListFragment extends ListFragment {
 
    
    List<AcademicCalendarEvent> coursesList = new ArrayList<AcademicCalendarEvent>(){{
    	
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 1),"ד' חנוכה - אין לימודים"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 2),"ה' חנוכה - אין לימודים"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 3),"טקס קבלת פנים - סגל חדש"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 4),"ו' חנוכה - אין לימודים"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 5),"ז' חנוכה - אין לימודים"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 6),"ח' חנוכה - אין לימודים"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 7),"טקס קבלת פנים - סגל חדש"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 8),"צום י' בטבת - אין  בחינות"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 9),"טקס פרס משה ינאי"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 10),"יריד תעסוקה"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 11),"טקס מרצים מצטיינים"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 12),"טקס מצטייני נשיא"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 13),"קונצרט חגיגי לסיום"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 14),"יום אחרון ללימודי ס' חורף"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 15),"אין לימודים"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 16),"אין לימודים"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 17),"תחילת בחינות ס' חורף"));
    	add(new AcademicCalendarEvent(DayOfWeek.SUNDAY, new GregorianCalendar(2013, 11, 18),"סיום בחינות ס' חורף"));
    	}};
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        
        final AcademicCalendarFragmentListAdapter adapter = new AcademicCalendarFragmentListAdapter(inflater.getContext(), coursesList);
        
        setListAdapter(adapter);
 
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}