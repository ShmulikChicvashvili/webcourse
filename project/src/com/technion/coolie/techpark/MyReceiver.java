package com.technion.coolie.techpark;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		
    	if (action != null && action.equals("com.example.android.mocklocation")) {
    	     Intent broadcastIntent = new Intent();
    	     
    	     int  activityType= intent.getIntExtra("com.example.android.mocklocation.activityType",-1);
		     Log.d("MockLlocation", "getting : " + activityType);   
    	     broadcastIntent.addCategory(ClientSideUtils.CATEGORY_LOCATION_SERVICES)
		        			   .setAction(ClientSideUtils.ACTION_REFRESH_STATUS_LIST)
					           .putExtra(ClientSideUtils.EXTRA_ACTIVITY_NAME, activityType);

		        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
    	}
		
	}

}
