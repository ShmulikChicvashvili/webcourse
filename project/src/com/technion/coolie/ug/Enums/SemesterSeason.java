package com.technion.coolie.ug.Enums;

import java.io.Serializable;

public enum SemesterSeason implements Serializable {
	WINTER {
		public int getIdx() {
			return 0;
		}

	},
	SPRING {
		@Override
		public int getIdx() {
			// TODO Auto-generated method stub
			return 1;
		}
	},
	SUMMER {
		@Override
		public int getIdx() {
			// TODO Auto-generated method stub
			return 2;
		}
	};
	public abstract int getIdx();

};
