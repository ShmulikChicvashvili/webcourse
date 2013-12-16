package com.technion.coolie;



import com.technion.coolie.skeleton.CoolieServerInterfaceService;
import com.technion.coolie.skeleton.CoolieStatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public abstract class CoolieServerInterface {
	Context mContext;
	
	enum Action
	{
		
		
	}
	public CoolieServerInterface(Context c)
	{
		mContext = c;
		mContext.registerReceiver(receiver, new IntentFilter(
				CoolieServerInterfaceService.NOTIFICATION));
	}

	
	
	public abstract void handleResult(String result, CoolieStatus status);

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				CoolieStatus status = (CoolieStatus) bundle
						.getSerializable(CoolieServerInterfaceService.STATUS);
				//TODO handle errors
					String result = bundle.getString(CoolieServerInterfaceService.RESULT);
					handleResult(result, status);
			}
		}
	};

	protected void finalize() throws Throwable
	{
		mContext.unregisterReceiver(receiver);
	}
	
	
	public void doActionOnServer(Action action,String... input) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		// add infos for the service which file to download and where to store
		intent.putExtra(CoolieServerInterfaceService.INPUT,input);			
		mContext.startService(intent);
	}
	
	
}




