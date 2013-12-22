package com.technion.coolie.letmein.model.adapters;

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.letmein.model.ContactInfo;
import com.technion.coolie.letmein.model.ContactInfoViewHolder;

public class ContactsAdapter extends OneAdapterToRuleThemAll<ContactInfo, ContactInfoViewHolder> {

	public ContactsAdapter(final Context context, final List<ContactInfo> dataSet) {
		super(context, dataSet,
				new OneAdapterToRuleThemAll.Connector<ContactInfo, ContactInfoViewHolder>() {

					@Override
					public void setViewFromInvitation(final ContactInfoViewHolder holder,
							final ContactInfo invitation) {
						holder.Name.setText(invitation.name);
						holder.Image.setImageURI(Uri.parse(invitation.imageUri));
						if (invitation.email != null)
							holder.Email.setText(invitation.email);
					}

					@Override
					public ContactInfoViewHolder initViewHolder(final View view) {
						final ContactInfoViewHolder $ = new ContactInfoViewHolder();
						$.Name = (TextView) view.findViewById(R.id.lmi_contacts_auto_name);
						$.Image = (ImageView) view.findViewById(R.id.lmi_contacts_auto_image);
						$.Email = (TextView) view.findViewById(R.id.lmi_contacts_auto_email);
						return $;
					}

					@Override
					public String getFilteredValue(final ContactInfo instance) {
						return instance.name;
					}

					@Override
					public Long getItemId(final ContactInfo instance) {
						return instance.id;
					}

				});
	}

}
