package com.technion.coolie.assignmentor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.technion.coolie.assignmentor.MainActivity.MyAdapter;

import android.os.AsyncTask;
import android.text.SpannableString;
import android.util.Log;

public class TaskParser {
	
	private MyAdapter mAdapter;
	
	private String taskName;
	private String courseName;
	private String courseId;
	private String dueDate;
	
	private String taskRegExp1 = "(HW[0-9])$";
	private String taskRegExp2 = "(HW [0-9])$";
	private String taskRegExp3 = "(Homework [0-9])$";
	private String taskRegExp4 = "(Assignment #[0-9])$";
	private String taskRegExp5 = "(Homework Assignment [0-9])$";
	private String taskRegExp6 = "(Home Assignment [0-9])$";
	private String taskRegExp7 = "(תרגיל בית מס' [0-9])$";
	private String taskRegExp8 = "(תרגיל בית [0-9])$";
	private String dueDateRegExp = "([0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4})";
	
	
	
	// parameter 'url' will be a string holding the html data received from the Skeleton teem service.
	// for now, this class gets a url as parameter, fetches the data, parse it, and adding new tasks
	// using the adapter instance received as second parameter.
	public TaskParser(String url, MyAdapter adapter) {
		
		mAdapter = adapter;
		
		FetchWebSite fetch = new FetchWebSite();
		fetch.execute(url);
	}
	
	
	// Might be used later.
//	public TasksInfo getData() {
////		String[] data = new String[] { taskTitle, dueDate };
////		return data;
//		return taskName;
//	}
	
	private class FetchWebSite extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			
			try {
				Log.i("TasksList", "Fetching website from url: " + params[0]);
				Document doc = Jsoup.connect(params[0]).get();
				// Extracting the course id and name from the title.
				String title = doc.title();
				String[] splitTitle = title.split(",");
				String[] courseInfo = splitTitle[0].split("-");
				courseId = courseInfo[0].trim();
				courseName = courseInfo[1].trim();
				Log.i("TasksList", "Course Id: " + courseId + " Course Name: " + courseName);
				
				
				// Extracting the newest task name.
				Elements taskNameElements = doc.select(":matchesOwn(" + taskRegExp1 + "|" + taskRegExp2 
						+ "|" + taskRegExp3 + "|" + taskRegExp4 + "|" 
						+ taskRegExp5 + "|" + taskRegExp6 + "|" + taskRegExp7 + "|" 
						+ taskRegExp8 + ")");
				for (Element e : taskNameElements) {
					taskName = e.text();
				}
				Log.i("TasksList", "Last task found: " + taskName);
				
				// Extracting the due date of the newest task.
				Elements dueDateElements = doc.select(":matchesOwn(" + dueDateRegExp + ")");
				for (Element e : dueDateElements) {
					dueDate = e.text().split(",")[0];
				}
				Log.i("TasksList", "Last due date found: " + dueDate);
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
				Log.i("TasksList", "Caught IOException while trying to get data from: " + params[0]);
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				mAdapter.insert(-1,new SpannableString(taskName), courseName, courseId, dueDate, false);
			} else {
				Log.i("TasksList", "onPostExecute -> IOException raised. No new tasks in the list");
			}
		}
	}

}
