package com.technion.coolie.webcourse;


public class CourseData {
	
	String CourseName;
	String CourseDescription;
	
	public CourseData() {
		
	}
	
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
