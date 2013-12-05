package com.technion.coolie.teletech;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.teletech.ContactSummaryFragment.OnContactSelectedListener;

public class MainActivity extends CoolieActivity implements
		OnContactSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.teletech_main);
		if (findViewById(com.technion.coolie.R.id.fragment_container) != null) {
			FragmentTransaction trans = getSupportFragmentManager()
					.beginTransaction();
			trans.replace(com.technion.coolie.R.id.fragment_container,
					new ContactSummaryFragment());
			trans.commit();
		}

	}

	@Override
	public void onContactSelected(int position) {
		FullContactInformation contact = (FullContactInformation) getSupportFragmentManager()
				.findFragmentById(com.technion.coolie.R.id.contact_fragment);
		if (contact != null)
			// This means that the layout is large and that we only need to
			// update the view to display.
			contact.updateContactInformationView(position);
		else {
			// We are in a small layout and we need to replace the fragment that
			// is presented.
			FullContactInformation newContact = new FullContactInformation();
			Bundle args = new Bundle();
			args.putInt(FullContactInformation.ARG_POSITION_STRING, position);
			newContact.setArguments(args);
			FragmentTransaction trans = getSupportFragmentManager()
					.beginTransaction();
			trans.replace(com.technion.coolie.R.id.fragment_container,
					newContact);
			trans.addToBackStack(null);
			trans.commit();
		}
	}

	/**
	 * @param v
	 */
	public void onPhoneNumberClicked(View v) {
		String phone_no = ((TextView) v).getText().toString()
				.replaceAll("-", "");
		if (phone_no.equals("NA"))
			return;
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + phone_no));
		startActivity(callIntent);
	}
	
	/**
	 * @param v
	 */
	public void onEmailClicked(View v){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String mailTo = ((TextView) v).getText().toString();
		if(mailTo == null)
			return;
		intent.setData(Uri.parse("mailto:"+mailTo));
		startActivity(intent);
	}
	
	/**
	 * @param v
	 */
	public void onWebsiteClicked(View v){
		String website = ((TextView)v).getText().toString();
		if(website == null)
			return;
		Intent i = new Intent(Intent.ACTION_VIEW, 
			       Uri.parse(website));
			startActivity(i);
	}

}
