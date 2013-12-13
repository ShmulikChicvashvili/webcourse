package com.technion.coolie.ug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;

import com.technion.coolie.ug.coursesAndExams.CourseItem;

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
		List<String> l = new ArrayList<String>();
		l.add(tdElems.get(0).text());
		l.add(tdElems.get(1).text());
		String courseName = tdElems.get(shiftFromExamColumns).text();
		String courseId = tdElems.get(shiftFromExamColumns + 1).text();
		// TODO: handle <br> elements inside courseName
		Elements e = tdElems.get(shiftFromExamColumns).select("br");

		// TODO: Get points from DB by course number...
		list.add(new CourseItem(new StringBuilder(courseName).reverse()
				.toString(), courseId.substring(0, courseId.indexOf("-")),
				"3.0", l));

	}
}
