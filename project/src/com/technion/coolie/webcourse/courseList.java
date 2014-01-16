package com.technion.coolie.webcourse;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class courseList {
	public static ArrayList getCourses(String TZ,String Pass) throws IOException {
		ArrayList al = new ArrayList();
		
		Document doc=Jsoup.connect("http://grades.cs.technion.ac.il/grades.cgi")
				 .data("Login", "1")
				 .data("Course", "")
				 .data("Page", "Grades")
				 .data("SEM", "")
				 .data("ID", TZ) 
				 .data("Password", Pass).method(Method.POST) 
	             .execute().parse();
		
		String s = "";
		for (int i = 0; i < doc.select("font").size(); i++) {
		
			String temp = doc.select("font").get(i).text();
			s = s + "\n" + temp;
			 
		}
		s= s + "\n";
	//	System.out.println(s);
		
		
		Pattern pEng= Pattern.compile("Course List((.|\n)*)Add the following course number to list:");
		Matcher mEng = pEng.matcher(s);
		String s1 = "";
		if (mEng.find()){
			s1 = mEng.group(1);
		}
		
		Pattern pHe= Pattern.compile("רשימת הקורסים((.|\n)*)הוסף את מספר הקורס");
		Matcher mHe = pHe.matcher(s);
		if (mHe.find()){
			s1 = mHe.group(1);
		}
	//	System.out.println(s1);
		
		
	//	System.out.println(s1);
		Pattern pCourseNum= Pattern.compile("\n(\\d+)\n(.*)");
		Matcher mCourseNum = pCourseNum.matcher(s1);
		while(mCourseNum.find()){
			
			al.add(mCourseNum.group(1)+ " - " + mCourseNum.group(2));

		}
//		System.out.println(al.size());
//		System.out.println(al.get(0));
//		System.out.println(al.get(1));
//		System.out.println(al.get(2));
//		System.out.println(al.get(3));
////		System.out.println(al.get(4));
		return al;
	            
	}


}
