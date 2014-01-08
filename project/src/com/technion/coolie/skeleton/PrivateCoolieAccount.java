package com.technion.coolie.skeleton;

import com.technion.coolie.R;
import com.technion.coolie.skeleton.AccountPreference;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

/**
 * 
 * This enum describes if an account authorization is needed before getting
 * the string from the URL
 * 
 */
public enum PrivateCoolieAccount {
	NONE("", 0), UG("UG", R.drawable.skel_ug_icon),
	GOOGLE("google", R.drawable.skel_google_icon),
	FACEBOOK("Facebook", R.drawable.skel_facebook_icon),
	MATHNET("Mathnet", R.drawable.skel_mathnet_icon),
	MOODLE("Moodle", R.drawable.skel_moodle_icon),
	WEBCOURSE("Webcourse", R.drawable.skel_webcourse_icon),
	PHMOODLE("PHMoodle", R.drawable.skel_phmoodle_icon),
	LIBRARY("Library", R.drawable.skel_library_icon);
	
	private Context mContext;
	private String name;
	private int imageResource;
	private String username;
	private String password;
	private AccountPreference preference;
	private boolean alreadyConnected;
	
	private PrivateCoolieAccount(String name, int imageResource) {
		this.name = name;
		this.imageResource = imageResource;
		
		username = null;
		password = null;
		alreadyConnected = false;
	}
	
	public String getName()
	{
		return name;
	}
	public  int getImageResource()
	{
		return imageResource;
	}
	
    public void onDialogSignon(String username, String password)
    {
    	this.username = username;
    	this.password = password;
    	
    	this.alreadyConnected = true;
    }
	
    public String getUsername()
    {
    	return username;
    }
    
    public String getPassword()
    {
    	return password;
    }
    
    public AccountPreference getPreference(Context context)
    {
		preference = new AccountPreference(this, context);
    	return preference;
    }
    
    public boolean isAlreadyLoggedIn()
    {
    	return alreadyConnected;
    }
    
    public String getPreferenceName()
    {
    	return name;
    }
    
    public void openSigninDialog(FragmentActivity a)
    {
    	SignonDialog s = new SignonDialog(this);
    	s.show(a.getSupportFragmentManager(), "dialog");
    }
}