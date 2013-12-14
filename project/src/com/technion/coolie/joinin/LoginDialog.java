package com.technion.coolie.joinin;

import com.facebook.widget.LoginButton;
import com.technion.coolie.R;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.facebook.FacebookLogin;
import com.technion.coolie.joinin.facebook.FacebookLogin.OnLoginDone;
import com.technion.coolie.joinin.map.MainMapActivity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.Toast;



public class LoginDialog  extends Dialog {
	
	private Activity mActivity = null;
	FacebookLogin.OnLoginDone mAfterLogin = null;
	
	
	
	public LoginDialog(Activity activity, FacebookLogin.OnLoginDone afterLogin) {
		super(activity); 
		mActivity = activity;
		mAfterLogin = afterLogin;
	}
	
	@Override 
	protected void onCreate(final Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	    getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.ji_dialog_login);	    
	    ((com.facebook.widget.LoginButton) findViewById(R.id.authButton)).setOnClickListener(new View.OnClickListener() {
	    	@Override 
	    	public void onClick(final View v) {	    		
	    		FacebookLogin.login(mActivity, mAfterLogin);
	    		dismiss();
	    	}
	    });
	}
	
	  @Override 
	  public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		  if (keyCode == KeyEvent.KEYCODE_BACK)
			  mActivity.finish();
		  return true;
	  }
}
