package com.technion.coolie.ug.Enums;

import java.io.Serializable;

import android.content.Context;

import com.technion.coolie.R;

public enum SemesterSeason implements Serializable {
	WINTER(R.string.ug_semester_winter) {
		@Override
		public int getIdx() {
			return 0;
		}

	},
	SPRING(R.string.ug_semester_spring) {
		@Override
		public int getIdx() {
			return 1;
		}
	},
	SUMMER(R.string.ug_semester_summer) {
		@Override
		public int getIdx() {
			return 2;
		}
	};
	private int repStringId;

	public abstract int getIdx();

	private SemesterSeason(int repString) {
		this.repStringId = repString;
	}

	public String getName(Context context) {
		return context.getString(repStringId);
	}

	static public SemesterSeason valueByName(String name, Context context) {
		for (final SemesterSeason semester : values())
			if (semester.getName(context).equals(name))
				return semester;
		throw new NullPointerException();
	}

};
