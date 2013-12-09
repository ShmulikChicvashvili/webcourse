package com.technion.coolie.skeleton;

import com.technion.coolie.CoolieActivity;


public interface IFacebookAccount {
	public void login(CoolieActivity act);
	public void logout();
	public String getAccount();
}
