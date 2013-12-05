package com.technion.coolie.letmein;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.technion.coolie.R;

public class EmptyInvitationListFragment extends Fragment {

	public interface OnNewInvitationListener {
		public void onNewInvitation();
	}

	private OnNewInvitationListener activity;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.lmi_empty_invitation_item, container, false);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				activity.onNewInvitation();
			}
		});

		return view;
	}

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		if (activity instanceof OnNewInvitationListener)
			this.activity = (OnNewInvitationListener) activity;
		else
			throw new ClassCastException(activity.toString() + " must implemenet "
					+ OnNewInvitationListener.class.getName());
	}
}