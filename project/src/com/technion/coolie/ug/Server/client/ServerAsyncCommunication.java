package com.technion.coolie.ug.Server.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.technion.coolie.server.ug.ReturnCodesUg;
import com.technion.coolie.server.ug.api.UgFactory;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.Server.ServerCourse;
import com.technion.coolie.ug.calendar.AcademicCalendarListFragment;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.gradessheet.GradesSheetListFragment;
import com.technion.coolie.ug.model.AcademicCalendarEvent;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.UGLoginObject;
import com.technion.coolie.ug.tracking.TrackingCoursesFragment;
import com.technion.coolie.ug.utils.UGAsync;
import com.technion.coolie.ug.*;

import java.io.IOException;
import java.net.URL;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.technion.coolie.ug.HtmlParser;
import com.technion.coolie.ug.MainActivity;

import org.jsoup.Connection.Method;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.Exam;
/**
 * 
 * wrapper functions of the (UGFactory)server functions. all a-sync tasks should
 * be defined here.
 * 
 */
public class ServerAsyncCommunication {

	//static Context appContext;
	//static public MainActivity mainActivity;


	/**
	 * sets the semester array with the current arrays
	 * 
	 * @param semesters
	 * @return
	 */
	public static SemesterSeason findCurrentSemesters(Semester[] semesters) {

		return SemesterSeason.WINTER;
	}

	static public void getGradesSheetfromServer(final Context context) {
		Log.v("ServerAsyncCommunication","getGradesSheetfromServer");
		UGAsync<AccomplishedCourse> a = new UGAsync<AccomplishedCourse>()
		{
			List<AccomplishedCourse> l;
			@Override
			protected List<AccomplishedCourse> doInBackground(String... params) 
			{

				UGDatabase db = UGDatabase.getInstance(context);
				l = UgFactory.getUgGradeSheet().getMyGradesSheet(db.getCurrentLoginObject());
				
				
				//l = HtmlParser.parseGrades("stam");			
				return super.doInBackground(params);
				
			}

			@Override
			protected void onPostExecute(List<AccomplishedCourse> result) 
			{
				
				if (l==null || l.size()==0) return;
				UGDatabase.getInstance(context).setGradesSheet(l);
				/*GradesSheetListFragment f = context.getGradesSheetFragment();
				if (f==null) return;
				f.updateData();*/
			}
		};
		a.execute();
	}
	
	

	static public void getAllCoursesFromServer() {

		UGAsync<Course> a = new UGAsync<Course>() {
			List<ServerCourse> l;

			@Override
			protected List<Course> doInBackground(String... params) {

				Semester s = new Semester(2013, SemesterSeason.WINTER);
				List<ServerCourse> l = UgFactory.getUgCourse().getAllCourses(s);
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(List<Course> result) {
				if (l!=null)
				{
					Log.d("all courses", l.size() + "");
				}
			}

		};
		a.execute();
	}
	
	
	static public void getCalendarEventsFromServer(final Context context) {

		UGAsync<AcademicCalendarEvent> a = new UGAsync<AcademicCalendarEvent>() {

			List<AcademicCalendarEvent> l;

			@Override
			protected List<AcademicCalendarEvent> doInBackground(
					String... params) {

				l = UgFactory.getUgEvent().getAllAcademicEvents();
				
				
				//l = HtmlParser.parseCalendar();			
				return super.doInBackground(params); 		
			}

			@Override 
			protected void onPostExecute(List<AcademicCalendarEvent> result) 
			{
				if (l==null || l.size()==0) return;
				UGDatabase.getInstance(context).setAcademicCalendar(l);
				/*AcademicCalendarListFragment f = mainActivity.getCalendarFragment();
				if (f==null) return;
				f.updateData();*/
			}

		};
		a.execute();
	}
	
	/*static public void getAllExams(final Semester semester) {
		//getStudentExams(Student student, Semester semester)

		UGAsync<Exam> a = new UGAsync<Exam>() {
			//List<ServerCourse> l;

			@Override
			protected List<Exam> doInBackground(String... params) {

				//Semester s = new Semester(2013, SemesterSeason.WINTER);
				UGLoginObject currentLoginObject = UGDatabase.getInstance(mainActivity).getCurrentLoginObject();
				//List<Exam> l = UgFactory.getUgExam().getStudentExams(currentLoginObject, semester);
				List<CourseItem> l = UgFactory.getUgExam().getStudentExams(currentLoginObject,semester);
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(List<Exam> result) {
				//Log.d("all courses", l.size() + "");
			}

		};
		a.execute();
	}*/

	public void addTrackingCourseToServer(UGLoginObject o, CourseKey ck, final Context context) {
		AsyncTask<CourseKey, Void, ReturnCodesUg> asyncTask = new AsyncTask<CourseKey, Void, ReturnCodesUg>() {
			@Override
			protected ReturnCodesUg doInBackground(CourseKey... params) {
				if (params == null || params[0] == null)
					return null;
				ReturnCodesUg returnCode = UgFactory.getUgTracking() 
						.addTrackingStudent(
								UGDatabase.getInstance(context)
										.getCurrentLoginObject(), params[0]);
				return returnCode;
			}

			@Override
			protected void onPostExecute(ReturnCodesUg returnCode) {
				if (returnCode == null) {
					Log.v("addTrackingCourseToServer", "returnCode is null");
					return;
				}

				Log.v("addTrackingCourseToServer", returnCode.toString());
				if (returnCode != ReturnCodesUg.SUCCESS) {
				}
			}
		};
		asyncTask.execute(ck);
	}

	public void deleteTrackingCourseFromServer(UGLoginObject o, CourseKey ck,final Context context) {
		AsyncTask<CourseKey, Void, ReturnCodesUg> asyncTask = new AsyncTask<CourseKey, Void, ReturnCodesUg>() {
			@Override
			protected ReturnCodesUg doInBackground(CourseKey... params) {
				if (params == null || params[0] == null)
					return null;
				ReturnCodesUg returnCode = UgFactory.getUgTracking()
						.removeTrackingStudentFromCourse(
								UGDatabase.getInstance(context)
										.getCurrentLoginObject(), params[0]);
				return returnCode;
			}

			@Override
			protected void onPostExecute(ReturnCodesUg returnCode) {
				if (returnCode == null) {
					Log.v("deleteTrackingCourseFromServer",
							"returnCode is null");
					return;
				}

				Log.v("deleteTrackingCourseFromServer", returnCode.toString());
				if (returnCode != ReturnCodesUg.SUCCESS) {
					// cant remove this course on (server problem)
				}
			}
		};
		asyncTask.execute(ck);
	}
	
	static public void getAllExamsFromClient(final Semester semester, final String userName, final String password,final Context context) {

		AsyncTask<Void,Void,Void> ast = new AsyncTask<Void,Void,Void>()
		{

			@Override
			protected Void doInBackground(Void... arg0) {
				HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02");
				
				try {
			        // Add your data
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			        nameValuePairs.add(new BasicNameValuePair("OP", "LI"));
			        nameValuePairs.add(new BasicNameValuePair("UID", userName));
			        nameValuePairs.add(new BasicNameValuePair("PWD", password));
			        String sem = semester.getYear()+semester.getSs().getId();
			        nameValuePairs.add(new BasicNameValuePair("SEM", sem));
			        nameValuePairs.add(new BasicNameValuePair("NEXTOP", "WK"));
			        nameValuePairs.add(new BasicNameValuePair("Login.x", "8"));
			        nameValuePairs.add(new BasicNameValuePair("Login.y", "17"));
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			        HttpResponse response = httpclient.execute(httppost);
			        HttpEntity responseEntity = response.getEntity();
			        String s = EntityUtils.toString(responseEntity); // <----- s is a html of exams page
			        List<CourseItem> x = HtmlParseFromClient.parseStudentExams(Jsoup.parse(s));
			        UGDatabase db = UGDatabase.getInstance(context);
			        db.setCoursesAndExams(x);
			        Math.random();
			    } catch (Exception e) {
			        // TODO Auto-generated catch block
			    }
				return null; 
			}
			
			
		};
		ast.execute();
	}
	
	
	static public void registrate(final String courseNumber, final String groupNumber,final String userName,final String password,final Context context, final TrackingCoursesFragment trackingCoursesFragment) {
		
		//public class UGAsync<T> extends AsyncTask<String, Void , List<T>>
		AsyncTask<Void,Void,Void> ast = new AsyncTask<Void,Void,Void>()
		{

			@Override
			protected Void doInBackground(Void... arg0) {
				HttpPost getCookiePost = new HttpPost("http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02");
				try {
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			        nameValuePairs.add(new BasicNameValuePair("OP", "LI"));
			        nameValuePairs.add(new BasicNameValuePair("UID", userName));
			        nameValuePairs.add(new BasicNameValuePair("PWD", password));
			        nameValuePairs.add(new BasicNameValuePair("Login.x", "16"));
			        nameValuePairs.add(new BasicNameValuePair("Login.y", "22"));
			        getCookiePost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        HttpClient getCookieClient = new DefaultHttpClient();
			        HttpResponse getCookieResponse = getCookieClient.execute(getCookiePost);
			        String cookie =getCookieResponse.getHeaders("Set-Cookie")[0].toString().split(";")[0]+courseNumber+groupNumber;
			        
			        Connection c = Jsoup.connect("http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&Add+to+my+basket.x=12&Add+to+my+basket.y=18&LGRP1="+groupNumber+"&LGRP2=&LGRP3=&LMK1="+courseNumber+"&LMK2=&LMK3=&RSND=")
			        		.timeout(7000)
			        		.header("Cookie", cookie)
			        		.method(Method.GET);
			        Document d = Jsoup.parse(new String (c.execute().bodyAsBytes(),"ISO-8859-8"));
			        String sss = d.toString();
			        Connection c2 = Jsoup.connect("http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&SALU23411111=&LGRP1=&LGRP2=&LGRP3=&LMK1=&LMK2=&LMK3=&RSND=SND")
			        		.timeout(7000)
			        		.header("Cookie", cookie)
			        		.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
			        		.header("Referer", "http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&Add+to+my+basket.x=27&Add+to+my+basket.y=6&LGRP1="+groupNumber+"&LGRP2=&LGRP3=&LMK1="+courseNumber+"&LMK2=&LMK3=&RSND=")
			        		.method(Method.GET);
			        Document d2 = Jsoup.parse(new String (c2.execute().bodyAsBytes(),"ISO-8859-8"));
			        String sss2 = d2.toString();
			        
			    } catch (Exception e) {
			    	//HtmlParseFromClient.handleRegistrationRequest(null);
			    } 
				return null;
			}
			@Override
			protected void onPostExecute(Void c) 
			{
				/*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder.setMessage("hi");
				alertDialogBuilder.show();*/
				trackingCoursesFragment.onRegistrationSuccessed(null);
			}
	
		};
		ast.execute();
	}
	
	
static public void unRegistrate(final String courseNumber,final String userName,final String password,final Context context) {
		
		AsyncTask<Void,Void,Void> ast = new AsyncTask<Void,Void,Void>()
		{

			@Override
			protected Void doInBackground(Void... arg0) {
				HttpPost getCookiePost = new HttpPost("http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02");
				try {
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			        nameValuePairs.add(new BasicNameValuePair("OP", "LI"));
			        nameValuePairs.add(new BasicNameValuePair("UID", userName));
			        nameValuePairs.add(new BasicNameValuePair("PWD", password));
			        nameValuePairs.add(new BasicNameValuePair("Login.x", "16"));
			        nameValuePairs.add(new BasicNameValuePair("Login.y", "22"));
			        getCookiePost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        HttpClient getCookieClient = new DefaultHttpClient();
			        HttpResponse getCookieResponse = getCookieClient.execute(getCookiePost);
			        String cookie =getCookieResponse.getHeaders("Set-Cookie")[0].toString().split(";")[0];
			        
			        Connection c = //Jsoup.connect("http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&UPG"+courseNumber+"=&DEL"+courseNumber+"=on&LGRP1=&LGRP2=&LGRP3=&LMK1=&LMK2=&LMK3=&RSND=SND")
			        		Jsoup.connect("http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&UPG"+courseNumber+"=&DEL"+courseNumber+"=on&LGRP1=&LGRP2=&LGRP3=&LMK1=&LMK2=&LMK3=&RSND=SND")
			        			
			        		.timeout(7000)
			        		.header("Cookie", cookie)
			        		.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
			        		.method(Method.GET);
			        Document d = Jsoup.parse(new String (c.execute().bodyAsBytes(),"ISO-8859-8"));
			        String sss = d.toString();
			        Math.random();

				} catch (Exception e) {
			    	//HtmlParseFromClient.handleRegistrationRequest(null);
			    } 
				return null;
			}
			@Override
			protected void onPostExecute(Void c) 
			{
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder.setMessage("May be done.");
				alertDialogBuilder.show();
			}
	
		};
		ast.execute();
	}
	
	static public void getStudentDetailsFromClient(final Semester semester, final String username, final String password) {
		//getStudentExams(Student student, Semester semester)

		UGAsync<Exam> a = new UGAsync<Exam>() {
			//List<ServerCourse> l;

			@Override
			protected List<Exam> doInBackground(String... params) {

				HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://techmvs.technion.ac.il/cics/wmn/wmngrad?abiipbht&ORD=1&s=1");
				
				try {
			        // Add your data
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			        nameValuePairs.add(new BasicNameValuePair("function", "signon"));
			        nameValuePairs.add(new BasicNameValuePair("userid", username));
			        nameValuePairs.add(new BasicNameValuePair("password", password));

			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			        // Execute HTTP Post Request
			        HttpResponse response = httpclient.execute(httppost);
			       
			        
			        HttpEntity responseEntity = response.getEntity();
			        if(responseEntity!=null) {
			            String s = EntityUtils.toString(responseEntity); //
			            Math.random();
			        }

			    } catch (Exception e) {
			        // TODO Auto-generated catch block
			    } 
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(List<Exam> result) {
			}

		};
		a.execute();
	}
	
	static public void getCurentSemestersFromClient(final Semester semester) {
		//getStudentExams(Student student, Semester semester)

		UGAsync<Exam> a = new UGAsync<Exam>() {
			//List<ServerCourse> l;

			@Override
			protected List<Exam> doInBackground(String... params) 
			{
				try {
					Document doc = Jsoup.connect("http://www.undergraduate.technion.ac.il/rishum/search.php").get();
					String s = doc.toString();
					Math.random();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(List<Exam> result) {
			}

		};
		a.execute();
	}
	// getAllInfo

	// getcourses

	// getTrackingCourses

	// getAcademic

}
