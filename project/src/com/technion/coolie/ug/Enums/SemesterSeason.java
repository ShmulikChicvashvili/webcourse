package com.technion.coolie.ug.Enums;

import java.io.Serializable;

import android.content.Context;

import com.technion.coolie.R;

public enum SemesterSeason implements Serializable {
	WINTER("01", R.string.ug_semester_winter) {
		@Override
		public int getIdx() {
			return 0;
		}

	},
	SPRING("02", R.string.ug_semester_spring) {
		@Override
		public int getIdx() {
			return 1;
		}
	},
	SUMMER("03", R.string.ug_semester_summer) {
		@Override
		public int getIdx() {
			return 2;
		}
	};
	private int repStringId;
	private String id;

	public abstract int getIdx();

	private SemesterSeason(String id, int repString) {
		this.repStringId = repString;
		this.id = id;
	}

	public String getName(Context context) {
		return context.getString(repStringId);
	}

	public String getId() {
		return id;
	}

	static public SemesterSeason valueByName(String name, Context context) {
		for (final SemesterSeason semester : values())
			if (semester.getName(context).equals(name))
				return semester;
		throw new NullPointerException();
	}

};
