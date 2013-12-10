package com.technion.coolie.skeleton;

import android.content.Context;

public class RecentlyUsedAdapter extends ModulesAdapter {

	public RecentlyUsedAdapter(Context c) {
		super(c);
	}

	@Override
	int compareModules(CoolieModule m1, CoolieModule m2) {
		return m1.getLastUsed().compareTo(m2.getLastUsed());
	}
}
