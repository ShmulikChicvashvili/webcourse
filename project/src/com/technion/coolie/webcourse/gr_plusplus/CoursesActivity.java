package com.technion.coolie.webcourse.gr_plusplus;

import java.util.ArrayList;

import com.technion.coolie.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class CoursesActivity extends Activity {

	
	CourseData[] courses = new CourseData[] {	new CourseData("Operating Systems", "234123 - bla bla bla"), 
											 	new CourseData("Algorithem1", "234247 - bla bla bla"), 
											 	new CourseData("Logic Design", "234262 - bla bla bla"), 
											 	new CourseData("Programming Languages", "234319 - bla bla bla"), 
											 	new CourseData("Advanced Programming Project", "236503 - bla bla bla")
											};
	
	ArrayList<CourseData> mCoursesList = new ArrayList<CourseData>();
	
	public CoursesListAdapter coursesListAdapter;
	public ListView mList;
	public AlertDialog.Builder dlgAlert;
	
	public final static String EXTRA_MESSAGE = "com.example.gr_plusplus.MESSAGE";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Create list of courses
        populateListWithArray();
        
        setContentView(R.layout.web_activity_courses);
        mList = (ListView) findViewById(R.id.courses_list);
        CoursesListAdapter coursesListAdapter = new CoursesListAdapter(this, mCoursesList);
        mList.setAdapter(coursesListAdapter);
        dlgAlert  = new AlertDialog.Builder(this);
            
        OnItemClickListener itemClicked = new OnItemClickListener() {
        	
			@Override
			public void onItemClick(AdapterView<?> list, View view, int position, long id) {
				// TODO Auto-generated method stub
				loadCourseInformationActivity(((CourseData)mList.getItemAtPosition(position)).CourseDescription);				
			}
        };
        mList.setOnItemClickListener(itemClicked);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.courses, menu);
        return true;
    }
    
    
    public void loadCourseInformationActivity(String message) {
        
    	Intent intent = new Intent(this, CourseInformationActivity.class);
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);    	
    }
    
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
    
    private void populateListWithArray () {
    	
    	int index;
    	for (index = 0; index < courses.length; ++index)
    	{
    		mCoursesList.add(courses[index]);
    	}
    }
    
}
