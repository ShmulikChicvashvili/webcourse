package com.technion.coolie.skeleton;

import android.content.Context;

public class RecentlyUsedAdapter extends ModulesAdapter {

	public RecentlyUsedAdapter(Context c) {
		super(c);
	}

	@Override
	int compareModules(CoolieModule m1, CoolieModule m2) {
		if (m1.getLastUsed() == null && m2.getLastUsed() != null) {
			return -1; // meaning m2 got used more recently..
		} else {
			if (m1.getLastUsed() != null && m2.getLastUsed() == null) {
				return 1; // meaning m1 got used more recently..
			} else {
				if (m1.getLastUsed() == null && m2.getLastUsed() == null) {
					return 1; // randomly determined the m1 is more recent..
				}
			}
		}

		return m1.getLastUsed().compareTo(m2.getLastUsed());
	}
}
