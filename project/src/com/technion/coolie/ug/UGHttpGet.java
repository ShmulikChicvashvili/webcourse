package com.technion.coolie.ug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class UGHttpGet extends AsyncTask<String, Void, HttpResponse> 
{
	
	protected HttpResponse doInBackground(String... urls) {
		HttpResponse response1 = null;
		HttpResponse response2=null;
		String session = "rishum=QdeV2jBM5Bu0SPfaggNNfpfBydL-";
		try {
        	HttpParams httpParameters1 = new BasicHttpParams();
        	HttpConnectionParams.setSoTimeout(httpParameters1, 0);
        	HttpClient client1 = new DefaultHttpClient(httpParameters1);
        	String getURL1 = "http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&Add+to+my+basket.x=4&Add+to+my+basket.y=4&LGRP1="+"11"+"&LGRP2=&LGRP3=&LMK1="+"234111"+"&LMK2=&LMK3=&RSND=";
        	HttpUriRequest get1 = new HttpGet(getURL1);
        	get1.addHeader("Cookie", session);
        	response1 = client1.execute(get1);
        	
        	HttpParams httpParameters2 = new BasicHttpParams();
        	HttpConnectionParams.setSoTimeout(httpParameters2, 0);
        	HttpClient client2 = new DefaultHttpClient(httpParameters2);
        	String getURL2 = "http://techmvs.technion.ac.il:100/cics/WMN/wmnnut02?OP=RS&RUTHY=RUTHY&SALU23411111=&LGRP1=&LGRP2=&LGRP3=&LMK1=&LMK2=&LMK3=&RSND=SND";
        	HttpUriRequest get2 = new HttpGet(getURL2);        	
        	get2.addHeader("Cookie", session+"23411111");
        	get2.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");
        	response2 = client2.execute(get2);
        	
        	String responseAsString = EntityUtils.toString(response2.getEntity());
			Math.random();
        } 
        catch (Exception e) 
        {
            return null;
        }
        return response2;
    }
/*
    protected void onPostExecute(HttpResponse response) {
    	Header[] allHeaders = response.getAllHeaders();
    	try 
    	{
			String responseAsString = EntityUtils.toString(response.getEntity());
			Math.random();
		} 
    	catch (Exception e) {
			e.printStackTrace();
		}
    	Math.random();
    }*/
}
