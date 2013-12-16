package com.technion.coolie;



import java.util.List;

import com.google.gson.Gson;
import com.technion.coolie.server.ug.framework.CourseKey;
import com.technion.coolie.server.ug.framework.Semester;
import com.technion.coolie.server.ug.framework.Student;
import com.technion.coolie.skeleton.CoolieServerInterfaceService;
import com.technion.coolie.skeleton.CoolieStatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;


/*this is how u will use it on a your activity, u will need to create CoolieServerInterface for each function u do,
 * and deserialize the Gson appropriately on each handleResult u write
 * CoolieServerInterface c = new CoolieServerInterface(getApplicationContext()){

					@Override
					public void handleResult(String result, CoolieStatus status) {
						new Gson().fromJson(result, new TypeToken<List<CourseServer>>() {
					    }.getType());
						
					}
				};
				Semester semester = new Semester();
				c.getAllCourses(semester);
 */


public abstract class CoolieServerInterface {
	Context mContext;
	
	public enum Action
	{
		UG_GET_ALL_COURSES,
		UG_GET_COURSES,
		UG_GET_STUDENT_CURRENT_COURSES,
		
	
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
	
	
	public void getAllCourses(Semester semester) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_ALL_COURSES;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.RESULT,semester);		
		mContext.startService(intent);
	}
	
	public void getCourses(List<CourseKey> courseKeys) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_COURSES;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(courseKeys));
		mContext.startService(intent);
	}
	
	public void getStudentCurrentCourses(Student student) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_STUDENT_CURRENT_COURSES;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(student));
		mContext.startService(intent);
	}
}




