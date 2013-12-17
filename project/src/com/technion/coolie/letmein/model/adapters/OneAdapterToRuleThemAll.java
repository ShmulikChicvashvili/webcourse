package com.technion.coolie.letmein.model.adapters;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;



import com.technion.coolie.R;


public abstract class OneAdapterToRuleThemAll<ContainedClass, ViewHolderClass> extends
		BaseAdapter implements Filterable {

	public interface Connector<ContainedClass, ViewHolderClass> {
		public void setViewFromInvitation(final ViewHolderClass holder,
				final ContainedClass invitation);

		public ViewHolderClass initViewHolder(final View view);

		public String getFilteredValue(ContainedClass instance);
		public Long getItemId(ContainedClass instance);
	}

	private Connector<ContainedClass, ViewHolderClass> instance;
	protected final Context context;
	private List<ContainedClass> displayedDataset;
	private List<ContainedClass> dataset;

	private List<ContainedClass> getDisplayedDataset() {
		if (displayedDataset == null)
			displayedDataset = getFullDataset();

		return displayedDataset;
	}

	protected List<ContainedClass> getFullDataset() {
		return dataset;
	}

	private void setDisplayedDataset(final List<ContainedClass> dataset) {
		displayedDataset = dataset;
	}

	public OneAdapterToRuleThemAll(Context context,
			List<ContainedClass> dataset,
			Connector<ContainedClass, ViewHolderClass> instance) {
		this.dataset = dataset;
		this.context = context;
		this.instance = instance;
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View $;
		ViewHolderClass holder;

		if (convertView != null) {
			$ = convertView;
			holder = (ViewHolderClass) $.getTag();
		} else {
			$ = LayoutInflater.from(context).inflate(
					R.layout.lmi_contacts_autocomplete_layout, null);
			holder = this.instance.initViewHolder($);
			$.setTag(holder);
		}

		this.instance.setViewFromInvitation(holder, getItem(position));
		return $;
	}

	@Override
	public int getCount() {
		return getDisplayedDataset().size();
	}

	@Override
	public ContainedClass getItem(final int position) {
		return this.displayedDataset.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return this.instance.getItemId(getItem(position));
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(final CharSequence constraint,
					final FilterResults results) {
				setDisplayedDataset((List<ContainedClass>) results.values);
				notifyDataSetChanged();
			}

			@Override
			protected FilterResults performFiltering(
					final CharSequence constraint) {
				final FilterResults $ = new FilterResults();

				if (constraint == null || constraint.length() <= 0) {
					$.values = getFullDataset();
					$.count = getFullDataset().size();

					return $;
				}

				final String lowerCaseConstraint = constraint.toString()
						.toLowerCase(Locale.getDefault());
				final List<ContainedClass> filtered = new LinkedList<ContainedClass>();
				for (final ContainedClass i : getFullDataset())
					if (OneAdapterToRuleThemAll.this.instance
							.getFilteredValue(i)
							.toLowerCase(Locale.getDefault())
							.startsWith(lowerCaseConstraint))
						filtered.add(i);

				$.values = filtered;
				$.count = filtered.size();

				return $;
			}
		};
	}
}
