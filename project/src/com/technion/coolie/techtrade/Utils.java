package com.technion.coolie.techtrade;

public class Utils {
	
	/******************
	 * 
	 * @param d - Double number to be formatted
	 * @return String representing the number as is if double and without
	 * decimal point if it's an integer.
	 ******************/
	public static String parseDoubleToString(Double d){
		return (d%1 == 0)?String.valueOf(d.intValue()):d.toString();
	}
}
