package com.technion.coolie.letmein;

import com.technion.coolie.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeFragment extends Fragment {

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.lmi_welcome_fragment, container, false);
	}
}
