package com.technion.coolie.ug.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.ExamItem;
import com.technion.coolie.ug.utils.calendar.CalendarProvider;
import com.technion.coolie.ug.utils.calendar.EventValues;

public class CalendarUtils {

	/**
	 * adds the exams of all the courses to the default calendar of this device.
	 */
	public static void addMyCourses(Context context, List<CourseItem> courses) {
		Log.d("CalendarUtils", "Adding  exams of " + courses.size()
				+ " courses to calendar!");
		CalendarProvider provider = new CalendarProvider(context);
		List<ExamItem> allExams = new ArrayList<ExamItem>();
		for (CourseItem courseItem : courses) {
			allExams = courseItem.getExams();
			for (int i = 0; i < allExams.size(); i++) {
				EventValues eventExam = new EventValues();
				if (allExams.get(i).getDate() == null
						|| examExists(provider, eventExam, context, courseItem,
								i))
					continue;
				eventExam
						.setEventStartDate(allExams.get(i).getDate().getTime());
				Calendar cal = allExams.get(i).getDate();
				cal.add(Calendar.HOUR_OF_DAY, 3);
				eventExam.setEventEndDate(cal.getTime());
				eventExam.setEventLocation(allExams.get(i).getPlace());
				eventExam.setEventTitle(constructEventTitle(context,
						courseItem, i));
				addToCalendar(eventExam, context, provider);
			}
		}
	}

	/**
	 * checks if this exam's event is already found in the calendar.
	 */
	private static boolean examExists(CalendarProvider provider,
			EventValues eventExam, Context context, CourseItem courseItem, int i) {
		return (provider.findEventsByTitle(
				constructEventTitle(context, courseItem, i)).size() > 0);
	}

	private static String constructEventTitle(Context context,
			CourseItem courseItem, int i) {
		if (i == 0)
			return courseItem.getCoursName() + " "
					+ context.getString(R.string.ug_calendar_moed_a);
		if (i == 1)
			return courseItem.getCoursName() + " "
					+ context.getString(R.string.ug_calendar_moed_b);
		else
			return "";
	}

	private static void addToCalendar(EventValues eventExam, Context context,
			CalendarProvider provider) {
		provider.addEvent(eventExam);
	}

}
