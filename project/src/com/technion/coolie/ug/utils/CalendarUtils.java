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
				if (allExams.get(i).getDate() == null)
					continue;
				eventExam
						.setEventStartDate(allExams.get(i).getDate().getTime());
				Calendar cal = allExams.get(i).getDate();
				cal.add(Calendar.HOUR_OF_DAY, 3);
				eventExam.setEventEndDate(cal.getTime());
				eventExam.setEventLocation(allExams.get(i).getPlace());
				if (i == 0)
					eventExam.setEventTitle(courseItem.getCoursName() + " "
							+ context.getString(R.string.ug_calendar_moed_a));
				if (i == 1)
					eventExam.setEventTitle(courseItem.getCoursName() + " "
							+ context.getString(R.string.ug_calendar_moed_b));
				addToCalendar(eventExam, context, provider);
			}
		}
	}

	private static void addToCalendar(EventValues eventExam, Context context,
			CalendarProvider provider) {
		provider.addEvent(eventExam);
	}
}
