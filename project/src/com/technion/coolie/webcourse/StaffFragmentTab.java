package com.technion.coolie.webcourse;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.technion.coolie.R;
import com.technion.coolie.server.webcourse.api.WebcourseFactory;
//import api.WebcourseFactory;



public class StaffFragmentTab extends SherlockListFragment {
    
	String mMessage;
	
//	List<Lession> yechielOffice = Arrays.asList(
//			new Lession(Days.Monday,  new LessionTime(13, 30), new LessionTime(14, 30), "Taub 319"),
//			new Lession(Days.Wednesday,  new LessionTime(13, 30), new LessionTime(14, 30), "Taub 319"));
//	List<Lession> yechielLectures = Arrays.asList( 
//			new Lession(Days.Monday,  new LessionTime(11, 30), new LessionTime(13, 30), "Taub 7"),
//			new Lession(Days.Wednesday,  new LessionTime(11, 30), new LessionTime(13, 30), "Taub 7"));
//	
//	List<Lession> raedaOffice = Arrays.asList(
//			new Lession(Days.Thursday,  new LessionTime(12, 30), new LessionTime(13, 30), "Taub 409"));
//	List<Lession> raedaLectures = Arrays.asList( 
//			new Lession(Days.Sunday,  new LessionTime(10, 30), new LessionTime(12, 30), "Taub 4"),
//			new Lession(Days.Monday,  new LessionTime(14, 30), new LessionTime(16, 30), "Taub 4"));
//	
//	List<Lession> hasanOffice = Arrays.asList( 
//			new Lession(Days.Thursday,  new LessionTime(10, 0), new LessionTime(11, 30), "Taub 505"));
//	List<Lession> hasanLectures = Arrays.asList( 
//			new Lession(Days.Sunday,  new LessionTime(14, 30), new LessionTime(16, 30), "Taub 4"),
//			new Lession(Days.Tuesday,  new LessionTime(12, 30), new LessionTime(14, 30), "Taub 8"));
//	
//	StaffSubData[] subData = new StaffSubData[] {
//			new StaffSubData("04-829-3373", new ArrayList<Lession>(yechielOffice), new ArrayList<Lession>(yechielLectures)),
//			new StaffSubData("04-829-4886", new ArrayList<Lession>(raedaOffice), new ArrayList<Lession>(raedaLectures)),
//			new StaffSubData("04-829-4936", new ArrayList<Lession>(hasanOffice), new ArrayList<Lession>(hasanLectures))
//	};
	
//	StaffData[] staff = new StaffData[] { 
//			new StaffData("Dr. Yechiel M. Kimchi", "Lecturer in charge", "yechiel@cs.technion.ac.il", subData[0]),
//			new StaffData("Raeda Naamneh", "Teaching Assistant in charge", "raeda@cs.technion.ac.il", subData[1]),
//			new StaffData("Hasan Abasi", "Teaching Assistant", "hassan@cs.technion.ac.il", subData[2])
//		};
//	List<StaffData> staffData = Arrays.asList(staff);
	List<StaffData> mStaffList = new ArrayList<StaffData>();
	
	public StaffListAdapter mStaffListAdapter;
	public ExpandableListView mList;
	public AlertDialog.Builder dlgAlert;
	
	//public final static String EXTRA_MESSAGE = "com.example.gr_plusplus.MESSAGE";
	@Override
	public void onListItemClick (ListView list, View view, int position, long id) {
			
		 String message = ((StaffData)mList.getItemAtPosition(position)).mName;
		 generateAlertMessage(dlgAlert, message, "List Item Clicked");
							
	 }
     
     
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
		final View rootView = inflater.inflate(R.layout.web_staff_fragment, container, false);
        
		//TextView tvStaff = (TextView) rootView.findViewById(R.id.staff_fragment_id);
		//tvStaff.setText(mMessage);
		
		//populateListWithArray ();
		String courseNumber = mMessage.substring(0, 6);
		asyncParse<StaffData> a = new asyncParse<StaffData>() {
			
			@Override
			protected List<StaffData> doInBackground(String... params) {
				// TODO Auto-generated method stub
				CourseData course = new CourseData(params[0], "");
				
				mStaffList = WebcourseFactory.getWebcourseManager().getStaffInfo(course);
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(List<StaffData> result) {
				// TODO Auto-generated method stub
				mList = (ExpandableListView) rootView.findViewById(android.R.id.list);
		        mStaffListAdapter = new StaffListAdapter(rootView.getContext(), mStaffList);
		        //mList.setAdapter(coursesListAdapter);
				//ListFragment.setListAdapter(mStaffListAdapter);
		        //setListAdapter(mStaffListAdapter);
		        mList.setAdapter(mStaffListAdapter);
		        //SherlockListFragment.setListAdapter(mStaffListAdapter);
				super.onPostExecute(result);
			}
			
		};
		a.execute(courseNumber);
		
//		mList = (ExpandableListView) rootView.findViewById(android.R.id.list);
//        mStaffListAdapter = new StaffListAdapter(rootView.getContext(), mStaffList);
//        //mList.setAdapter(coursesListAdapter);
//		//ListFragment.setListAdapter(mStaffListAdapter);
//        //setListAdapter(mStaffListAdapter);
//        mList.setAdapter(mStaffListAdapter);
//        //SherlockListFragment.setListAdapter(mStaffListAdapter);
       
     
        
        
       
        /*
        OnItemClickListener itemClicked = new OnItemClickListener() {
        	
			@Override
			public void onItemClick(AdapterView<?> list, View view, int position, long id) {
				// TODO Auto-generated method stub
				String message = ((StaffData)mList.getItemAtPosition(position)).mName;
				generateAlertMessage(dlgAlert, message, "List Item Clicked");				
			}
        };
        mList.setOnItemClickListener(itemClicked);
		*/
		return rootView;
				
    }
	
	public void SetIntentMessage (String message) {
		
		mMessage = message;
	}
	
	/*
	private void populateListWithArray () {
    	
    	int index;
    	for (index = 0; index < staff.length; ++index)
    	{
    		mStaffList.add(staff[index]);
    	}
    }
    */
	
	public void generateAlertMessage (AlertDialog.Builder dlgAlert, String message, String title) {
    	dlgAlert.setMessage(message);
		dlgAlert.setTitle(title);
		
		dlgAlert.setPositiveButton("Ok",
			    new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			          //dismiss the dialog  
			        }
			    });
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
    }
}

