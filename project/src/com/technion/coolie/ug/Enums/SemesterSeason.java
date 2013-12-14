package com.technion.coolie.ug.Enums;

import java.io.Serializable;

public enum SemesterSeason implements Serializable {
	WINTER {
		public int getIdx() {
			return 0;
		}

	},
	SPRING {
		public int getIdx() {
			return 1;
		}
	},
	SUMMER {
		public int getIdx() {
			return 2;
		}
	};
	public abstract int getIdx();

};
