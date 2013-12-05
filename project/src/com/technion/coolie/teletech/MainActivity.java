package com.technion.coolie.teletech;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.teletech.ContactSummaryFragment.OnContactSelectedListener;

public class MainActivity extends CoolieActivity implements
		OnContactSelectedListener {

	private List<ContactInformation> contacts;
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.teletech_adittional_info);
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.technion.coolie.teletech.ContactSummaryFragment.OnContactSelectedListener
	 * #onContactSelected(int)
	 */
	@Override
	public void onContactSelected(int position) {
		FullContactInformation contact = (FullContactInformation) getSupportFragmentManager()
				.findFragmentById(com.technion.coolie.R.id.contact_fragment);
		if (contact != null)
			contact.updateContactInformationView(position);
		else {
			FullContactInformation newContact = new FullContactInformation();
			Bundle args = new Bundle();
			args.putInt(FullContactInformation.ARG_POSITION_STRING, position);
			newContact.setArguments(args);
			FragmentTransaction trans = getSupportFragmentManager()
					.beginTransaction();
			trans.replace(com.technion.coolie.R.id.contactsList, newContact);
			trans.addToBackStack(null);
			trans.commit();

		}

	}

}
