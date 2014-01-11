package com.technion.coolie.webcourse;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.technion.coolie.R;



public class AssignmentsFragmentTab extends SherlockListFragment {
	
	public AssignmentListAdapter mAssignmentListAdapter;
	public ListView mList;
	
		
	
	AssignmentData[] assignments = new AssignmentData[] { 
			new AssignmentData("Dry1", "This is the dry submission num. 1", "15/12/13, 12:00", true),
			new AssignmentData("Wet1", "This is the wet submission num. 1", "20/12/13 - 23:59", false),
			new AssignmentData("Assignment 2", "Data Path, Controller, Dynamic Dicipline", "19/12/13 - 12:00", false),
			new AssignmentData("Assignment 3", "Just Some Description", "05/01/14 - 12:30", true),
			new AssignmentData("Wet2", "Last Wet for this semester", "20/01/14 - 23:59", false)
	};
	
	List<AssignmentData> assignmentsData = Arrays.asList(assignments);
	ArrayList<AssignmentData> mAssignmentList = new ArrayList<AssignmentData>(assignmentsData);
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
		View rootView = inflater.inflate(R.layout.web_assignments_fragment, container, false);
        
		 mList = (ListView) rootView.findViewById(android.R.id.list);
	     mAssignmentListAdapter = new AssignmentListAdapter(rootView.getContext(), mAssignmentList);
	     setListAdapter(mAssignmentListAdapter);
	        
	     return rootView;
    }
	
}
