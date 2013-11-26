package com.technion.coolie.assignmentor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.IntentService;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.text.SpannableString;
import android.util.Log;

public class TaskParser extends IntentService {
	 
	private static final String WORKER_THREAD = "AmWorkerThread";
	
	private String taskRegExp1 = "(HW[0-9])$";
	private String taskRegExp2 = "(HW [0-9])$";
	private String taskRegExp3 = "(Homework [0-9])$";
	private String taskRegExp4 = "(Assignment #[0-9])$";
	private String taskRegExp5 = "(Homework Assignment [0-9])$";
	private String taskRegExp6 = "(Home Assignment [0-9])$";
	private String taskRegExp7 = "(תרגיל בית מס' [0-9])$";
	private String taskRegExp8 = "(תרגיל בית [0-9])$";
	private String dueDateRegExp = "([0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4})";
    

	private List<String> urls;
	private ArrayList<String> courseList;
    private List<TasksInfo> fetchedTasks;
 
    public TaskParser() {
    	super(WORKER_THREAD);
    }
    
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    	courseList = intent.getStringArrayListExtra(MainActivity.COURSE_LIST);
    	// If course list is empty stop service (no implicit call to stopSelf() is needed,
    	// all taking care of by super (IntentService).
    	if (courseList == null || courseList.size() == 0) return;
    	
    	if (isConnected()) {
    		
    		// need to add date check to determine the current semester (Winter\Spring) and current year
    		String urlPrefixCS = "http://webcourse.cs.technion.ac.il/";
    		String urlSuffixCS = "/Winter2013-2014/en/hw.html";
    		urls = new ArrayList<String>();
    		for (String s : courseList) {
    			urls.add(urlPrefixCS + s + urlSuffixCS);
    		}
    		fetchDataFromWeb();
    	} else {
    		Log.i(MainActivity.AM_TAG, "No internet connection!");
    	}
    }
 
    public void fetchDataFromWeb() {
    	try {
    		fetchedTasks = new ArrayList<TasksInfo>();
    		for (String url : urls) {
    			// Fetch the html from web.
    			Document doc = Jsoup.connect(url).get();
    			// Course name and ID are in the title element (in WebCourse).
    			String title = doc.title();
    			String[] splitTitle = title.split(",");
    			String[] courseInfo = splitTitle[0].split("-");
    			String courseId = courseInfo[0].trim();
    			String courseName = courseInfo[1].trim();
    			Log.i(MainActivity.AM_TAG, "Course Id: " + courseId + " Course Name: " + courseName);
    			
    			// Extracting the newest task name.
    			Elements taskNameElements = doc.select(":matchesOwn(" + taskRegExp1 + "|" + taskRegExp2 
    					+ "|" + taskRegExp3 + "|" + taskRegExp4 + "|" 
    					+ taskRegExp5 + "|" + taskRegExp6 + "|" + taskRegExp7 + "|" 
    					+ taskRegExp8 + ")");
    			String taskName = "";
    			for (Element e : taskNameElements) {
    				String tmp = e.text();
    				if (taskName.compareTo(tmp) <= 0) {
    					taskName = tmp;
    				}
    			}
    			Log.i(MainActivity.AM_TAG, "Last task found: " + taskName);
    			
    			// Extracting the due date of the newest task.
    			Elements dueDateElements = doc.select(":matchesOwn(" + dueDateRegExp + ")");
    			String dueDate = "";
    			for (Element e : dueDateElements) {
    				String tmp = e.text().split(",")[0];
    				tmp = fixDate(tmp);
    				if (datesCompare(dueDate, tmp) <= 0 )
    				dueDate = tmp;
    			}
    			Log.i(MainActivity.AM_TAG, "Last due date found: " + dueDate);
    			TasksInfo newTask = new TasksInfo(new SpannableString(taskName), courseName, courseId, dueDate);
    			newTask.difficulty = 0;
    			newTask.importance = 0;
    			newTask.progress = 0;
    			fetchedTasks.add(newTask);
    		}
    		populateList();
    	} catch (Exception e) {
			e.printStackTrace();
			Log.i(MainActivity.AM_TAG, "Caught IOException while trying to get data from web");
		}
    }
    
    private int datesCompare(String date1, String date2) {
    	
    	String[] date1Arr = date1.split("/");
    	String[] date2Arr = date2.split("/");
    	if (date1Arr.length < 3) return -1;
    	else if (date2Arr.length < 3) return 1;
    	int year = date1Arr[2].compareTo(date2Arr[2]);
	    if (year == 0) {
		   int month = date1Arr[1].compareTo(date2Arr[1]);
		   if (month == 0) {
			   return date1Arr[0].compareTo(date2Arr[0]);
		   } else {
		  	   return month;
		   }
	    } else {
		    return year;
  	    }
    }
    
    private String fixDate(String date) {
    	String[] dateArr = date.split("/");
    	String day = dateArr[0];
    	String month = dateArr[1];
    	String year = dateArr[2];
    	if (day.length() != 2) day = "0" + day;
    	if (month.length() != 2) month = "0" + month;
    	if (year.length() == 2) year = "20" + year;
    	return day + "/" + month + "/" + year;
    }
    
    public void populateList() {
    	
    	ArrayList<TasksInfo> tasksList = MainActivity.mAdapter.getList();
    	
    	Iterator<TasksInfo> iterator = fetchedTasks.iterator();
    	while (iterator.hasNext()) {
    		if (tasksList.contains(iterator.next())) {
    			// Remove all tasks that already in the tasks list.
    			iterator.remove();
    		} 
    	}
    	MainActivity.mAdapter.insertFetched(fetchedTasks);
    	
    	// Send broadcast to notify the adapter the list was updates so it would update the view.
    	Log.i(MainActivity.AM_TAG, "Sending DATA_FETCHED broadcast!");
    	Intent dataFetchedIntent = new Intent();
    	dataFetchedIntent.setAction(MainActivity.DATA_FETCHED);
    	sendBroadcast(dataFetchedIntent);
    }
    
    // testing network availability
 	private boolean isNetworkAvailable() {
 		ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
 		NetworkInfo mobileNetworkInfo = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
 		NetworkInfo wifiNetworkInfo = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
 		return ((mobileNetworkInfo == null ? false : mobileNetworkInfo.isAvailable()) || 
 				(wifiNetworkInfo == null ? false : wifiNetworkInfo.isAvailable()));
 	}
 	
 	// testing data availability
 	private boolean isDataAvailable() {
 		ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
 		NetworkInfo mobileNetworkInfo = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
 		NetworkInfo wifiNetworkInfo = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
 		return ((mobileNetworkInfo == null ? false : mobileNetworkInfo.isConnected()) || 
 				(wifiNetworkInfo == null ? false : wifiNetworkInfo.isConnected()));
 	}
 	
 	private boolean isConnected() {
 		return (isNetworkAvailable() && isDataAvailable());
 	}
}
