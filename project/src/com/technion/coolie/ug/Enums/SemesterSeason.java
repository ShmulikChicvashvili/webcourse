package com.technion.coolie.ug.Enums;

import java.io.Serializable;

public enum SemesterSeason implements Serializable {
	WINTER {
		@Override
		public int getIdx() {
			return 0;
		}

	},
	SPRING {
		@Override
		public int getIdx() {
			return 1;
		}
	},
	SUMMER {
		@Override
		public int getIdx() {
			return 2;
		}
	};
	public abstract int getIdx();

};
