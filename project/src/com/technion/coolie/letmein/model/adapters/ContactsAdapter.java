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
import com.technion.coolie.letmein.model.Invitation;
import com.technion.coolie.letmein.model.adapters.BaseInvitationAdapter.ContactView;

public class ContactsAdapter extends BaseAdapter implements Filterable {
	private final Context context;
	private List<ContactInfo> displayedDataset;
	private List<ContactInfo> dataset;

	private List<ContactInfo> getDisplayedDataset() {
		if (displayedDataset == null)
			displayedDataset = getFullDataset();

		return displayedDataset;
	}
	
	protected List<ContactInfo> getFullDataset() {
		return dataset;
	}

	private void setDisplayedDataset(final List<ContactInfo> dataset) {
		displayedDataset = dataset;
	}


	public ContactsAdapter(Context context,List<ContactInfo> dataset)
	{
		this.dataset = dataset;
		this.context = context;
	}


	private static class ViewHolder {
		public TextView Name;
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
			$ = LayoutInflater.from(context).inflate(R.layout.lmi_contacts_autocomplete_layout, null);
			holder = initViewHolder($);
			$.setTag(holder);
		}

		setViewFromInvitation(holder, getItem(position));
		return $;
	}

	private void setViewFromInvitation(final ViewHolder holder, final ContactInfo invitation) {
		holder.Name.setText(invitation.name);
		holder.Image.setImageURI(Uri.parse(invitation.imageUri));
	}

	private ViewHolder initViewHolder(final View view) {
		final ViewHolder $ = new ViewHolder();
		$.Name = (TextView) view.findViewById(R.id.lmi_contacts_auto_name);
		$.Image = (ImageView) view.findViewById(R.id.lmi_contacts_auto_image);
		return $;
	}

	@Override
	public int getCount() {
		return getDisplayedDataset().size();
	}

	@Override
	public ContactInfo getItem(final int position) {
		return this.dataset.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return getItem(position).id;
	}
	

	@Override
	public Filter getFilter() {
		return new Filter() {
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(final CharSequence constraint, final FilterResults results) {
				setDisplayedDataset((List<ContactInfo>) results.values);
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

				final String lowerCaseConstraint = constraint.toString().toLowerCase(
						Locale.getDefault());
				final List<ContactInfo> filtered = new LinkedList<ContactInfo>();
				for (final ContactInfo i : getFullDataset())
					if (i.name.toLowerCase(Locale.getDefault())
							.startsWith(lowerCaseConstraint))
						filtered.add(i);

				$.values = filtered;
				$.count = filtered.size();

				return $;
			}
		};
	}
}
