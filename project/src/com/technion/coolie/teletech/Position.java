/**
 * 
 */
package com.technion.coolie.teletech;

/**
 * @author Argaman
 * 
 */
public enum Position {

	Professor("PROFESSOR"), Assistant("ASSISTANT"), Student("STUDENT"), Staff(
			"STAFF");

	// TODO: add all the positions that need to be added.
	private final String value;

	private Position(String s) {
		value = s;
	}

	public String value() {
		return value;
	}
}
