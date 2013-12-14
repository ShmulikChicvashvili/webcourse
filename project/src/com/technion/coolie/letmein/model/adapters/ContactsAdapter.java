package com.technion.coolie.letmein.model.adapters;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.net.Uri;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.letmein.model.ContactInfo;
import com.technion.coolie.letmein.model.ContactInfoViewHolder;
import com.technion.coolie.letmein.model.Invitation;
import com.technion.coolie.letmein.model.adapters.BaseInvitationAdapter.ContactView;
import com.technion.coolie.letmein.model.adapters.OneAdapterToRuleThemAll.Connector;

public class ContactsAdapter extends
		OneAdapterToRuleThemAll<ContactInfo, ContactInfoViewHolder> implements
		Filterable {

	public ContactsAdapter(Context context, List<ContactInfo> dataset) {
		super(
				context,
				dataset,
				new OneAdapterToRuleThemAll.Connector<ContactInfo, ContactInfoViewHolder>() {

					@Override
					public void setViewFromInvitation(
							ContactInfoViewHolder holder, ContactInfo invitation) {
						holder.Name.setText(invitation.name);
						holder.Image
								.setImageURI(Uri.parse(invitation.imageUri));

					}

					@Override
					public ContactInfoViewHolder initViewHolder(View view) {
						final ContactInfoViewHolder $ = new ContactInfoViewHolder();
						$.Name = (TextView) view
								.findViewById(R.id.lmi_contacts_auto_name);
						$.Image = (ImageView) view
								.findViewById(R.id.lmi_contacts_auto_image);
						return $;
					}

					@Override
					public String getFilteredValue(ContactInfo instance) {
						return instance.name;
					}

					@Override
					public Long getItemId(ContactInfo instance) {
						return instance.id;
					}

				});
	}
}
