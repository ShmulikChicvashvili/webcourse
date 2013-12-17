package com.technion.coolie;



import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.ug.framework.CourseKey;
import com.technion.coolie.server.ug.framework.CourseServer;
import com.technion.coolie.server.ug.framework.Semester;
import com.technion.coolie.server.ug.framework.SemesterSeason;
import com.technion.coolie.server.ug.framework.Student;
import com.technion.coolie.server.webcourse.framework.CourseData;
import com.technion.coolie.skeleton.CoolieServerInterfaceService;
import com.technion.coolie.skeleton.CoolieStatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;


/*this is how u will use it on a your activity, u will need to create CoolieServerInterface for each function u do,
 * and deserialize the Gson appropriately on each handleResult u write..
 * for example
 *
 	CoolieServerInterface c = new CoolieServerInterface(getApplicationContext()){

			@Override
			public void handleResult(String result, CoolieStatus status) {
				List<CourseServer> myOutput;
				myOutput = new Gson().fromJson(result, new TypeToken<List<CourseServer>>() {
			    }.getType());
				
			}

			
			};
			SemesterSeason ss = SemesterSeason.WINTER;
			Semester semester = new Semester(2011,ss);
			c.getAllCourses(semester);
 */


public abstract class CoolieServerInterface {
	Context mContext;
	
	public enum Action
	{
		UG_GET_ALL_COURSES,
		UG_GET_COURSES,
		UG_GET_STUDENT_CURRENT_COURSES,
		UG_GET_ALL_ACADEMIC_EVENTS,
		UG_GET_STUDENT_EXAMS,
		UG_GET_MY_GRADES_SHEET,
		UG_GET_STUDENT_PAYMENTS,
		UG_ADD_TRACKING_STUDENT,
		UG_REMOVE_TRACKING_STUDENT_FROM_COURSE,
		TELETECH_GET_ALL_CONTACTS,
		WEBCOURSE_GET_STAFF_INFO,
		
	
	}
	public CoolieServerInterface(Context c)
	{
		mContext = c;
		mContext.registerReceiver(receiver, new IntentFilter(
				CoolieServerInterfaceService.NOTIFICATION));
	}

	
	
	public abstract void handleResult(String result, CoolieStatus status);

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				CoolieStatus status = (CoolieStatus) bundle
						.getSerializable(CoolieServerInterfaceService.STATUS);
				//TODO handle errors
					String result = bundle.getString(CoolieServerInterfaceService.RESULT);
					handleResult(result, status);
			}
		}
	};

	protected void finalize() throws Throwable
	{
		mContext.unregisterReceiver(receiver);
	}
	
///////UG PART	
	public void getAllCourses(Semester semester) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_ALL_COURSES;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT,semester);		
		mContext.startService(intent);
	}
	
	public void getCourses(List<CourseKey> courseKeys) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_COURSES;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(courseKeys));
		mContext.startService(intent);
	}
	
	public void getStudentCurrentCourses(Student student,Semester semester) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_STUDENT_CURRENT_COURSES;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		intent.putExtra(CoolieServerInterfaceService.INPUT2, new Gson().toJson(semester));
		mContext.startService(intent);
	}
	
	public void getAllAcademicEvents() {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_ALL_ACADEMIC_EVENTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		mContext.startService(intent);
	}
	
	public void getStudentExams(Student student,Semester semester) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_STUDENT_EXAMS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		intent.putExtra(CoolieServerInterfaceService.INPUT2, new Gson().toJson(semester));
		mContext.startService(intent);
	}
	
	public void getMyGradesSheet(Student student) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_MY_GRADES_SHEET;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		mContext.startService(intent);
	}
	
	public void getStudentPayments(Student student) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_STUDENT_PAYMENTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		mContext.startService(intent);
	}
	
	public void addTrackingStudent(Student student,CourseKey courseKey) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_ADD_TRACKING_STUDENT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		intent.putExtra(CoolieServerInterfaceService.INPUT2, new Gson().toJson(courseKey));
		mContext.startService(intent);
	}
	
	public void removeTrackingStudentFromCourse(Student student,CourseKey courseKey) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_REMOVE_TRACKING_STUDENT_FROM_COURSE;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		intent.putExtra(CoolieServerInterfaceService.INPUT2, new Gson().toJson(courseKey));
		mContext.startService(intent);
	}
	
///////teletech PART

	public void getAllContacts() {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TELETECH_GET_ALL_CONTACTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		mContext.startService(intent);
	}
	
	
///////webcourse PART	
	
	public void getStaffInfo(CourseData courseData) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.WEBCOURSE_GET_STAFF_INFO;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(courseData));
		mContext.startService(intent);
	}
	
}




