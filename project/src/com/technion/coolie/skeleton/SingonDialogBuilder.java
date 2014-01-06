package com.technion.coolie.skeleton;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technion.coolie.skeleton.PrivateCoolieAccount;
import com.technion.coolie.R;

public class SingonDialogBuilder {
	
	public static void setDialogAppearance(AlertDialog.Builder builder, LayoutInflater inflater, Context c, final PrivateCoolieAccount account, DialogInterface.OnClickListener cancelListener)
	{
		// Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.skel_singin_dialog, null);
	    
	    TextView title = (TextView) layout.findViewById(R.id.skel_singin_title);
	    title.setText(c.getString(R.string.skel_signin_signin_to) + " " + account.getName());
	 
	    final EditText userNameField = (EditText) layout.findViewById(R.id.skel_signin_username_field);
	    final EditText passwordField = (EditText) layout.findViewById(R.id.skel_signin_password_field);
	    
	    builder.setView(layout)
	    // Add action buttons
	           .setPositiveButton(R.string.skel_signin, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   String username = userNameField.getText().toString();
	            	   String password = passwordField.getText().toString();
	            	   
	            	   //TODO add authentication.
	            	   account.onDialogSignon(username, password);
	               }
	           })
	           .setNegativeButton(R.string.skel_cancel, cancelListener);
	}
}
