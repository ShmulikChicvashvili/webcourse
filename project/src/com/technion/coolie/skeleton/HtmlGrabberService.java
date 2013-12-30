package com.technion.coolie.skeleton;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.technion.coolie.HtmlGrabber.Account;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HtmlGrabberService extends IntentService {

	public static final String STATUS = "status";
	public static final String RESULT = "result";
	public static final String URL = "url";
	public static final String ACCOUNT = "account";
	public static final String NOTIFICATION = "com.technion.coolie.HtmlGrabber";
	
	public HtmlGrabberService() {
		super("HtmlGrabber");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		InputStream in;
		Intent intent = new Intent(NOTIFICATION);;
		try {
			URL url = new URL(arg0.getStringExtra(URL));
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			if(arg0.getSerializableExtra(ACCOUNT).equals(Account.FACEBOOK)) {
				Bitmap b = BitmapFactory.decodeStream(is);
				if(b != null)
					intent.putExtra("Image", b);
			}
			in = new BufferedInputStream(is);
			
			String res = readStream(in);
		
			
			Log.v("---",res.toString());
			intent.putExtra(RESULT, res);
			intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
			
			in.close();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendBroadcast(intent);
	}

	private String readStream(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			Log.e("something bad", "IOException", e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.e("something bad", "IOException", e);
			}
		}
		return sb.toString();
	}
}
