package com.technion.coolie.letmein.scapping;


import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import android.os.AsyncTask;


public class Scrapper {
	
	private static final String UA = "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko";
	private static final String LOGIN_FIELD = "j_username";
	private static final String LOGIN_PASS_FIELD = "j_password";
	private static final String LOGIN_BUTTON = "uidPasswordLogon";
	private static final String SITE = "http://portal.technion.ac.il";
	private static final String SALT = "j_salt";
	
	public static boolean CheckLogin(String username,String password)
	{
		Response r;
		try {
			Response resp = Jsoup.connect(SITE).userAgent(UA).method(Method.GET).execute();
			//System.out.println(resp.url());
			final String salt = resp.parse().getElementsByAttributeValue("name", SALT).first().val();
			//System.out.println(resp.body());
			//System.out.println(salt);
			final Map<String, String> cookies = resp.cookies();
			final Connection c = Jsoup.connect(resp.url().toString()).userAgent(UA).cookies(cookies)
			.data("login_submit", "on").data("login_do_redirect", "1").data(SALT, salt)
			.data(LOGIN_FIELD, username).data(LOGIN_PASS_FIELD, password)
			.data(LOGIN_BUTTON, "Log On").method(Method.POST);
			//final Request req = (Request) c.request();
			resp = c.execute();
			//System.out.println(resp.url());
			return resp.cookies().containsKey("JSESSIONMARKID");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}



