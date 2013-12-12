package com.technion.coolie.studybuddy.Model;

import java.util.Date;
import java.util.Random;

public enum Utils {
	INSTANCE;

	private final static Random r = new Random((new Date()).getTime());

	public static int randomInt(int num) {
		return r.nextInt(num);
	}
}
