package com.technion.coolie.letmein.model.adapters;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
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
import com.technion.coolie.letmein.model.Invitation;

public abstract class BaseInvitationAdapter extends BaseAdapter implements Filterable {
	private final Context context;
	private List<Invitation> displayedDataset;

	private List<Invitation> getDisplayedDataset() {
		if (displayedDataset == null)
			displayedDataset = getFullDataset();

		return displayedDataset;
	}

	private void setDisplayedDataset(final List<Invitation> dataset) {
		displayedDataset = dataset;
	}

	public BaseInvitationAdapter(final Context context) {
		this.context = context;
	}

	protected abstract List<Invitation> getFullDataset();

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
		holder.TimeOfArrival.setText("Arriving "
				+ DateUtils.getRelativeTimeSpanString(invitation.getDate().getTime(),
						new Date().getTime(), DateUtils.MINUTE_IN_MILLIS));
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
		return getDisplayedDataset().size();
	}

	@Override
	public Invitation getItem(final int position) {
		return getDisplayedDataset().get(position);
	}

	@Override
	public long getItemId(final int position) {
		return getItem(position).getId();
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(final CharSequence constraint, final FilterResults results) {
				setDisplayedDataset((List<Invitation>) results.values);
				notifyDataSetChanged();
			}

			@Override
			protected FilterResults performFiltering(final CharSequence constraint) {
				final FilterResults $ = new FilterResults();

				if (constraint == null || constraint.length() <= 0) {
					$.values = getFullDataset();
					$.count = getFullDataset().size();

					return $;
				}

				final List<Invitation> filtered = new LinkedList<Invitation>();
				for (final Invitation i : getFullDataset())
					if (i.getContactName().startsWith(constraint.toString()))
						filtered.add(i);

				$.values = filtered;
				$.count = filtered.size();

				return $;
			}
		};
	}
}
