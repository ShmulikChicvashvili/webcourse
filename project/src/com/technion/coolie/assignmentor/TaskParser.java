package com.technion.coolie.assignmentor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.technion.coolie.HtmlGrabber;
import com.technion.coolie.skeleton.CoolieStatus;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.text.SpannableString;
import android.util.Log;
import android.util.Pair;

public class TaskParser extends IntentService {
	 
	private static final String WORKER_THREAD = "AmWorkerThread";
	
	private String taskRegExp1 = "(HW[0-9])";
	private String taskRegExp2 = "(HW [0-9])";
	private String taskRegExp3 = "(Homework [0-9])";
	private String taskRegExp4 = "(Assignment #[0-9])";
	private String taskRegExp5 = "(Homework Assignment [0-9])";
	private String taskRegExp6 = "(Home Assignment [0-9])";
	private String taskRegExp7 = "(תרגיל בית מס' [0-9])";
	private String taskRegExp8 = "(תרגיל בית [0-9])";
	private String taskRegExp9 = "(Assignment [0-9])";
	private String taskRegExp10 = "(Wet [0-9])";
	private String taskRegExp11 = "(Dry [0-9])";
	private String dueDateRegExp = "([0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4})";
	private String dueDateTitleRegExp = "(Due date*)";

	private List<String> urls;
	private ArrayList<String> courseList;
 
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
    	
    		// need to add date check to determine the current semester (Winter\Spring) and current year
    		String urlPrefixCS = "http://webcourse.cs.technion.ac.il/";
    		String urlSuffixCS = "/Winter2013-2014/en/hw.html";
    		urls = new ArrayList<String>();
    		for (String s : courseList) {
    			urls.add(urlPrefixCS + s + urlSuffixCS);
    		}
    		fetchDataFromWeb();
    }
 
    public void fetchDataFromWeb() {
    	
    	HtmlGrabber hg = new HtmlGrabber(getApplicationContext()) {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void handleResult(String result, CoolieStatus status) {
				Document doc = Jsoup.parse(result);
				String title = doc.title();
    			String[] splitTitle = title.split(",");
    			String[] courseInfo = splitTitle[0].split("-");
    			String courseId = courseInfo[0].trim();
    			String courseName = courseInfo[1].trim();
    			
    			Element lastTable = null;
    			// Find the element which has a child node with comment <!--WebCourse:Content-->
    			// This element has a table child element which holds all the data we seek.
    			for (Element e : doc.getAllElements()) {
    				for (Node n : e.childNodes())
    					if (n instanceof Comment && (((Comment) n).getData().equals("WebCourse:Content"))) {
    						lastTable = e.select("table").first();
    					}
    			}
    			if (lastTable == null) return;
    			Element body = lastTable.child(0);
    			
    			// Each element in 'tableElements' holds the data for specific HW.
    			Elements tableElements = body.select("table:matches(" + taskRegExp1 
    					+ "|" + taskRegExp2 + "|" + taskRegExp3 + "|" + taskRegExp4 + "|" 
    					+ taskRegExp5 + "|" + taskRegExp6 + "|" + taskRegExp7 + "|" 
    					+ taskRegExp8 + "|" + taskRegExp9 + "|" + taskRegExp10 
    					+ "|" + taskRegExp11 + ")");
    			
    			ArrayList<Pair<String, String>> namesAndDates = new ArrayList<Pair<String,String>>();
    			// We add the first HW found to the list and then compare any other HW's found against the
    			// HW that already in the list.
    			if (!tableElements.isEmpty()) {
    				Element firstTable = tableElements.get(0);
    				
    				// First tr element holds the hw title.
    				Element trFirst = firstTable.select("tr").first();
    				if (trFirst != null) {
    					// First tr (row) element in the table holds the HW title.
    					String name = trFirst.text();
    					// We look for a row which contain the text "Due date". Some other rows in the table
    					// may contain dates and by looking for that specific row, we make sure we find the
    					// actual due date.
    					Elements trElements = firstTable.select("tr:matches(" 
    												+ dueDateTitleRegExp + ")"); 
    					Element dateElement = trElements.first();
    					if (dateElement != null) { 
    						Elements datesElements = dateElement.select("td:matches(" + dueDateRegExp + ")");
    						dateElement = datesElements.first();
    						// Checking if due date exists.
    						if (dateElement != null) {
    							String date = dateElement.text();
    							// In case the due date include time, remove the time.
    							date = date.split(",")[0].trim();
    							namesAndDates.add(new Pair<String, String>(name, date));
    						}
    					} else {
    						// In case due date doesnt exist, use an empty string.
    						namesAndDates.add(new Pair<String, String>(name, ""));
    					}
    				}
    				
    				// Iterate over the rest of the elements and populate the list with the latest
    				// HW found. We use a list for cases there's more than one HW with same due date.
    				for (int i = 1; i < tableElements.size(); i++) {
    					Element table = tableElements.get(i);
    					firstTable = table.child(0);
        				
        				// First tr element holds the hw title.
        				trFirst = firstTable.select("tr").first();
        				
    					if (trFirst != null) {
    						String name = trFirst.text();
    						Elements trElements = firstTable.select("tr:matches(" + dueDateTitleRegExp + ")");
    						Element dateElement = trElements.first();
    						if (dateElement != null) { 
    							Elements datesElements = dateElement.select("td:matches(" + dueDateRegExp + ")");
    							dateElement = datesElements.first();
    							// Checking if due date exists.
    							if (dateElement != null) {
    								String date = dateElement.text();
    								// In case the due date includes time, remove the time.
    								date = date.split(",")[0].trim();
    								String latestDate = namesAndDates.get(namesAndDates.size()-1).second;
    								if (latestDate.isEmpty()) {
    									// If the hw in the list has no due date, add the current hw to the list.
    									// Later we'll compare these hw's by name and find the latest.
    									namesAndDates.add(new Pair<String, String>(name, date));
    								} else {
    									// Compare the current date to the date in the list.
    									// If the current date is after the date in the list, replace it.
    									// If the current date is the same as the date in the list, add it.
    									int compare = datesCompare(latestDate, date);
    									if (compare <= 0) {
    										if (compare < 0) removePairs(namesAndDates);
    										namesAndDates.add(new Pair<String, String>(name, date));
    									}
    								}
    							}
        					} else {
        						// In case due date not exists, use an empty string and add to the list.
        						namesAndDates.add(0, new Pair<String, String>(name, ""));
        					}
    					}
    				}
    			}
    			Log.i(MainActivity.AM_TAG, courseId + " - number of hws found: " + String.valueOf(namesAndDates.size()));
    			
    			// At this point, the list contain only the latest HW found and HW's with no due date.
    			// Now, we compare the HW's titles and keep the last HW only (i.e the HW with the highest number
    			// in the title).
    			namesAndDates = compareNames(namesAndDates);
    			
    			// Adding the HW's found to DB.
    			for (Pair<String, String> p : namesAndDates) {
    				String url = "http://webcourse.cs.technion.ac.il/" + courseId;
    				TasksInfo newTask = new TasksInfo(new SpannableString(p.first), courseName, courseId, 
    						p.second, false, 0, 0, 0, url);
    				addTaskToList(newTask);
    			}
			}
		};
		for (String url : urls) {
    		// Fetch the html from web.
    		hg.getHtmlSource(url, HtmlGrabber.Account.NONE);
		}
    }
    
    private void removePairs(ArrayList<Pair<String, String>> namesAndDates) {
    	// Remove all name & date pairs excepts those which have NO due date.
    	Iterator<Pair<String, String>> iterator = namesAndDates.iterator();
    	while (iterator.hasNext()) {
    		if (!iterator.next().second.isEmpty()) iterator.remove();
    	}
    }
    
    private ArrayList<Pair<String, String>> compareNames(ArrayList<Pair<String, String>> namesAndDates) {
    	ArrayList<Pair<String, String>> newList = new ArrayList<Pair<String,String>>();
    	// First we add the last element which is most likely to be an element with an actual date
    	// (hw's with no date are added to the begining of the list).
    	if (namesAndDates.isEmpty()) return newList;
    	
    	newList.add(namesAndDates.get(namesAndDates.size()-1));
    	
    	// Iterate over the list and compare the hw's names. 
    	// In particular compare the numbers in the strings.
    	for (int i = 0; i < namesAndDates.size()-1; i++) {
    		Pair<String, String> pair1 = newList.get(newList.size()-1);
    		Pair<String, String> pair2 = namesAndDates.get(i);
    		String name1 = pair1.first;
    		String name2 = pair2.first;
    		Integer name1Number = Integer.valueOf(name1.replaceAll("[^0-9]", ""));
    		Integer name2Number = Integer.valueOf(name2.replaceAll("[^0-9]", ""));
    		if (name2Number >= name1Number) {
    			if (name2Number > name1Number) newList.clear();
    			newList.add(pair2);
    		} 
    	}
    	return newList;
    }
    
    
    // If date2 is after date1 return negative integer.
    // if date2 is before date1 return positive integer.
    // if date2 equals date1 return 0.
    private int datesCompare(String date1, String date2) {
    	
    	String[] date1Arr = fixDate(date1).split("/");
    	String[] date2Arr = fixDate(date2).split("/");
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
    
    public void addTaskToList(TasksInfo newTask) {
    	
    	ArrayList<TasksInfo> tasksList = (ArrayList<TasksInfo>) MainActivity.mAdapter.getList();
    	
    	if (tasksList.contains(newTask)) {
    		return;
    	}
    	else MainActivity.mAdapter.insertFetched(newTask);
    	
    	// Send broadcast to notify the adapter the list was updates so it would update the view.
    	Intent dataFetchedIntent = new Intent();
    	dataFetchedIntent.setAction(MainActivity.DATA_FETCHED);
    	sendBroadcast(dataFetchedIntent);
    }
}
