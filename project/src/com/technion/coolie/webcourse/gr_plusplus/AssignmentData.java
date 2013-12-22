package com.technion.coolie.webcourse.gr_plusplus;


public class AssignmentData {
	
	private String mAssignmentName;
	private String mAssignmentDesc;
	private String mAssignmentDueDate;
	private boolean mIsAssignmentDone;
	
	AssignmentData() {
		
	}
	
	public AssignmentData(String name, String desc, String dueDate, boolean isAssignmentDone) {  
		mAssignmentName = name;
		mAssignmentDesc = desc;
		mAssignmentDueDate = dueDate;
		mIsAssignmentDone = isAssignmentDone;
    }
	
	public String getName() {
		return mAssignmentName;
	}
	
	public String getDescription() {
		return mAssignmentDesc;
	}
	
	public String getDueDate() {
		return mAssignmentDueDate;
	}
	
	public boolean getIsDone() {
		return mIsAssignmentDone;
	}
}
