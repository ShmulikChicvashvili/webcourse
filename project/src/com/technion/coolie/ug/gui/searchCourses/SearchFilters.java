package com.technion.coolie.ug.gui.searchCourses;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.util.Log;
import android.util.Pair;

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

	Pair<Date, Date> meetingDateRange;
	Pair<Date, Date> examADateRange;

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

	// TODO make gui to set this.

	public void setMeetingDateRange(Pair<Date, Date> meetingDateRange) {
		this.meetingDateRange = meetingDateRange;
	}

	public void setExamADateRange(Pair<Date, Date> examADateRange) {
		this.examADateRange = examADateRange;
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
				&& (meetingDateRange == null || hasMeetingInRange(course,
						meetingDateRange))
				&& (examADateRange == null || examAInRange(course,
						examADateRange));

	}

	private boolean examAInRange(Course course, Pair<Date, Date> examADateRange2) {
		return (course.getMoedA().after(examADateRange2.first) && course
				.getMoedA().before(examADateRange2.second));
	}

	private boolean hasMeetingInRange(Course course, Pair<Date, Date> dateRange2) {
		if (course.getRegistrationGroups() == null)
			return false;

		for (Meeting meeting : getAllMeetings(course)) {
			if (meeting != null) {
				if (isMeetingInRange(dateRange2, meeting))
					return true;
			}

		}
		return false;
	}

	// setMeetingRange TODO

	private static final String inputFormat = "HH:mm";
	private static final SimpleDateFormat inputParser = new SimpleDateFormat(
			inputFormat, Locale.US);

	private boolean isMeetingInRange(Pair<Date, Date> dateRange2,
			Meeting meeting) {
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(dateRange2.first);
		// Calendar cal2 = Calendar.getInstance();
		// cal.setTime(dateRange2.second);

		// int hour = now.get(Calendar.HOUR);
		// int minute = now.get(Calendar.MINUTE);

		// date = parseDate(hour + ":" + minute);
		// Date dateStart = parseDate(inputParser.format(dateRange2.first));
		// Date dateEnd = parseDate(inputParser.format(dateRange2.second));
		if (meeting.getStartingHour() == null
				|| meeting.getEndingHour() == null)
			return false;

		return (dateRange2.first.before(meeting.getStartingHour()) && dateRange2.second
				.after(meeting.getEndingHour()));

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

	public Pair<Date, Date> getExamARange() {
		return examADateRange;
	}

	public Pair<Date, Date> getMeetingRange() {
		return meetingDateRange;
	}
}
