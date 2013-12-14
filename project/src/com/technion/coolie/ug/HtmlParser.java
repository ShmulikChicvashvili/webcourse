package com.technion.coolie.ug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
<<<<<<< HEAD
import java.util.regex.Matcher;
import java.util.regex.Pattern;
=======
>>>>>>> 987aeac0cdba3f1db3718c6518fb4d1f97e27f55

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;

import com.technion.coolie.ug.coursesAndExams.CourseItem;
import com.technion.coolie.ug.coursesAndExams.ExamItem;
<<<<<<< HEAD
import com.technion.coolie.ug.gradessheet.GradesFooterItem;
import com.technion.coolie.ug.gradessheet.GradesSectionItem;
import com.technion.coolie.ug.gradessheet.Item;
import com.technion.coolie.ug.model.AccomplishedCourse;
=======
import com.technion.coolie.ug.gradessheet.Item;
>>>>>>> 987aeac0cdba3f1db3718c6518fb4d1f97e27f55

public class HtmlParser {
	private static StringBuilder response;
	private static final int FIRST_COURSE_OFFSET = 2;

	public static Document parseFromFille(String filename, Context context) {
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					context.getAssets().open(filename), "ISO-8859-8"));
			response = new StringBuilder();
			String inputLine;

			while ((inputLine = reader.readLine()) != null)
				response.append(inputLine);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = Jsoup.parse(response.toString());
		return doc;
	}

	public static ArrayList<CourseItem> parseCoursesAndExamsDoc(Document doc) {
		ArrayList<CourseItem> list = new ArrayList<CourseItem>();
		Element coursesTable = doc.select("table").last();
		Elements trElems = coursesTable.select("tr");
		int numOfColumns = getNumOfColumns(trElems);
		for (int i = FIRST_COURSE_OFFSET; i < trElems.size(); i++) {
			getCourseExam(list, trElems.get(i), numOfColumns);
		}
		return list;
	}

	private static int getNumOfColumns(Elements trElems) {
		return (trElems.get(0).children().size() + trElems.get(1).children()
				.size());
	}

	private static void getCourseExam(List<CourseItem> list, Element trElem,
			int numOfColumns) {
		int shiftFromExamColumns = numOfColumns - 3; // number of columns could
														// vary between 7 to 8
		Elements tdElems = trElem.select("td");
		List<ExamItem> l = new ArrayList<ExamItem>();
//		String[] moedB = tdElems.get(0).text().split(".");
//		String[] moedA = tdElems.get(1).text().split(".");
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Calendar calendarDate1 = Calendar.getInstance();
		Calendar calendarDate2 = Calendar.getInstance();

		Calendar moedB = null, moedA=null;
		try {
			calendarDate1.setTime(sdf.parse(tdElems.get(0).text()));
			 moedB = calendarDate1;
			calendarDate2.setTime(sdf.parse(tdElems.get(1).text())); 
			 moedA = calendarDate2;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		
		l.add(new ExamItem(moedA, "ulman"));
		l.add(new ExamItem(moedB, "ulman"));
		String courseName = tdElems.get(shiftFromExamColumns).text();
		String courseId = tdElems.get(shiftFromExamColumns + 1).text();
		// TODO: handle <br> elements inside courseName
		Elements e = tdElems.get(shiftFromExamColumns).select("br");

		// TODO: Get points from DB by course number...
		list.add(new CourseItem(new StringBuilder(courseName).reverse()
				.toString(), courseId.substring(0, courseId.indexOf("-")),
				"3.0", l));

	}

	public static ArrayList<Item> parseCalendar() {
		Document doc = parseFromFille("calendar.html", MainActivity.context);
		
		Elements tr = doc.select("tr:has(td.td9:contains(true))");
		System.out.println(tr.size());
//		Elements td = tr.first().select("td");
//		int i = 0;
//		for (Element elem:td){
//			System.out.println("==========  " + (i++) + "  ==========");
//			System.out.println(elem.text());
//		}
		return null;
	}
<<<<<<< HEAD
	
	// grades sheet parsing
	
	
		private static ArrayList<Item> gradesItems = new ArrayList<Item>();
		
		
		public static ArrayList<Item> parseGrades(String studentId)
		{
			Document doc = HtmlParser.parseFromFille("grades2.html", MainActivity.context);
			Elements details = doc.select("td");

			// set student grades
			setStudentGrades(doc);
			return gradesItems;
		}
		
		
		

		private static void setStudentGrades(Document doc) {
			Elements tableElems = doc.select("table");
			// number of table elements
			int numOftableElements = tableElems.size();
			for (int i = 4; i < numOftableElements; i++) {
				Element tableElem = tableElems.get(i);
				setSingleSemesterGrades(tableElem);

			}
		}

		private static void setSingleSemesterGrades(Element tableElem) {
			Elements tdElems = tableElem.select("td");
			int numOfTdElemsInTable = tdElems.size();

			// set table header (semester detatils)
			setSemesterYear(tdElems.first().text());

			for (int i = 4; i < numOfTdElemsInTable - 3; i = i + 3) {

				// replace each non-breaking space with whitespace
				String course = tdElems.get(i + 2).text()
						.replaceAll("[\\u00A0]+", " ");

				// extracting course name from string
				String courseName = course
						.substring(0, course.lastIndexOf(" "));

				// extracting course number from string
				String courseNumber = course
						.substring(course.lastIndexOf(" ") + 1);

				// reversing course name
				String reverse = reverseString(courseName);

				// check if course accomplished (if not - grade is a pure text)
				String grade = tdElems.get(i).text();
				grade = grade.matches(".*\\d.*") ? grade : reverseString(grade);
				// add grade line to list
//				items.add(new GradesEntryItem(courseNumber, reverse, tdElems
//						.get(i + 1).text(), grade));
				gradesItems.add(new AccomplishedCourse(courseNumber, reverse, tdElems
						.get(i + 1).text(), null, grade));
			}
			// set table footer (semester avg + total points in semester)
			setBottomLine(tdElems.get(numOfTdElemsInTable - 3).text(), tdElems
					.get(numOfTdElemsInTable - 2).text());

		}

		private static void setBottomLine(String avg, String points) {
			// replace "&nbsp;" with whitespace
			String fixedAvgString = avg.replaceAll("[\\u00A0]+", " ");
			int lastIndexOfAvg = fixedAvgString.indexOf(" ");
			String average = avg.substring(0, lastIndexOfAvg);

			gradesItems.add(new GradesFooterItem(average, points));
		}

		private static void setSemesterYear(String semesterYear) {

			// replace "&nbsp;" with whitespace
			String fixedString = semesterYear.replaceAll("[\\u00A0]+", " ");

			// finds first digit in string
			int firstDigitIndex = indexOf(Pattern.compile("\\d"), fixedString);

			// string of foreign year
			String foreignYear = yearSubstring(
					Pattern.compile("[0-9]+\\/[0-9]+"), fixedString);

			int foreignYearSize = foreignYear.length();
			int firstIndexSeason = (fixedString.lastIndexOf(" ") == -1) ? firstDigitIndex
					+ foreignYearSize
					: firstDigitIndex + foreignYearSize + 1;

			// season string
			String season = fixedString.substring(firstIndexSeason,
					fixedString.length());

			// Hebrew year string
			int lastIndexHebrewYear = (fixedString.indexOf(" ") == -1) ? firstDigitIndex
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

		private static int indexOf(Pattern pattern, String s) {
			Matcher matcher = pattern.matcher(s);
			return matcher.find() ? matcher.start() : -1;
		}

		private static String yearSubstring(Pattern pattern, String s) {
			Matcher matcher = pattern.matcher(s);
			return matcher.find() ? matcher.group() : "-1";
		}

		private static String reverseString(String s) {
			return (new StringBuilder(s).reverse().toString());
		}
}
=======
}
>>>>>>> 987aeac0cdba3f1db3718c6518fb4d1f97e27f55
