package com.technion.coolie.skeleton;

import com.technion.coolie.skeleton.PrivateCoolieAccount;
import com.technion.coolie.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignonDialog extends DialogFragment {
	
	private PrivateCoolieAccount account;
	
	public SignonDialog(PrivateCoolieAccount acc) {
		account = acc;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
         	   SignonDialog.this.getDialog().cancel();
            }
        };
        
	    SingonDialogBuilder.setDialogAppearance(builder, inflater, getActivity(), account, cancelListener);
	    return builder.create();
	}
}