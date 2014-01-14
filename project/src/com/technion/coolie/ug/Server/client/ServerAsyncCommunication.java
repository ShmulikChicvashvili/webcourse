package com.technion.coolie.ug.Server.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.technion.coolie.server.ug.ReturnCodesUg;
import com.technion.coolie.server.ug.api.UgFactory;
import com.technion.coolie.ug.HtmlParseFromClient;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.Server.ServerCourse;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.AcademicCalendarEvent;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;
import com.technion.coolie.ug.model.UGLoginObject;
import com.technion.coolie.ug.tracking.TrackingCoursesFragment;
import com.technion.coolie.ug.utils.UGAsync;

/**
 * 
 * wrapper functions of the (UGFactory)server functions. all a-sync tasks should
 * be defined here.
 * 
 */
public class ServerAsyncCommunication {

  // static Context appContext;
  // static public MainActivity mainActivity;

  /**
   * sets the semester array with the current arrays
   * 
   * @param semesters
   * @return
   */
  public static SemesterSeason findCurrentSemesters(Semester[] semesters) {

    return SemesterSeason.WINTER;
  }

  static public void getGradesSheetfromServer(final Context context,
      final String studentId, final String password) {
    UGAsync<AccomplishedCourse> a = new UGAsync<AccomplishedCourse>() {
      @Override
      protected List<AccomplishedCourse> doInBackground(String... params) {
        UGLoginObject loginObject = new UGLoginObject(studentId, password);
        List<AccomplishedCourse> l = UgFactory.getUgGradeSheet()
            .getMyGradesSheet(loginObject);
        // l = HtmlParser.parseGrades("stam");
        return l;
      }

      @Override
      protected void onPostExecute(List<AccomplishedCourse> result) {
        Log.i("UG", "Grades sheet downloaded");
        Toast.makeText(context, "Grades sheet downloading done", 1000).show();
        if (result == null || result.size() == 0)
          return;
        UGDatabase.getInstance(context).setGradesSheet(result);
        /*
         * GradesSheetListFragment f = context.getGradesSheetFragment(); if
         * (f==null) return; f.updateData();
         */
      }
    };
    a.execute();
  }

  /*
   * static public void getGradesSheetfromServerSync(final Context context,
   * final String studentId, final String password) { UGLoginObject loginObject
   * = new UGLoginObject(studentId, password); List<AccomplishedCourse> result =
   * UgFactory.getUgGradeSheet().getMyGradesSheet(loginObject);
   * Log.i("UG","Grades sheetsync  downloaded"); if (result==null ||
   * result.size()==0) return;
   * UGDatabase.getInstance(context).setGradesSheet(result); }
   */

  static public void getAllCoursesFromServer() {

    UGAsync<Course> a = new UGAsync<Course>() {
      List<ServerCourse> l;

      @Override
      protected List<Course> doInBackground(String... params) {

        Semester s = new Semester(2013, SemesterSeason.WINTER);
        try {
          List<ServerCourse> l = UgFactory.getUgCourse().getAllCourses(s);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        return super.doInBackground(params);
      }

      @Override
      protected void onPostExecute(List<Course> result) {
        if (l != null) {
          Log.d("all courses", l.size() + "");
        }
      }

    };
    a.execute();
  }

  static public void getCalendarEventsFromServer(final Context context) {

    UGAsync<AcademicCalendarEvent> a = new UGAsync<AcademicCalendarEvent>() {

      @Override
      protected List<AcademicCalendarEvent> doInBackground(String... params) {
        List<AcademicCalendarEvent> l = null;
        try {
          l = UgFactory.getUgEvent().getAllAcademicEvents();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        // l = HtmlParser.parseCalendar();
        return l;
      }

      @Override
      protected void onPostExecute(List<AcademicCalendarEvent> result) {
        Log.i("UG", "Calendar events downloaded");
        Toast.makeText(context, "Calendar downloading done", 1000).show();
        if (result == null || result.size() == 0)
          return;
        UGDatabase.getInstance(context).setAcademicCalendar(result);
        /*
         * AcademicCalendarListFragment f = mainActivity.getCalendarFragment();
         * if (f==null) return; f.updateData();
         */
      }
    };
    a.execute();
  }

  /*
   * static public void getAllExams(final Semester semester) {
   * //getStudentExams(Student student, Semester semester)
   * 
   * UGAsync<Exam> a = new UGAsync<Exam>() { //List<ServerCourse> l;
   * 
   * @Override protected List<Exam> doInBackground(String... params) {
   * 
   * //Semester s = new Semester(2013, SemesterSeason.WINTER); UGLoginObject
   * currentLoginObject =
   * UGDatabase.getInstance(mainActivity).getCurrentLoginObject(); //List<Exam>
   * l = UgFactory.getUgExam().getStudentExams(currentLoginObject, semester);
   * List<CourseItem> l =
   * UgFactory.getUgExam().getStudentExams(currentLoginObject,semester); return
   * super.doInBackground(params); }
   * 
   * @Override protected void onPostExecute(List<Exam> result) {
   * //Log.d("all courses", l.size() + ""); }
   * 
   * }; a.execute(); }
   */

  public void addTrackingCourseToServer(UGLoginObject o, CourseKey ck,
      final Context context) {
    AsyncTask<CourseKey, Void, ReturnCodesUg> asyncTask = new AsyncTask<CourseKey, Void, ReturnCodesUg>() {
      @Override
      protected ReturnCodesUg doInBackground(CourseKey... params) {
        if (params == null || params[0] == null)
          return null;
        ReturnCodesUg returnCode = UgFactory.getUgTracking()
            .addTrackingStudent(
                UGDatabase.getInstance(context).getCurrentLoginObject(),
                params[0]);
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

  public void deleteTrackingCourseFromServer(UGLoginObject o, CourseKey ck,
      final Context context) {
    AsyncTask<CourseKey, Void, ReturnCodesUg> asyncTask = new AsyncTask<CourseKey, Void, ReturnCodesUg>() {
      @Override
      protected ReturnCodesUg doInBackground(CourseKey... params) {
        if (params == null || params[0] == null)
          return null;
        ReturnCodesUg returnCode = UgFactory.getUgTracking()
            .removeTrackingStudentFromCourse(
                UGDatabase.getInstance(context).getCurrentLoginObject(),
                params[0]);
        return returnCode;
      }

      @Override
      protected void onPostExecute(ReturnCodesUg returnCode) {
        if (returnCode == null) {
          Log.v("deleteTrackingCourseFromServer", "returnCode is null");
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

  static public void getAllExamsFromClient(final String userName,
      final String password, final Context context) {

    AsyncTask<Void, Void, List<CourseItem>> ast = new AsyncTask<Void, Void, List<CourseItem>>() {

      List<CourseItem> x;

      @Override
      protected List<CourseItem> doInBackground(Void... arg0) {
        List<CourseItem> allExams = getAllExamsFromClientSync(context,
            userName, password);
        UGDatabase db = UGDatabase.getInstance(context);
        db.setCoursesAndExams(allExams);
        return allExams;
      }

      @Override
      protected void onPostExecute(List<CourseItem> result) {
        Log.i("UG", "Courses and exams events downloaded");
        Toast.makeText(context, "Courses and exams downloading done", 1000)
            .show();
        /*
         * AcademicCalendarListFragment f = mainActivity.getCalendarFragment();
         * if (f==null) return; f.updateData();
         */
      }
    };
    ast.execute();
  }

  static public List<CourseItem> getAllExamsFromClientSync(Context context,
      final String userName, final String password) {
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost(
        "http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02");

    List<CourseItem> allExams = new ArrayList<CourseItem>();
    try {
      Semester[] currentSemesters = UGDatabase.getInstance(context)
          .getCurrentSemesters();
      if (currentSemesters == null || currentSemesters.length != 3) {
        currentSemesters = getCurentSemestersFromClientSync();
      }
      for (int i = 0; i < 3; i++) {
        Semester semester = currentSemesters[i];
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("OP", "LI"));
        String qws = userName;
        nameValuePairs.add(new BasicNameValuePair("UID", userName));
        nameValuePairs.add(new BasicNameValuePair("PWD", password));
        String sem = semester.getYear() + semester.getSs().getId();
        nameValuePairs.add(new BasicNameValuePair("SEM", sem));
        nameValuePairs.add(new BasicNameValuePair("NEXTOP", "WK"));
        nameValuePairs.add(new BasicNameValuePair("Login.x", "8"));
        nameValuePairs.add(new BasicNameValuePair("Login.y", "17"));
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity responseEntity = response.getEntity();
        String s = EntityUtils.toString(responseEntity, "ISO-8859-8"); // <-----
                                                                       // s is a
                                                                       // html
                                                                       // of
                                                                       // exams
                                                                       // page
        List<CourseItem> x = HtmlParseFromClient.parseStudentExams(
            Jsoup.parse(s), semester);
        allExams.addAll(x);
      }
    } catch (Exception e) {
      return null;
    }
    return allExams;
  }

  static public void registrate(final String courseNumber,
      final String groupNumber, final String userName, final String password,
      final Context context,
      final TrackingCoursesFragment trackingCoursesFragment) {

    AsyncTask<Void, Void, Document> ast = new AsyncTask<Void, Void, Document>() {
      ProgressDialog progressDialog;

      @Override
      protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Registering to course",
            "Trying to register to " + courseNumber + " group " + groupNumber,
            true);

        // do initialization of required objects objects here
      };

      @Override
      protected Document doInBackground(Void... arg0) {
        HttpPost getCookiePost = new HttpPost(
            "http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02");
        Document result = null;
        try {
          Log.i("2", "registration do in background");
          List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
          nameValuePairs.add(new BasicNameValuePair("OP", "LI"));
          nameValuePairs.add(new BasicNameValuePair("UID", userName));
          nameValuePairs.add(new BasicNameValuePair("PWD", password));
          nameValuePairs.add(new BasicNameValuePair("Login.x", "16"));
          nameValuePairs.add(new BasicNameValuePair("Login.y", "22"));
          getCookiePost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
          HttpClient getCookieClient = new DefaultHttpClient();
          HttpResponse getCookieResponse = getCookieClient
              .execute(getCookiePost);
          String cookie = getCookieResponse.getHeaders("Set-Cookie")[0]
              .toString().split(";")[0] + courseNumber + groupNumber;

          Connection c = Jsoup
              .connect(
                  "http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&Add+to+my+basket.x=12&Add+to+my+basket.y=18&LGRP1="
                      + groupNumber
                      + "&LGRP2=&LGRP3=&LMK1="
                      + courseNumber
                      + "&LMK2=&LMK3=&RSND=").timeout(7000)
              .header("Cookie", cookie).method(Method.GET);
          Document d = Jsoup.parse(new String(c.execute().bodyAsBytes(),
              "ISO-8859-8"));
          String sss = d.toString();
          Connection c2 = Jsoup
              .connect(
                  "http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&SALU23411111=&LGRP1=&LGRP2=&LGRP3=&LMK1=&LMK2=&LMK3=&RSND=SND")
              .timeout(7000)
              .header("Cookie", cookie)
              .header(
                  "User-Agent",
                  "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
              .header(
                  "Referer",
                  "http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&Add+to+my+basket.x=27&Add+to+my+basket.y=6&LGRP1="
                      + groupNumber
                      + "&LGRP2=&LGRP3=&LMK1="
                      + courseNumber
                      + "&LMK2=&LMK3=&RSND=").method(Method.GET);
          result = Jsoup.parse(new String(c2.execute().bodyAsBytes(),
              "ISO-8859-8"));
          String sss2 = result.toString();

        } catch (Exception e) {
          HtmlParseFromClient.handleRegistrationRequest(null);
        }
        return result;
      }

      @Override
      protected void onPostExecute(Document d) {
        boolean b = HtmlParseFromClient.handleRegistrationRequest(d);
        Log.i("2", "registration result : " + b);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
            context);
        if (b) {
          alertDialogBuilder.setMessage("registration successed");
          trackingCoursesFragment.onRegistrationSuccessed(new CourseKey(
              courseNumber, null));
        } else {
          alertDialogBuilder.setMessage("registration failed");
        }
        progressDialog.dismiss();
        alertDialogBuilder.show();
      }

    };
    ast.execute();
  }

  static public void unRegistrate(final int position,
      final String courseNumber, final String userName, final String password,
      final Context context,
      final TrackingCoursesFragment trackingCoursesFragment) {

    AsyncTask<Void, Void, Document> ast = new AsyncTask<Void, Void, Document>() {

      ProgressDialog progressDialog;

      @Override
      protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Unregistering",
            "Unregistering from " + courseNumber, true);
        progressDialog.show();
        // do initialization of required objects objects here
      };

      @Override
      protected Document doInBackground(Void... arg0) {
        HttpPost getCookiePost = new HttpPost(
            "http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02");
        try {
          List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
          nameValuePairs.add(new BasicNameValuePair("OP", "LI"));
          nameValuePairs.add(new BasicNameValuePair("UID", userName));
          nameValuePairs.add(new BasicNameValuePair("PWD", password));
          nameValuePairs.add(new BasicNameValuePair("Login.x", "16"));
          nameValuePairs.add(new BasicNameValuePair("Login.y", "22"));
          getCookiePost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
          HttpClient getCookieClient = new DefaultHttpClient();
          HttpResponse getCookieResponse = getCookieClient
              .execute(getCookiePost);
          String cookie = getCookieResponse.getHeaders("Set-Cookie")[0]
              .toString().split(";")[0];

          Connection c = // Jsoup.connect("http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&UPG"+courseNumber+"=&DEL"+courseNumber+"=on&LGRP1=&LGRP2=&LGRP3=&LMK1=&LMK2=&LMK3=&RSND=SND")
          Jsoup
              .connect(
                  "http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&UPG"
                      + courseNumber + "=&DEL" + courseNumber
                      + "=on&LGRP1=&LGRP2=&LGRP3=&LMK1=&LMK2=&LMK3=&RSND=SND")

              .timeout(7000)
              .header("Cookie", cookie)
              .header(
                  "User-Agent",
                  "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
              .method(Method.GET);
          Document d = Jsoup.parse(new String(c.execute().bodyAsBytes(),
              "ISO-8859-8"));
          return d;

        } catch (Exception e) {
          HtmlParseFromClient.handleRegistrationRequest(null);
        }
        return null;
      }

      @Override
      protected void onPostExecute(Document c) {
        trackingCoursesFragment.onCancellationSuccessed(position);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
            context);
        progressDialog.dismiss();
        alertDialogBuilder.setMessage("Course was removed successfully");
        alertDialogBuilder.show();
      }

    };
    ast.execute();
  }

  static public void getStudentDetailsFromClient(final Context context,
      final String username, final String password) {

    AsyncTask<Void, Void, Void> ast = new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... arg0) {
        // Student student = getStudentDetailsFromClientSync(username,password);
        Student student = new Student("demo student", "demo student", 100, 100,
            new GregorianCalendar(), 100);
        // UGDatabase.getInstance(context).setStudentInfo(student);
        return null;
      }

      @Override
      protected void onPostExecute(Void v) {
        Log.i("UG", "Courses and exams events downloaded");
        Toast.makeText(context, "Students details downloading done", 1000)
            .show();
      }
    };
    ast.execute();
  }

  public static Student getStudentDetailsFromClientSync(final String username,
      final String password) {
    HttpClient httpclient = new DefaultHttpClient();
    try {

      Document doc1 = Jsoup.connect(
          "http://www.undergraduate.technion.ac.il/Tadpis.html").get();
      String gradesSheetUrl = HtmlParseFromClient.getGradesSheetUrl(doc1);

      HttpPost httppost = new HttpPost(gradesSheetUrl);
      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
      nameValuePairs.add(new BasicNameValuePair("function", "signon"));
      nameValuePairs.add(new BasicNameValuePair("userid", username));
      nameValuePairs.add(new BasicNameValuePair("password", password));
      httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

      // Execute HTTP Post Request
      HttpResponse response = httpclient.execute(httppost);
      HttpEntity responseEntity = response.getEntity();
      String s = EntityUtils.toString(responseEntity, "ISO-8859-8"); //
      return HtmlParseFromClient.getStudentDetails(s, username);

    } catch (Exception e) {
      return null;
    }
  }

  static public void getCurentSemestersFromClient(final Context context) {

    AsyncTask<Void, Void, Void> ast = new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... arg0) {
        Semester[] currentSemesters = null;
        currentSemesters = getCurentSemestersFromClientSync();
        UGDatabase.getInstance(context).setCurrentSemesters(currentSemesters);
        return null;
      }
    };
    ast.execute();
  }

  public static Semester[] getCurentSemestersFromClientSync() {
    Semester[] currentSemesters;
    Document doc;
    try {
      doc = Jsoup.connect(
          "http://www.undergraduate.technion.ac.il/rishum/search.php").get();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
    currentSemesters = HtmlParseFromClient.getSemesters(doc);
    return currentSemesters;
  }

  // getAllInfo

  // getcourses

  // getTrackingCourses

  // getAcademic

}
