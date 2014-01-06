package com.technion.coolie.skeleton;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technion.coolie.skeleton.CoolieAccount;
import com.technion.coolie.R;

public class AccountPreference extends DialogPreference
{
	Context mContext;
	CoolieAccount account;
	public AccountPreference(CoolieAccount account, Context context) {
		super(context, null);
		mContext = context;
		this.account = account;
		this.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				
				Dialog d = getDialog();
				d.show();
				
				//SignonDialog dialog = new SignonDialog(acc);
				
				//getFragmentManager()
				//dialog.show(, "bla");
				return false;
			}
		  });

	}
	
	@Override
	protected View onCreateView(ViewGroup parent) {
		
		LayoutInflater inf = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout layout = (RelativeLayout) inf.inflate(R.layout.skel_preferences_single_account, null);
		
		TextView title = (TextView) layout.findViewById(R.id.skel_preference_account_name);
		title.setText(account.getName());
		
		ImageView image = (ImageView) layout.findViewById(R.id.skel_preference_account_image);
		image.setImageResource(account.getImageResource());
		
		Button status = (Button) layout.findViewById(R.id.skel_preference_account_status_button);
		
		if(account.isAlreadyConnected())
		{
			TextView info = (TextView) layout.findViewById(R.id.skel_preference_account_info);
			info.setText(mContext.getString(R.string.skel_preference_account_logged_in_as) + account.getUsername());
			
			status.setText(R.string.skel_logout);
		}
		else
		{
			status.setText(R.string.skel_signin);
		}
		
		return layout;
	}
	
	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
         	   getDialog().cancel();
            }
        };
		
		SingonDialogBuilder.setDialogAppearance(builder, inflater, mContext, account, cancelListener);
		
		super.onPrepareDialogBuilder(builder);
	}
	
	/*@Override
	protected void onClick() {
		Dialog d = getDialog();
		d.show();
		super.onClick();
	}*/
}
