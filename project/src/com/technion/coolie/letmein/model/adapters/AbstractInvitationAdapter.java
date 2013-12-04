package com.technion.coolie.letmein.model.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.letmein.model.Invitation;

public abstract class AbstractInvitationAdapter extends BaseAdapter {
	private final Context context;

	public AbstractInvitationAdapter(final Context context) {
		this.context = context;
	}

	protected abstract List<Invitation> getInvitationList();

	protected static class ContactView {
		public String ContactName;
		public int ContactImageId;
	}

	protected abstract ContactView getContactViewById(String contactId);

	private static class ViewHolder {
		public TextView Name;
		public TextView TimeOfArrival;
		public ImageView Image;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View $;
		ViewHolder holder;

		if (convertView != null) {
			$ = convertView;
			holder = (ViewHolder) $.getTag();
		} else {
			$ = LayoutInflater.from(context).inflate(R.layout.lmi_invitation_item, null);
			holder = initViewHolder($);
			$.setTag(holder);
		}

		setViewFromInvitation(holder, getItem(position));
		return $;
	}

	private void setViewFromInvitation(final ViewHolder holder, final Invitation invitation) {
		final ContactView cv = getContactViewById(invitation.getContactId());
		holder.Name.setText(cv.ContactName);
		holder.TimeOfArrival.setText(invitation.getDate().toString());
		holder.Image.setImageResource(cv.ContactImageId);
	}

	private ViewHolder initViewHolder(final View view) {
		final ViewHolder $ = new ViewHolder();
		$.Name = (TextView) view.findViewById(R.id.lmi_contact_name);
		$.TimeOfArrival = (TextView) view.findViewById(R.id.lmi_contact_time_of_arrival);
		$.Image = (ImageView) view.findViewById(R.id.lmi_contact_image);
		return $;
	}

	@Override
	public int getCount() {
		return getInvitationList().size();
	}

	@Override
	public Invitation getItem(final int position) {
		return getInvitationList().get(position);
	}

	@Override
	public long getItemId(final int position) {
		return getItem(position).getId();
	}
}
