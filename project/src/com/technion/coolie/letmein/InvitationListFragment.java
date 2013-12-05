package com.technion.coolie.letmein;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.technion.coolie.R;
import com.technion.coolie.letmein.model.adapters.AbstractInvitationAdapter;

public class InvitationListFragment extends Fragment {

	public interface AdapterSupplier {
		public AbstractInvitationAdapter getAdapter();
	}

	private AdapterSupplier activity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lmi_invitation_list, container,
				false);
		((ListView) view).setAdapter(activity.getAdapter());

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof AdapterSupplier) {
			this.activity = (AdapterSupplier) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet "
					+ "InvitationListFragment.AdapterSupplier");
		}
	}
}