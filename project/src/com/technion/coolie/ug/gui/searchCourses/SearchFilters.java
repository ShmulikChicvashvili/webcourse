package com.technion.coolie.ug.gui.searchCourses;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.util.Log;

import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.Faculty;
import com.technion.coolie.ug.model.Meeting;
import com.technion.coolie.ug.model.RegistrationGroup;
import com.technion.coolie.ug.model.Semester;

public class SearchFilters implements Serializable {

	Semester semester; // There are only three available semesters at any time.
						// you can only choose one.
	boolean hasFreePlaces;
	Faculty faculty;

	DateRange meetingDateRange;
	DateRange examADateRange;

	static class DateRange implements Serializable {

		public DateRange(Date first, Date second, String dayInWeek) {
			super();
			this.first = first;
			this.second = second;
			this.dayInWeek = dayInWeek;
		}

		public Date first;
		public Date second;
		public String dayInWeek;
		private static final long serialVersionUID = -5926846168578481161L;
	}

	public SearchFilters(final Semester semester, final boolean hasFreePlaces,
			final Faculty faculty) {
		super();
		this.semester = semester;
		this.hasFreePlaces = hasFreePlaces;
		this.faculty = faculty;
		examADateRange = null;
		meetingDateRange = null;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(final Semester semester) {
		this.semester = semester;
	}

	public boolean isHasFreePlaces() {
		return hasFreePlaces;
	}

	public void setHasFreePlaces(final boolean hasFreePlaces) {
		this.hasFreePlaces = hasFreePlaces;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(final Faculty faculty) {
		this.faculty = faculty;
	}

	public void setMeetingDateRange(DateRange meetingDateRange) {
		this.meetingDateRange = meetingDateRange;
	}

	public void setExamADateRange(DateRange examADateRange) {
		this.examADateRange = examADateRange;
		if (examADateRange != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(examADateRange.first);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			examADateRange.first = cal.getTime();
			cal.setTime(examADateRange.second);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			examADateRange.second = cal.getTime();
		}
	}

	public List<Course> filter(final List<Course> courses, final String query) {
		final List<Course> filteredList = new ArrayList<Course>();
		for (final Course course : courses)
			if (meetCriterions(course, query))
				filteredList.add(course);
		return filteredList;
	}

	private boolean meetCriterions(final Course course, final String query) {
		return (faculty == Faculty.ALL_FACULTIES || course.getFaculty() == faculty)
				&& course.getSemester().equals(semester)
				&& (!hasFreePlaces || course.hasFreePlaces())
				&& isSubstring(
						query.toLowerCase(Locale.US),
						course.getName().toLowerCase(Locale.US) + " "
								+ course.getCourseNumber())
				&& (meetingDateRange == null || hasMeetingInRange(course))
				&& (examADateRange == null || examAInRange(course));

	}

	private boolean examAInRange(Course course) {

		// Log.d("SearchFilters", "course moed A is "
		// + course.getMoedA().getTime().toString()
		// + " and we compare it with the range from "
		// + examADateRange.first.toString() + " to "
		// + examADateRange.second.toString());

		return !examADateRange.first.after(course.getMoedA().getTime())
				&& !examADateRange.second.before(course.getMoedA().getTime());
	}

	private boolean hasMeetingInRange(Course course) {
		if (course.getRegistrationGroups() == null)
			return false;

		for (Meeting meeting : getAllMeetings(course)) {
			if (meeting != null) {
				if (isMeetingInRange(meeting))
					return true;
			}

		}
		return false;
	}

	private static final String inputFormat = "HH:mm";
	private static final SimpleDateFormat inputParser = new SimpleDateFormat(
			inputFormat, Locale.US);

	private boolean isMeetingInRange(Meeting meeting) {

		if (meeting.getStartingHour() == null
				|| meeting.getEndingHour() == null)
			return false;

		Log.d("SearchFilters",
				"meeting is " + timeValue(meeting.getStartingHour()) + "to "
						+ timeValue(meeting.getEndingHour())
						+ " and we compare it with the range from "
						+ timeValue(meetingDateRange.first) + " to "
						+ timeValue(meetingDateRange.second));

		return timeValue(meetingDateRange.second) <= timeValue(meeting
				.getStartingHour())
				&& timeValue(meetingDateRange.first) >= timeValue(meeting
						.getEndingHour());

	}

	public int timeValue(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int minuters = cal.get(Calendar.MINUTE);
		return minuters + 100 * hours;

	}

	private Date parseDate(String date) {

		try {
			return inputParser.parse(date);
		} catch (java.text.ParseException e) {
			Log.d("error", "error");
			return new Date(0);
		}
	}

	public List<Meeting> getAllMeetings(Course course) {
		List<Meeting> allMeetings = new ArrayList<Meeting>();
		if (course.getRegistrationGroups() != null) {
			for (RegistrationGroup group : course.getRegistrationGroups()) {
				if (group.getLectures() != null)
					allMeetings.addAll(group.getLectures());
				if (group.getTutorials() != null)
					allMeetings.addAll(group.getTutorials());
			}
		}
		return allMeetings;
	}

	/**
	 * checks for each word in the query and checks that its in the string
	 */
	private boolean isSubstring(final String query, final String strOnCheck) {
		for (final String word : query.split(" "))
			if (strOnCheck.indexOf(word) == -1)
				return false;

		return true;
	}

	private static final long serialVersionUID = 5080644007328929764L;

	public DateRange getExamARange() {
		return examADateRange;
	}

	public DateRange getMeetingRange() {
		return meetingDateRange;
	}
}
