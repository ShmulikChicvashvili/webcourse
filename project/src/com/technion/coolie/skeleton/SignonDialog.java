package com.technion.coolie.skeleton;

import com.technion.coolie.CoolieAccount;
import com.technion.coolie.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignonDialog extends DialogFragment {
	
	private CoolieAccount account;
	
	public SignonDialog(CoolieAccount acc) {
		account = acc;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    LinearLayout v = (LinearLayout) inflater.inflate(R.layout.skel_singin_dialog, null);
	    TextView t = (TextView) v.findViewById(R.id.skel_singin_title);
	    t.setText(getActivity().getString(R.string.skel_signin_signin_to) + " " + account.getName());
	    builder.setView(v)
	    // Add action buttons
	           .setPositiveButton(R.string.skel_signin_signin_button, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   // sign in the user ...
	               }
	           })
	           .setNegativeButton(R.string.skel_signin_cancel_button, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   SignonDialog.this.getDialog().cancel();
	               }
	           });      
	    return builder.create();
	}
}