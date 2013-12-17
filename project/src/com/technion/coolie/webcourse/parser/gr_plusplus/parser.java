package com.parser.gr_plusplus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.gr_plusplus.*;
import com.example.gr_plusplus.Lession.Days;

import support_communication.api.CommunicationServiceFactory;
import support_communication.api.IHtmlService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class parser {

	public static ArrayList<AnnouncementsData> parseAnnouncements(
			String courseNum) {

		IHtmlService h = CommunicationServiceFactory.getHtmlService();
		String url = "http://webcourse.cs.technion.ac.il/" + courseNum
				+ "/Winter2013-2014/en/news.html";
		String html = h.getHtmlPage(url);
		if (html == null) {
			System.out.println("fuckush");
		}
		Document doc = Jsoup.parse(html);
		String s = "";
		for (int i = 0; i < doc.select("font").size(); i++) {
			String temp = doc.select("font").get(i).text();
			s = s + "\n" + temp;
		}
		System.out.println(s);
		ArrayList<AnnouncementsData> annData = new ArrayList<AnnouncementsData>();
		Pattern p = Pattern.compile("(.*)\n(.*)\nCreated on (.*)\n");
		Pattern p2 = Pattern.compile("(.*)\n(.*)\nLast updated on (.*)\n");
		Matcher m = p.matcher(s);
		Matcher m2 = p2.matcher(s);
		boolean a = m.find();
		boolean b = m2.find();
		while (a || b) {
			if (a) {
				AnnouncementsData announcement = new AnnouncementsData(
						m.group(1), m.group(2), m.group(3));
				annData.add(announcement);
			}
			if (b) {
				AnnouncementsData announcement = new AnnouncementsData(
						m2.group(1), m2.group(2), m2.group(3));
				annData.add(announcement);
			}
			a = m.find();
			b = m2.find();
		}
		return annData;
	}

	public static ArrayList<StaffData> parseStaff(String courseNum) {

		IHtmlService h = CommunicationServiceFactory.getHtmlService();
		String url = "http://webcourse.cs.technion.ac.il/"+courseNum+"/Winter2013-2014/en/staff.html";
		String html = h
				.getHtmlPage(url);
		Document doc = Jsoup.parse(html);
		// This is Temporary for the first iteration
		List<Lession> yechielOffice = Arrays.asList(
				new Lession(Days.Monday,  new LessionTime(13, 30), new LessionTime(14, 30), "Taub 319"),
				new Lession(Days.Wednesday,  new LessionTime(13, 30), new LessionTime(14, 30), "Taub 319"));
		List<Lession> yechielLectures = Arrays.asList( 
				new Lession(Days.Monday,  new LessionTime(11, 30), new LessionTime(13, 30), "Taub 7"),
				new Lession(Days.Wednesday,  new LessionTime(11, 30), new LessionTime(13, 30), "Taub 7"));
		
		List<Lession> raedaOffice = Arrays.asList(
				new Lession(Days.Thursday,  new LessionTime(12, 30), new LessionTime(13, 30), "Taub 409"));
		List<Lession> raedaLectures = Arrays.asList( 
				new Lession(Days.Sunday,  new LessionTime(10, 30), new LessionTime(12, 30), "Taub 4"),
				new Lession(Days.Monday,  new LessionTime(14, 30), new LessionTime(16, 30), "Taub 4"));
		
		List<Lession> hasanOffice = Arrays.asList( 
				new Lession(Days.Thursday,  new LessionTime(10, 0), new LessionTime(11, 30), "Taub 505"));
		List<Lession> hasanLectures = Arrays.asList( 
				new Lession(Days.Sunday,  new LessionTime(14, 30), new LessionTime(16, 30), "Taub 4"),
				new Lession(Days.Tuesday,  new LessionTime(12, 30), new LessionTime(14, 30), "Taub 8"));
		
		StaffSubData[] subData = new StaffSubData[] {
				new StaffSubData("04-829-3373", new ArrayList<Lession>(yechielOffice), new ArrayList<Lession>(yechielLectures)),
				new StaffSubData("04-829-4886", new ArrayList<Lession>(raedaOffice), new ArrayList<Lession>(raedaLectures)),
				new StaffSubData("04-829-4936", new ArrayList<Lession>(hasanOffice), new ArrayList<Lession>(hasanLectures))
		};
		ArrayList<StaffData> staffD = new ArrayList<StaffData>();
		
		String s = "";
		for (int i = 0; i < doc.select("font").size(); i++) {

			String temp = doc.select("font").get(i).text();
			s = s + "\n" + temp;

		}
		s = s + "\n";

		Pattern p = Pattern
				.compile("(.*)\nPosition:.*?\n(.*)\nEmail:.*?\n([\\w,.,@]+)\n.*(\nPhone:.*?\n(.*))?(\nOffice location:.*?\n(.*))?(\nOffice hours:.*?\n(.*))?(\nLectures:.*?\n(.*))?");

		Matcher m = p.matcher(s);
		while (m.find()) {

			String sName = m.group(1);
			String sPosition = m.group(2);
			String sEmail = m.group(3);
			StaffData staff = new StaffData(sName, sPosition, sEmail, subData[0]);
			if (m.group(4) != null) {
				String sPhone = m.group(5);
			}
			if (m.group(6) != null) {
				String sLocation = m.group(7);
			}
			if (m.group(8) != null) {
				String sOfficeHours = m.group(9);
			}
			if (m.group(10) != null) {
				String sLectures = m.group(11);
			}
			staffD.add(staff);
		}

		return staffD;
	}

}
