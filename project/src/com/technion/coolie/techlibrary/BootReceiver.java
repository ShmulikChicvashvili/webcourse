package com.technion.coolie.techlibrary;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v4.app.ShareCompat.IntentReader;
import android.util.Log;

public class BootReceiver extends IntentService  {

	public BootReceiver() {
		super("bookReceiver");
		// TODO Auto-generated constructor stub
	}
//
//	public void onReceive(Context context, Intent intent) {
//		Log.d("onReceive", "hello");
//		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
//			new MainActivity().startAlarm(/*AlarmManager.INTERVAL_DAY*/);
//		}
//	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("onReceive", "hello");
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			new MainActivity().startAlarm(/*AlarmManager.INTERVAL_DAY*/);
		}
	}
	

}



