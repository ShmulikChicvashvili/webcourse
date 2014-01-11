package com.technion.coolie.techtrade;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class ContactOperations {

	public static void sms(Context context, String address) {  
		try{
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setType("vnd.android-dir/mms-sms");
			smsIntent.putExtra("address", address);
			context.startActivity(smsIntent);
		}catch(ActivityNotFoundException  e){
			Toast.makeText(context, "no SMS possible from this device.\r\n to bad, so sad", Toast.LENGTH_SHORT).show();   
		}
	}

	public static void call(Context context, String address) {   
		try{
			Intent callIntent = new Intent(Intent.ACTION_DIAL);
			callIntent.setData(Uri.parse("tel:"+address));
			context.startActivity(callIntent);  
		}catch(ActivityNotFoundException  e){
			Toast.makeText(context, "no calls possible from this device.\r\n you can't even call home \r\n we have you now ET", Toast.LENGTH_SHORT).show();   
		}
	}
}
