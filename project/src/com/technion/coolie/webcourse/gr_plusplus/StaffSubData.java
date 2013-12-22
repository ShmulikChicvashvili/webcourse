package com.technion.coolie.webcourse.gr_plusplus;
import java.util.ArrayList;


public class StaffSubData {

	String mPhone;
	String mOfficeLocation;
	ArrayList<Lession> mOfficeHours;
	ArrayList<Lession> mLectures;
	
	StaffSubData() {
		
	}
	
	public StaffSubData(String phone, ArrayList<Lession> officeHours, ArrayList<Lession> lectures) {
		
		mPhone = phone;
		mOfficeLocation = officeHours.get(0).GetPlace();
		mOfficeHours = officeHours;
		mLectures = lectures;
	}
	
	public String getPhone() {
		return mPhone;
	}
	
	public ArrayList<Lession> getOfficeHours() {
		return mOfficeHours;
	}
	
	public ArrayList<Lession> getLectures() {
		return mLectures;
	}
	
	public String getOfficeLocation() {
		return mOfficeLocation;
	}
}
