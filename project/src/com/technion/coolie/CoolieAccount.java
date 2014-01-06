package com.technion.coolie;

import android.content.Context;

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
	
	private com.technion.coolie.skeleton.CoolieAccount mAccount;
	
	private CoolieAccount() {
		mAccount = com.technion.coolie.skeleton.CoolieAccount.valueOf(this.name());
	}
	
    public String getUsername()
    {
    	return mAccount.getUsername();
    }
    
    public boolean isAlreadyConnected()
    {
    	return mAccount.isAlreadyConnected();
    }
}