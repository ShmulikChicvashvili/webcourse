package com.technion.coolie.ug.gradessheet;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.HtmlParser;

public class GradesSheetFragment extends Fragment {
	ArrayList<Item> items = new ArrayList<Item>();
	ListView listview = null;
	Document doc;
	TextView avg, succes, points;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ug_activity_grades_sheet,
				container, false);
		avg = (TextView) view.findViewById(R.id.average_value);
		succes = (TextView) view.findViewById(R.id.success_percentage_value);
		points = (TextView) view.findViewById(R.id.accumulated_points_value);

		// retrieves document with html content
		new parseGradesAsync(getActivity()).execute();

		return view;
	}

	class parseGradesAsync extends AsyncTask<Void, Void, Void> {

		private ProgressDialog pdia;
		private Context context;

		public parseGradesAsync(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			pdia = new ProgressDialog(context);
			pdia.setMessage("Loading...");
			pdia.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			doc = HtmlParser.parseFromFille("grades2.html", getActivity());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			// set student details
			Elements details = doc.select("td");
			setStudentDetails(details);

			// set student grades
			setStudentGrades(doc);

			listview = (ListView) getActivity()
					.findViewById(R.id.listView_main);
			EntryAdapter adapter = new EntryAdapter(context, items);
			listview.setAdapter(adapter);
			// dismiss progress-dialog
			pdia.dismiss();
		}

		private void setStudentDetails(Elements types) {
			avg.setText(types.get(17).text());
			succes.setText(types.get(16).text() + " %");
			points.setText(types.get(15).text());
		}

		private void setStudentGrades(Document doc) {
			Elements tableElems = doc.select("table");
			// number of table elements
			int numOftableElements = tableElems.size();
			for (int i = 4; i < numOftableElements; i++) {
				Element tableElem = tableElems.get(i);
				setSingleSemesterGrades(tableElem);

			}
		}

		private void setSingleSemesterGrades(Element tableElem) {
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
				items.add(new GradesEntryItem(courseNumber + "  " + reverse,
						tdElems.get(i + 1).text(), grade));
			}
			// set table footer (semester avg + total points in semester)
			setBottomLine(tdElems.get(numOfTdElemsInTable - 3).text(), tdElems
					.get(numOfTdElemsInTable - 2).text());

		}

		private void setBottomLine(String avg, String points) {
			// replace "&nbsp;" with whitespace
			String fixedAvgString = avg.replaceAll("[\\u00A0]+", " ");
			int lastIndexOfAvg = fixedAvgString.indexOf(" ");
			String average = avg.substring(0, lastIndexOfAvg);

			items.add(new GradesFooterItem(average, points));
		}

		private void setSemesterYear(String semesterYear) {

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
			items.add(new GradesSectionItem(season + " " + foreignYear + " ("
					+ hebrewYear + ")"));
		}

		private int indexOf(Pattern pattern, String s) {
			Matcher matcher = pattern.matcher(s);
			return matcher.find() ? matcher.start() : -1;
		}

		private String yearSubstring(Pattern pattern, String s) {
			Matcher matcher = pattern.matcher(s);
			return matcher.find() ? matcher.group() : "-1";
		}

		private String reverseString(String s) {
			return (new StringBuilder(s).reverse().toString());
		}
	}

}
