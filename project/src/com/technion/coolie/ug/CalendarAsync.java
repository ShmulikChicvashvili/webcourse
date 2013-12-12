package com.technion.coolie.ug;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class CalendarAsync extends AsyncTask<Void, Void, Void> {
	private ProgressDialog pdia;
	private Context context;
	private Document doc;

	public CalendarAsync(Context context) {
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
		doc = HtmlParser.parseFromFille("calendar.html", context);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		// set student details
		Elements details = doc.select("td.td6");
		for (Element elem: details){
			System.out.println(elem.text());
		}
//		setStudentDetails(details);

		// set student grades
//		setStudentGrades(doc);

//		listview = (ListView) getActivity()
//				.findViewById(R.id.listView_main);
//		EntryAdapter adapter = new EntryAdapter(context, items);
//		listview.setAdapter(adapter);
		// dismiss progress-dialog
		pdia.dismiss();
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
