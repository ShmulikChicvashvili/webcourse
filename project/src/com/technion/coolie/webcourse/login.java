package com.technion.coolie.webcourse;


import java.io.IOException;

//import org.jsoup.Connection;
import org.jsoup.Connection.Method;
//import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;

public class login {

 public static boolean is_leggal(String TZ,String Pass) throws IOException {
	 return (Jsoup.connect("http://grades.cs.technion.ac.il/grades.cgi")
			 .data("Login", "1")
			 .data("Course", "")
			 .data("Page", "Grades")
			 .data("SEM", "")
			 .data("ID", TZ) 
			 .data("Password", Pass).method(Method.POST)
             .execute().body().contains("<meta http-equiv=\"Expires\" content=\"-1\">") == false);

	 
 }
 
			
}


