package com.technion.coolie.skeleton;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.CoolieServerInterface;
import com.technion.coolie.CoolieServerInterface.Action;
import com.technion.coolie.server.teletech.api.TeletechFactory;
import com.technion.coolie.server.teletech.framework.ContactInformation;
import com.technion.coolie.server.ug.ReturnCodesUg;
import com.technion.coolie.server.ug.api.UgFactory;
import com.technion.coolie.server.ug.framework.AcademicCalendarEvent;
import com.technion.coolie.server.ug.framework.AccomplishedCourse;
import com.technion.coolie.server.ug.framework.CourseKey;
import com.technion.coolie.server.ug.framework.CourseServer;
import com.technion.coolie.server.ug.framework.Exam;
import com.technion.coolie.server.ug.framework.Payment;
import com.technion.coolie.server.ug.framework.Semester;
import com.technion.coolie.server.ug.framework.Student;
import com.technion.coolie.server.webcourse.api.WebcourseFactory;
import com.technion.coolie.server.webcourse.framework.CourseData;
import com.technion.coolie.server.webcourse.framework.StaffData;

import android.app.IntentService;
import android.content.Intent;


public class CoolieServerInterfaceService extends IntentService {

	public static final String INPUT = "input";
	public static final String INPUT2 = "input2";
	public static final String INPUT3 = "input3";
	public static final String INPUT4 = "input4";
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
				Semester semester = (Semester) arg0.getSerializableExtra(INPUT);
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
				listInput = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT), new TypeToken<List<CourseKey>>() {
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
				Semester semester;
				List<CourseServer> listOutput;
				student = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT),Student.class);
				semester = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT2),Semester.class);
				
				listOutput = UgFactory.getUgCourse().getStudentCurrentCourses(student,semester);
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(listOutput));
				sendBroadcast(intent);
				break;
			}
				
			case UG_GET_ALL_ACADEMIC_EVENTS:
			{
				
				List<AcademicCalendarEvent> listOutput;
				
				listOutput = UgFactory.getUgEvent().getAllAcademicEvents();
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(listOutput));
				sendBroadcast(intent);
				break;
			}
			
			case UG_GET_STUDENT_EXAMS:
			{
				Student student;
				Semester semester;
				List<Exam> listOutput;
				student = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT),Student.class);
				semester = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT2),Semester.class);
				
				listOutput = UgFactory.getUgExam().getStudentExams(student, semester);
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(listOutput));
				sendBroadcast(intent);
				break;
			}
		
			case UG_GET_MY_GRADES_SHEET:
			{
				Student student;
				List<AccomplishedCourse> listOutput;
				student = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT),Student.class);
				
				listOutput = UgFactory.getUgGradeSheet().getMyGradesSheet(student);
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(listOutput));
				sendBroadcast(intent);
				break;
			}
			
			case UG_GET_STUDENT_PAYMENTS:
			{
				Student student;
				List<Payment> listOutput;
				student = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT),Student.class);
				
				listOutput = UgFactory.getUgPayments().getStudentPayments(student);
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(listOutput));
				sendBroadcast(intent);
				break;
			}
		
			case UG_ADD_TRACKING_STUDENT:
			{
				Student student;
				CourseKey courseKey;
				ReturnCodesUg Output;
				student = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT),Student.class);
				courseKey = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT2),CourseKey.class);
				
				Output = UgFactory.getUgTracking().addTrackingStudent(student, courseKey);
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(Output));
				sendBroadcast(intent);
				break;
			}
			
			case UG_REMOVE_TRACKING_STUDENT_FROM_COURSE:
			{
				Student student;
				CourseKey courseKey;
				ReturnCodesUg Output;
				student = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT),Student.class);
				courseKey = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT2),CourseKey.class);
				
				Output = UgFactory.getUgTracking().removeTrackingStudentFromCourse(student, courseKey);
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(Output));
				sendBroadcast(intent);
				break;
			}
		
			case TELETECH_GET_ALL_CONTACTS:
			{
				
				List<ContactInformation> listOutput;
				
				listOutput = TeletechFactory.getTeletech().getAllContacts();
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(listOutput));
				sendBroadcast(intent);
				break;
			}
			
			case WEBCOURSE_GET_STAFF_INFO:
			{
				CourseData courseData;
				List<StaffData> listOutput;
				courseData = new Gson().fromJson((String)arg0.getSerializableExtra(INPUT),CourseData.class);
				
				listOutput = WebcourseFactory.getWebcourseManager().getStaffInfo(courseData);
				intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
				intent.putExtra(CoolieServerInterfaceService.RESULT, new Gson().toJson(listOutput));
				sendBroadcast(intent);
				break;
			}
		
		}
		
	}

}
