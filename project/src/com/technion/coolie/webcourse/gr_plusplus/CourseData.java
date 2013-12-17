package com.example.gr_plusplus;


public class CourseData {
	
	String CourseName;
	String CourseDescription;
	
	public CourseData(String name, String desc) {  
          CourseName = name;
          CourseDescription = desc;
    }
	
	public String getName() {
		return CourseName;
	}
	
	public String getDesc() {
		return CourseDescription;
	}
	

}
