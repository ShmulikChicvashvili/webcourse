package com.technion.coolie.ug;

import java.util.List;

import com.technion.coolie.server.ug.api.UgFactory;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.Server.client.ServerAsyncCommunication;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;
import com.technion.coolie.ug.model.UGLoginObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UGInitializer {
	
	static boolean startupInitializationDone = false;
	//static boolean startupInitializationInProgress = false;
	
	static boolean loginInitializationDone = false;
	
	public boolean isLoginInitializationDone()
	{
		return loginInitializationDone;
	}
	//boolean loginInitializationInProgress = false;
	
	
	public static void onCoolieStartupInitialization(Context context)
	{
		if (startupInitializationDone) return;
		
		//Download all courses
		
		ServerAsyncCommunication.getCalendarEventsFromServer(context);
		
		startupInitializationDone = true;
	}
	
	public static void onUgLoginInitialization(Context context, String studentId, String password)
	{
		//if there is no internet  return
		if (loginInitializationDone) return;		
		
		ServerAsyncCommunication.getGradesSheetfromServer(context,studentId,password);
		ServerAsyncCommunication.getAllExamsFromClient(studentId, password,	context);
		ServerAsyncCommunication.getCurentSemestersFromClient(context);
		ServerAsyncCommunication.getStudentDetailsFromClient(context, studentId, password);
		
		
		//Download student details
		//Download tracking courses
		
		loginInitializationDone=true;
	}
	
	
	public static boolean login(Context context, String studentId, String password)
	{
		//check internet connection
		UGLoginObject loginObject = new UGLoginObject(studentId, password);
		UGDatabase db = UGDatabase.getInstance(context);
		
		//Grades sheet 
		boolean localGradesSheetInitializationFlag = false;
		List<AccomplishedCourse> localGradesSheet = db.getGradesSheet();
		if (localGradesSheet==null || localGradesSheet.size()==0)
		{
			localGradesSheet = UgFactory.getUgGradeSheet().getMyGradesSheet(loginObject);
			if (!(localGradesSheet == null || localGradesSheet.size() == 0))
			{
				db.setGradesSheet(localGradesSheet);
				localGradesSheetInitializationFlag = true;;
			}
		}
		if (!localGradesSheetInitializationFlag)
		{
			ServerAsyncCommunication.getGradesSheetfromServer(context,studentId,password);
		}
		
		//Courses and exams
		boolean localCoursesAndExamsInitializationFlag = false;
		List<CourseItem> localCoursesAndExams = db.getCoursesAndExams();
		if (localCoursesAndExams==null || localCoursesAndExams.size()==0)
		{
			localCoursesAndExams = ServerAsyncCommunication.getAllExamsFromClientSync(context, studentId, password);
			if (!(localCoursesAndExams == null || localCoursesAndExams.size() == 0))
			{
				db.setCoursesAndExams(localCoursesAndExams);
				localCoursesAndExamsInitializationFlag = true;
			}
		}
		if (!localCoursesAndExamsInitializationFlag)
		{
			ServerAsyncCommunication.getAllExamsFromClient(studentId, password, context);
		}
		
		
		Student student = db.getStudentInfo();
		if(student==null || student.getName()==null || student.getName()=="" || student.getId()!=studentId)
		{
			student = ServerAsyncCommunication.getStudentDetailsFromClientSync(studentId, password);
			if(student != null)
			{
				db.setStudentInfo(student);
			}
		}
		
		boolean currentSemestersInitializationFlag=false;
		Semester[] currentSemesters = db.getCurrentSemesters();
		if (currentSemesters==null || currentSemesters.length!=3)
		{
			currentSemesters = ServerAsyncCommunication.getCurentSemestersFromClientSync();
			if (currentSemesters==null && currentSemesters.length==3)
			{
				db.setCurrentSemesters(currentSemesters);
				currentSemestersInitializationFlag = true;
			}
		}
		if (!currentSemestersInitializationFlag)
		{
			ServerAsyncCommunication.getCurentSemestersFromClient(context);
		}
		
		//UgLo
		//db.setCurrentLoginObject()
		db.setCurrentLoginObject(loginObject);
		
		return true;
	
	}
	
	/*static boolean isAlreadyInitializedAfterFirstInstalling_ugLogin(Context context)
	{
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPrefs.contains("isFirstTime_ugLogin");
	}
	
	static void firstInstalationInitializationDone_ugLogin(Context context)
	{
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPrefs.edit().putBoolean("isFirstTime_ugLogin", true).commit();
	}*/

}
