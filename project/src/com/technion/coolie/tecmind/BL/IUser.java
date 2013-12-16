package com.technion.coolie.tecmind.BL;

import java.util.Date;

public interface IUser {
	public void initiateFieldsFromServer(String sName, Title sTitle, Date sLastMining, int sTotalTechoins, int sBankAccount);
	
	public Post getPostById(String postId);
}
