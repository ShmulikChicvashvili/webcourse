package com.technion.coolie;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;

import com.technion.coolie.R;



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
	    setCanceledOnTouchOutside(false);
	    getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.ji_dialog_login);	   	    
	    ((ImageButton) findViewById(R.id.authButton)).setOnClickListener(new View.OnClickListener() {
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
