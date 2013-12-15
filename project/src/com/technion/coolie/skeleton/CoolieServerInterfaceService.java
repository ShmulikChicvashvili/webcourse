package com.technion.coolie.skeleton;

import android.app.IntentService;
import android.content.Intent;

public class CoolieServerInterfaceService extends IntentService {

	public static final String INPUT = "input";
	public static final String STATUS = "status";
	public static final String RESULT = "result";
	public static final String NOTIFICATION = "com.technion.coolie.CoolieServerInterface";
	
	
	public CoolieServerInterfaceService() {
		super("CoolieServerInterface");
		
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		Intent intent = new Intent(NOTIFICATION);
		
		
		sendBroadcast(intent);
	}

}
