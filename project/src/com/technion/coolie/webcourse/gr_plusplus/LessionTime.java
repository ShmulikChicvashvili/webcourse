package com.example.gr_plusplus;

public class LessionTime {
	
	final private int mHours;
	final private int mMinutes;
	
	public LessionTime() {
		mHours = 0;
		mMinutes = 0;	
	}
	
	public LessionTime(int hours, int minutes) {
		mHours = hours;
		mMinutes = minutes;
	}

	public int getHours() {
		return mHours;
	}
	
	public int getMinutes() {
		return mMinutes;
	}
	
	public String toString() {
		return (String.valueOf(mHours) + ":" + String.valueOf(mMinutes));
	}
}
