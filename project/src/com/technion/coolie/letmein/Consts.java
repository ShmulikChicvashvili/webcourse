package com.technion.coolie.letmein;

public interface Consts {
	final String LMI_PREFIX = "lmi_"; // So we won't interrupt other modules.
	final String PREF_FILE = LMI_PREFIX + "preferences";
	final String USERNAME = LMI_PREFIX + "username";
	final String PASSWORD = LMI_PREFIX + "password";
	final String IS_LOGGED_IN = LMI_PREFIX + "is_logged_in";
	final String CALENDAR_INFO = LMI_PREFIX + "calendar_info";
}
