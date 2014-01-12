package com.technion.coolie.techtrade;

import com.technion.coolie.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class UserOperations {
	/*TODO in here we supplied an interface to get the user details
	 * in our code where ever  we need to use the details we'll put a condition
	 * to check if the user is connected and if he isn't then we'll
	 * send him to login screen and if he does'nt login then we need to handle it somehow.*/
	public static Boolean isUserConnected(){
		return true;
//		return false;
	}
	
	public static String getUserName(){
		return "Moshe";
	}
	
	public static String getUserId(){
		return "12345678";
	}
	
	public static void openSigninDialog(){
		//TODO 
	}
	
}
