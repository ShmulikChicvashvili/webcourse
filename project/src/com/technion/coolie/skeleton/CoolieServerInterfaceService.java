package com.technion.coolie.skeleton;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.CoolieServerInterface;
import com.technion.coolie.CoolieServerInterface.Action;
import com.technion.coolie.server.ug.api.UgFactory;
import com.technion.coolie.server.ug.framework.CourseKey;
import com.technion.coolie.server.ug.framework.CourseServer;
import com.technion.coolie.server.ug.framework.Semester;
import com.technion.coolie.server.ug.framework.Student;

import android.app.IntentService;
import android.content.Intent;


public class CoolieServerInterfaceService extends IntentService {

	public static final String INPUT = "input";
	public static final String ACTION = "action";
	public static final String STATUS = "status";
	public static final String RESULT = "result";
	
	
	public static final String NOTIFICATION = "com.technion.coolie.CoolieServerInterface";
	
	
	public static final String SEMESTER = "semester";
	
	
	public CoolieServerInterfaceService() {
		super("CoolieServerInterface");
		
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		Intent intent = new Intent(NOTIFICATION);
		CoolieServerInterface.Action action = (Action) arg0.getSerializableExtra(ACTION);
		
		switch (action)
		{
			case UG_GET_ALL_COURSES:
			{
				Semester semester = (Semester) arg0.getSerializableExtra(RESULT);
				List<CourseServer> list;
				list = UgFactory.getUgCourse().getAllCourses(semester);
				
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(list));
				sendBroadcast(intent);
				break;
			}
		
			case UG_GET_COURSES:
			{
				List<CourseKey> listInput;
				List<CourseServer> listOutput;
				listInput = new Gson().fromJson((String)arg0.getSerializableExtra(RESULT), new TypeToken<List<CourseKey>>() {
			    }.getType());
				
				listOutput = UgFactory.getUgCourse().getCourses(listInput);
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(listOutput));
				sendBroadcast(intent);
				break;
			}
			
			case UG_GET_STUDENT_CURRENT_COURSES:
			{
				Student student;
				List<CourseServer> listOutput;
				student = new Gson().fromJson((String)arg0.getSerializableExtra(RESULT),Student.class);
				
				listOutput = UgFactory.getUgCourse().getStudentCurrentCourses(student);
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(listOutput));
				sendBroadcast(intent);
				break;
			}
				
		
		
		
		
		
		
		
		}
		
	}

}
