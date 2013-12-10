package com.technion.coolie.letmein.model;

public class  ContactInfo {
	public String name;
	public Long id;
	public String imageUri;
	public String phoneNumber;
	
	public ContactInfo(String name,Long id,String imageUri,String phoneNumber)
	{
		this.name = name;
		this.id = id;
		this.imageUri = imageUri;
		this.phoneNumber = phoneNumber;
	}
}
