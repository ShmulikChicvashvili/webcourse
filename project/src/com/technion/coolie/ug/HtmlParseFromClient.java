package com.technion.coolie.ug;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.actionbarsherlock.R;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.ExamItem;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.StudentDetails;
import com.technion.coolie.ug.model.UGLoginObject;
import com.technion.coolie.ug.db.UGDatabase;

public class HtmlParseFromClient {

	// holds current semester
	public static Semester currentSemester;

	/**
	 * Gets current semesters
	 * 
	 * @return array of current semesters
	 */
	static public Semester[] getSemesters(Document ug) {
		Semester[] semesters = new Semester[3];
		// TODO: Matvey - Call you get method inside Jsoup.parse(...)
		// in your get use the following url:
		// http://ug.technion.ac.il/rishum/search.php
		//Document ug = Jsoup.parse("CALL YOUR GET METHOD HERE");
		Elements inputs = ug.select("input[type=radio]");
		Element checked = inputs.select("input[checked= ]").first();
		for (int i = 0; i < inputs.size(); i++) {
			semesters[i] = stringToSemester(inputs.get(i).val());
		}
		currentSemester = stringToSemester(checked.val());
		return semesters;
	}

	public static Semester stringToSemester(String s) {
		int year = Integer.valueOf(s.substring(0, 4));
		SemesterSeason ss = null;
		String season = s.substring(4, 6);
		if (season.equals("01"))
			ss = SemesterSeason.WINTER;
		if (season.equals("02"))
			ss = SemesterSeason.SPRING;
		if (season.equals("03"))
			ss = SemesterSeason.SUMMER;
		return (new Semester(year, ss));

	}

	private String examsUrl = "http://techmvs.technion.ac.il:100/cics/wmn/wmnnut02";
	
	private final static int FIRST_COURSE_OFFSET = 2;

	/**
	 * Get student's courses and exams
	 * 
	 * @param student
	 * @param semester
	 * @return
	 */
	static public List<CourseItem> parseStudentExams(Document doc, Semester semester) {
		List<CourseItem> coursesAndExamsList = new ArrayList<CourseItem>();
		final Elements coursesTable = doc.select("table");
		// in case we aren't registered to any course
		if (coursesTable.size() == 3) {
			System.out.println("no registerd courses");
			return coursesAndExamsList;
		}
		// else - get exams
		final Elements trElems = coursesTable.last().select("tr");
		final int numOfColumns = getNumOfColumns(trElems);
		for (int i = FIRST_COURSE_OFFSET; i < trElems.size(); i++)
			getCourseExam(coursesAndExamsList, trElems.get(i), numOfColumns, semester);
		return coursesAndExamsList;

	}

	private static int getNumOfColumns(final Elements trElems) {
		return trElems.get(0).children().size()
				+ trElems.get(1).children().size();
	}

	static private void getCourseExam(List<CourseItem> coursesAndExamsList, final Element trElem,
			final int numOfColumns, Semester semester) {
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

		// UGDatabase.getInstance
		coursesAndExamsList.add(new CourseItem(new StringBuilder(courseName)
				.reverse().toString(), courseId.substring(0,
				courseId.indexOf("-")), "3.0", l, courseId.substring(courseId
				.indexOf("-")+1), semester));
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

	public StudentDetails getStudentDetails(String username, String password) {
		StudentDetails studentDetailsList;
		// TODO: Matvey - firstly need to get real url (like done in the
		// following commented line with JSOUP). Send this get request to the
		// following url:
		// "http://techmvs.technion.ac.il:80/cics/wmn/wmngrad?ORD=1"
		/* Document doc = Jsoup.connect(url).get(); */

		Document doc = Jsoup.parse("CALL YOUR GET METHOD HERE");
		String gradesUrl = doc.select("form").first().attr("action");

		// TODO: Matvey - after you get the real url (which will be saved in
		// "gradesUrl", post request...like in following commented line:
		/* gradesHtmlPost(url, username, password) */

		doc = Jsoup.parse("CALL YOUR POST METHOD HERE");
		final Elements details = doc.select("table").get(2).select("td");
		studentDetailsList = new StudentDetails(details.get(5).text(), details
				.get(4).text(), details.get(3).text());
		return studentDetailsList;
	}

	public static boolean handleRegistrationRequest(Document doc) {
		if (doc == null || !doc.select("img[alt*=Rejected]").isEmpty()) {
			return false;
		}
		if (!doc.select("img[alt*=Accepted]").isEmpty()) {
			return true;
		}

		return false;
	}

}
