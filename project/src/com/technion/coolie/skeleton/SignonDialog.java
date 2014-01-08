package com.technion.coolie.skeleton;

import com.technion.coolie.FacebookLogin;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.PrivateCoolieAccount;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignonDialog extends AlertDialog{
	private Activity mActivity;
	private PrivateCoolieAccount account;

	public SignonDialog(Activity act,PrivateCoolieAccount acc) {
		super(act);
		mActivity = act;
		account = acc;
	}
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	    
	    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
	    // Get the layout inflater

	    LayoutInflater inflater = mActivity.getLayoutInflater();

	    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
         	   SignonDialog.this.cancel();
            }
        };
        
	    SingonDialogBuilder.setDialogAppearance(builder, inflater, mActivity, account, cancelListener);
	    builder.create().show();
	   
	    }
	

	
}