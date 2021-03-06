package com.technion.coolie.letmein;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.technion.coolie.R;
import com.technion.coolie.letmein.model.adapters.BaseInvitationAdapter;
import com.technion.coolie.letmein.model.adapters.InvitationAdapter;

public class InvitationListFragment extends Fragment {

	public interface AdapterSupplier {
		public BaseInvitationAdapter getAdapter();

		public void changeInvitationView(int position);
	}

	private AdapterSupplier activity;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.lmi_invitation_list, container, false);

		ListView invitationList = (ListView) view;
		invitationList.setAdapter(activity.getAdapter());

		if (activity.getAdapter() instanceof InvitationAdapter)
			invitationList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					activity.changeInvitationView(position);
				}
			});

		return view;
	}

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		if (activity instanceof AdapterSupplier)
			this.activity = (AdapterSupplier) activity;
		else
			throw new ClassCastException(activity.toString() + " must implemenet "
					+ AdapterSupplier.class.getName());
	}
}