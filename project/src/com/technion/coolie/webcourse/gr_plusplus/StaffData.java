package com.technion.coolie.webcourse.gr_plusplus;


public class StaffData {
	
	String mName;
	String mMail;
	String mPosition;
	byte[] image;
	StaffSubData mStaffSubData;
	
	StaffData() {
		
	}
	
	
	public StaffData(String name, String position, String mail, StaffSubData staffSubData) {  
          mName = name;
          mPosition = position;
          mMail = mail; 
          mStaffSubData = staffSubData;
    }
	
	public String getName() {
		return mName;
	}
	
	public String getMail() {
		return mMail;
	}
	
	public String getPosition() {
		return mPosition;
	}
	
	public StaffSubData getSubData() {
		return mStaffSubData;
	}
}