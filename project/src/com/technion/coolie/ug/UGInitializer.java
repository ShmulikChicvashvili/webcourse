package com.technion.coolie.ug;

import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.Server.client.ServerAsyncCommunication;
import com.technion.coolie.ug.model.Semester;

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
	//	if (isAlreadyInitializedAfterFirstInstalling(context)) return;
		
		//Download all courses
		
		//Download academic events
		ServerAsyncCommunication.getCalendarEventsFromServer(context);
		
		
	}
	
	public static void onUgLoginInitialization(Context context, String studentId, String password)
	{
		//if there is no internet  return
		if (loginInitializationDone) return;		
		
		ServerAsyncCommunication.getGradesSheetfromServer(context,studentId,password);
		ServerAsyncCommunication.getAllExamsFromClient(studentId, password,	context);
		ServerAsyncCommunication.getCurentSemestersFromClient(context);
		
		
		//Download student details
		//Download current available semesters
		//Download tracking courses
		
		loginInitializationDone=true;
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
