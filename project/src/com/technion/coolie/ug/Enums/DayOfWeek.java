package com.technion.coolie.ug.Enums;

import java.io.Serializable;

public enum DayOfWeek implements Serializable {
	SUNDAY {
		@Override
		public String toSingleLetter() {
			return "א";
		}
	},
	MONDAY {
		@Override
		public String toSingleLetter() {
			return "ב";
		}
	},
	TUESDAY {
		@Override
		public String toSingleLetter() {
			return "ג";
		}
	},
	WEDNESDAY {
		@Override
		public String toSingleLetter() {
			return "ד";
		}
	},
	THURSDAY {
		@Override
		public String toSingleLetter() {
			return "ה";
		}
	},
	FRIDAY {
		@Override
		public String toSingleLetter() {
			return "ו";
		}
	},
	SATURDAY {
		@Override
		public String toSingleLetter() {
			return "ש";
		}
	};

	public abstract String toSingleLetter();
};
