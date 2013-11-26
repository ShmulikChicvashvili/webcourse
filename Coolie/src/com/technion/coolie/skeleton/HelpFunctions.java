package com.technion.coolie.skeleton;

import java.lang.reflect.Field;

public class HelpFunctions {

	public static int findResourceByName(String resourceName, Class<?> c) {
		String rn = getResourceName(resourceName);
		try {	    	
	        Field idField = c.getDeclaredField(rn);
	        return idField.getInt(idField);
	    } catch (Exception e) {
	        throw new RuntimeException("No resource ID found for: "
	                + resourceName + "(" + rn + ")" + " / " + c, e);
	    }
	}
	
	private static String getResourceName(String resourceName)
	{
		String rn = new String(resourceName.substring(0, 1).toLowerCase() + resourceName.substring(1));
		rn = rn.replaceAll(" ", "");
		for (int i = 'A'; i<='Z'; i++)
    	{
    		char ch = (char) i;
    		String s = new String(ch+"");
    		rn = rn.replaceAll(s, "_"+s.toLowerCase());
    	}	
		return rn;
	}
}