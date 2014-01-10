package com.technion.coolie.ug.utils;

import android.content.Context;
import android.view.WindowManager;


public class UGCurrentState {
	
	public static String currentOpenFragment = "none";
	
	
	
	
	public static int getRotationAngle(Context context){
	    return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
	}
	
	
}
