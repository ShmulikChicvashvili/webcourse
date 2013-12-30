package com.technion.coolie.ug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;

import com.technion.coolie.ug.calendar.CalendarSectionItem;
import com.technion.coolie.ug.gradessheet.GradesFooterItem;
import com.technion.coolie.ug.gradessheet.GradesSectionItem;
import com.technion.coolie.ug.gradessheet.SectionedListItem;
import com.technion.coolie.ug.model.AcademicCalendarEvent;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.ExamItem;

public class HtmlParser {
	private static StringBuilder response;
	private static final int FIRST_COURSE_OFFSET = 2;

	public static Document parseFromFille(final String filename,
			final Context context) {
		try {

			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(context.getAssets().open(filename),
							"ISO-8859-8"));
			response = new StringBuilder();
			String inputLine;

			while ((inputLine = reader.readLine()) != null)
				response.append(inputLine);
			reader.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		final Document doc = Jsoup.parse(response.toString());
		return doc;
	}

	/**
	 * courses and exams parsing
	 */
	public static ArrayList<CourseItem> parseCoursesAndExamsDoc(
			final Document doc) {
		final ArrayList<CourseItem> list = new ArrayList<CourseItem>();
		final Element coursesTable = doc.select("table").last();
		final Elements trElems = coursesTable.select("tr");
		final int numOfColumns = getNumOfColumns(trElems);
		for (int i = FIRST_COURSE_OFFSET; i < trElems.size(); i++)
			getCourseExam(list, trElems.get(i), numOfColumns);
		return list;
	}

	private static int getNumOfColumns(final Elements trElems) {
		return trElems.get(0).children().size()
				+ trElems.get(1).children().size();
	}

	private static void getCourseExam(final List<CourseItem> list,
			final Element trElem, final int numOfColumns) {
		final int shiftFromExamColumns = numOfColumns - 3; // number of columns
															// could
		// vary between 7 to 8
		final Elements tdElems = trElem.select("td");
		final List<ExamItem> l = new ArrayList<ExamItem>();
		final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
				Locale.getDefault());
		final Calendar calendarDate1 = Calendar.getInstance();
		final Calendar calendarDate2 = Calendar.getInstance();

		Calendar moedB = null, moedA = null;
		try {
			moedB = setExam(tdElems, sdf, calendarDate1, 0);
			moedA = setExam(tdElems, sdf, calendarDate2, 1);
		} catch (final ParseException e1) {
			e1.printStackTrace();
		}

		l.add(new ExamItem(moedA, "ulman"));
		l.add(new ExamItem(moedB, "ulman"));
		final String courseName = tdElems.get(shiftFromExamColumns).text();
		final String courseId = tdElems.get(shiftFromExamColumns + 1).text();
		// TODO: handle <br> elements inside courseName
		// Elements e = tdElems.get(shiftFromExamColumns).select("br");

		// TODO: Get points from DB by course number...
		list.add(new CourseItem(new StringBuilder(courseName).reverse()
				.toString(), courseId.substring(0, courseId.indexOf("-")),
				"3.0", l));
	}

	private static Calendar setExam(final Elements tdElems,
			final SimpleDateFormat sdf, Calendar calendarDate, final int index)
			throws ParseException {
		final String s = tdElems.get(index).text();
		if (!s.equals(""))
			calendarDate.setTime(sdf.parse(s));
		else
			calendarDate = null;
		return calendarDate;
	}

	/**
	 * calendar parsing
	 */
	public static ArrayList<SectionedListItem> parseCalendar() {
		final Document doc = parseFromFille("ug_calendar.html",
				MainActivity.context);
		final ArrayList<SectionedListItem> list = new ArrayList<SectionedListItem>();
		String month = "";
		final Elements trElems = doc.select("tr:has(td.td0)");
		for (int i = 1; i < trElems.size(); i++) {
			final Elements tdElems = trElems.get(i).select("td");
			// populate months sections
			if (!tdElems.first().text().equals(month))
				list.add(new CalendarSectionItem(tdElems.first().text()));
			setCalendarEvent(tdElems, list);
			month = tdElems.first().text();
		}
		return list;
	}

	private static void setCalendarEvent(final Elements tdElems,
			final ArrayList<SectionedListItem> list) {
		final String day = tdElems.get(1).text();
		final Calendar date = stringToCalendar(tdElems.get(2).text(),
				"dd/MM/yyyy");
		final String event = tdElems.get(6).text();
		list.add(new AcademicCalendarEvent(date, event, day));

	}

	private static Calendar stringToCalendar(final String string,
			final String format) {
		final SimpleDateFormat sdf = new SimpleDateFormat(format,
				Locale.getDefault());
		final Calendar calendarDate = Calendar.getInstance();
		try {
			calendarDate.setTime(sdf.parse(string));
		} catch (final ParseException e1) {
			e1.printStackTrace();
		}
		return calendarDate;
	}

	/**
	 * grades sheet parsing
	 */
	private static ArrayList<SectionedListItem> gradesItems = new ArrayList<SectionedListItem>();
	public static String avg, success, points;

	public static ArrayList<SectionedListItem> parseGrades(final String studentId) {
		gradesItems.clear();
		final Document doc = HtmlParser.parseFromFille("ug_grades_sheet.html",
				MainActivity.context);
		final Elements details = doc.select("td");
		setStudentDetails(details);
		// set student grades
		setStudentGrades(doc);
		return gradesItems;
	}

	private static void setStudentDetails(final Elements types) {
		avg = types.get(17).text();
		success = types.get(16).text() + " %";
		points = types.get(15).text();
	}

	private static void setStudentGrades(final Document doc) {
		final Elements tableElems = doc.select("table");
		// number of table elements
		final int numOftableElements = tableElems.size();
		for (int i = 4; i < numOftableElements; i++) {
			final Element tableElem = tableElems.get(i);
			setSingleSemesterGrades(tableElem);

		}
	}

	private static void setSingleSemesterGrades(final Element tableElem) {
		final Elements tdElems = tableElem.select("td");
		final int numOfTdElemsInTable = tdElems.size();

		// set table header (semester detatils)
		setSemesterYear(tdElems.first().text());

		for (int i = 4; i < numOfTdElemsInTable - 3; i = i + 3) {

			// replace each non-breaking space with whitespace
			final String course = tdElems.get(i + 2).text()
					.replaceAll("[\\u00A0]+", " ");

			// extracting course name from string
			final String courseName = course.substring(0,
					course.lastIndexOf(" "));

			// extracting course number from string
			final String courseNumber = course.substring(course
					.lastIndexOf(" ") + 1);

			// reversing course name
			final String reverse = reverseString(courseName);

			// check if course accomplished (if not - grade is a pure text)
			String grade = tdElems.get(i).text();
			grade = grade.matches(".*\\d.*") ? grade : reverseString(grade);
			// add grade line to list
			gradesItems.add(new AccomplishedCourse(courseNumber, reverse,
					tdElems.get(i + 1).text(), null, grade));
		}
		// set table footer (semester avg + total points in semester)
		setBottomLine(tdElems.get(numOfTdElemsInTable - 3).text(),
				tdElems.get(numOfTdElemsInTable - 2).text());

	}

	private static void setBottomLine(final String avg, final String points) {
		// replace "&nbsp;" with whitespace
		final String fixedAvgString = avg.replaceAll("[\\u00A0]+", " ");
		final int lastIndexOfAvg = fixedAvgString.indexOf(" ");
		final String average = avg.substring(0, lastIndexOfAvg);

		gradesItems.add(new GradesFooterItem(average, points));
	}

	private static void setSemesterYear(final String semesterYear) {

		// replace "&nbsp;" with whitespace
		final String fixedString = semesterYear.replaceAll("[\\u00A0]+", " ");

		// finds first digit in string
		final int firstDigitIndex = indexOf(Pattern.compile("\\d"), fixedString);

		// string of foreign year
		final String foreignYear = yearSubstring(
				Pattern.compile("[0-9]+\\/[0-9]+"), fixedString);

		final int foreignYearSize = foreignYear.length();
		final int firstIndexSeason = fixedString.lastIndexOf(" ") == -1 ? firstDigitIndex
				+ foreignYearSize
				: firstDigitIndex + foreignYearSize + 1;

		// season string
		String season = fixedString.substring(firstIndexSeason,
				fixedString.length());

		// Hebrew year string
		final int lastIndexHebrewYear = fixedString.indexOf(" ") == -1 ? firstDigitIndex
				: fixedString.indexOf(" ") - 1;
		String hebrewYear = fixedString.substring(1,
				Math.min(firstDigitIndex - 1, lastIndexHebrewYear));

		// reverse of Hebrew strings
		season = reverseString(season);
		hebrewYear = reverseString(hebrewYear);

		// add section to list
		gradesItems.add(new GradesSectionItem(season + " " + foreignYear + " ("
				+ hebrewYear + ")"));
	}

	private static int indexOf(final Pattern pattern, final String s) {
		final Matcher matcher = pattern.matcher(s);
		return matcher.find() ? matcher.start() : -1;
	}

	private static String yearSubstring(final Pattern pattern, final String s) {
		final Matcher matcher = pattern.matcher(s);
		return matcher.find() ? matcher.group() : "-1";
	}

	private static String reverseString(final String s) {
		return new StringBuilder(s).reverse().toString();
	}
}
