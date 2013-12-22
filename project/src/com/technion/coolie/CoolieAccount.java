package com.technion.coolie;

/**
 * 
 * This enum describes if an account authorization is needed before getting
 * the string from the URL
 * 
 */
public enum CoolieAccount {
	NONE(""), UG("UG"), GOOGLE("google"), FACEBOOK("Facebook"), MATHNET("Mathnet"), MOODLE("Moodle"), WEBCOURSE("Webcourse"), PHMOODLE("PHMoodle");
	
	private String name;
	private CoolieAccount(String name) {
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
}