package com.technion.coolie.skeleton;

import android.content.Context;

public class AlphabeticalModulesAdapter extends ModulesAdapter {

	public AlphabeticalModulesAdapter(Context c) {
		super(c);
	}

	@Override
	int compareModules(CoolieModule m1, CoolieModule m2) {
		return m1.getName(mContext).compareTo(m2.getName(mContext));
	}

}
