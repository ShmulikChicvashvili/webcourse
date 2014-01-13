package com.technion.coolie.skeleton;

import java.util.Date;

import com.technion.coolie.FBClientAccount;
import com.technion.coolie.FacebookLogin;
import com.technion.coolie.LoginDialog;
import com.technion.coolie.R;
import com.technion.coolie.FacebookLogin.OnLoginDone;
import com.technion.coolie.skeleton.AccountPreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * 
 * This enum describes if an account authorization is needed before getting the
 * string from the URL
 * 
 */
public enum PrivateCoolieAccount {
	NONE("", 0), UG("UG", R.drawable.skel_ug_icon), GOOGLE("google",
			R.drawable.skel_google_icon), FACEBOOK("Facebook",
			R.drawable.skel_facebook_icon), MATHNET("Mathnet",
			R.drawable.skel_mathnet_icon), MOODLE("Moodle",
			R.drawable.skel_moodle_icon), WEBCOURSE("Webcourse",
			R.drawable.skel_webcourse_icon), PHMOODLE("PHMoodle",
			R.drawable.skel_phmoodle_icon), LIBRARY("Library",
			R.drawable.skel_library_icon);

	private String name;
	private int imageResource;
	private String username;
	private String password;
	private AccountPreference preference;
	private boolean alreadyConnected;

	final String PREFS_NAME = "com.technion.coolie";
	FBClientAccount mLoggedAccount = null;
	LoginDialog mLoginDialog = null;
	SharedPreferences mCooliePref = null;

	private PrivateCoolieAccount(String name, int imageResource) {
		this.name = name;
		this.imageResource = imageResource;

		username = null;
		password = null;
		alreadyConnected = false;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getImageResource() {
		return imageResource;
	}
	
	public void setImageResource(int imageResource) {
		this.imageResource = imageResource;
	}

	public void onDialogSignon(String username, String password) {
		this.username = username;
		this.password = password;

		this.alreadyConnected = true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public AccountPreference getPreference(Context context) {
		preference = new AccountPreference(this, context);
		return preference;
	}
	
	public void setPreference(AccountPreference pref) {
		this.preference = pref;
	}

	public boolean isAlreadyLoggedIn() {
		return alreadyConnected;
	}
	
	public void setAlreadyLoggedIn(boolean alreadyConnected){
		this.alreadyConnected = alreadyConnected;
	}

	public String getPreferenceName() {
		return name;
	}

	public void openSigninDialog(final Activity a) {

		final FacebookLogin.OnLoginDone afterLogin = new OnLoginDone() {
			@Override
			public void loginCallback(final FBClientAccount account) {
				// Login success
				if (account != null) {
					mLoggedAccount = account;
					mCooliePref.edit().clear()
							.putString("account", mLoggedAccount.toJson())
							.commit();

				}
				// Login fail
				else {
					Toast.makeText(a.getApplicationContext(),
							"Failed login please try again", Toast.LENGTH_LONG)
							.show();
					mLoginDialog.show();
				}
			}
		};
		mCooliePref = a.getSharedPreferences(PREFS_NAME, 0);
		mCooliePref.edit().commit();
		switch (this) {
		case FACEBOOK:
			mLoginDialog = new LoginDialog(a, afterLogin);
			FacebookLogin.login(a, afterLogin);
			break;
		case GOOGLE:
			break;
		default:
			SignonDialog s = new SignonDialog(a, this);
			s.show();
			break;
		}

	}

	public static class serializeClass {
		public String name;
		public int imageResource;
		public String username;
		public String password;
		//public AccountPreference preference;
		public boolean alreadyConnected;
	}

}
