package com.technion.coolie;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

/**
 * 
 * This enum describes if an account authorization is needed before getting
 * the string from the URL
 * 
 */
public enum CoolieAccount {
	NONE, UG,
	GOOGLE,
	FACEBOOK,
	MATHNET,
	MOODLE,
	WEBCOURSE,
	PHMOODLE,
	LIBRARY;
	
	private com.technion.coolie.skeleton.PrivateCoolieAccount mAccount;
	
	private CoolieAccount() {
		mAccount = com.technion.coolie.skeleton.PrivateCoolieAccount.valueOf(this.name());
	}
	
    public String getUsername()
    {
    	return mAccount.getUsername();
    }
    
    public boolean isAlreadyLoggedIn()
    {
    	return mAccount.isAlreadyLoggedIn();
    }
    
    public void openSigninDialog(FragmentActivity activity)
    {
    	mAccount.openSigninDialog(activity);
    }
}