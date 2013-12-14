package com.technion.coolie.teletech.serverApi;

public enum TeletechFunctions {
	GET_ALL_CONTACTS("GET_ALL_CONTACTS");

	private final String value;

	private TeletechFunctions(final String s) {
		value = s;
	}

	public String value() {
		return value;
	}
}
