package com.technion.coolie.letmein;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.letmein.MainActivity.Invite;

public class InvitationListAdapter extends BaseAdapter {

	private Context context;
	private List<Invite> invites;

	public static List<Invite> mockupData() {
		return Arrays.asList(
				new Invite("Dad", "Arriving in 8 hours", String
						.valueOf(R.drawable.lmi_dad)),
				new Invite("My best friend", "Tommorow 18:00", String
						.valueOf(R.drawable.lmi_winger)), new Invite("Abed",
						"October 19", String.valueOf(R.drawable.lmi_abed)));
	}

	public InvitationListAdapter(Context context) {
		this.context = context;
	}

	public void setInvitations(List<Invite> invitations) {
		this.invites = invitations;
	}

	private class ViewHolder {
		public TextView name;
		public TextView timeOfArrival;
		public ImageView image;
	}

	@Override
	public int getCount() {
		if (isEmpty()) {
			return 1; // For handling the empty state.
		}

		return invites.size();
	}

	@Override
	public boolean isEmpty() {
		return invites == null || invites.isEmpty();
	}

	@Override
	public Invite getItem(int position) {
		return invites.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (isEmpty()) {
			return LayoutInflater.from(context).inflate(
					R.layout.lmi_empty_invitation_item, null);
		}

		View $;
		ViewHolder holder;

		if (convertView == null) {
			$ = LayoutInflater.from(context).inflate(R.layout.lmi_invitation_item, null);

			holder = new ViewHolder();
			holder.name = (TextView) $.findViewById(R.id.lmi_contact_name);
			holder.timeOfArrival = (TextView) $
					.findViewById(R.id.lmi_contact_time_of_arrival);
			holder.image = (ImageView) $.findViewById(R.id.lmi_contact_image);
			$.setTag(holder);
		} else {
			$ = convertView;
			holder = (ViewHolder) $.getTag();
		}

		Invite i = getItem(position);

		if (i == null) {
			System.out.println("it's i");
		}
		if (holder == null) {
			System.out.println("it's holder");
		}
		holder.name.setText(i.name);
		holder.timeOfArrival.setText(i.timeOfArrival);
		holder.image.setImageResource(Integer.valueOf(i.imageIndex));

		return $;
	}
}