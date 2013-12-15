package com.technion.coolie.ug.Enums;

import java.io.Serializable;

public enum DayOfWeek implements Serializable {
	SUNDAY {
		@Override
		public String toSingleLetter() {
			return "A";
		}
	},
	MONDAY {
		@Override
		public String toSingleLetter() {
			return "B";
		}
	},
	TUESDAY {
		@Override
		public String toSingleLetter() {
			return "C";
		}
	},
	WEDNESDAY {
		@Override
		public String toSingleLetter() {
			return "D";
		}
	},
	THURSDAY {
		@Override
		public String toSingleLetter() {
			return "E";
		}
	},
	FRIDAY {
		@Override
		public String toSingleLetter() {
			return "F";
		}
	},
	SATURDAY {
		@Override
		public String toSingleLetter() {
			return "S";
		}
	};

	public abstract String toSingleLetter();
};
