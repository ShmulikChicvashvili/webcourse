package com.technion.coolie.ug.web;

import java.util.List;

/**
 * list of all needed URLs for the ug project. For each URL, there are different
 * number of parameters. To use the URL call
 * buildURL(chosenURL,listOfParameters).
 * 
 * @author happyfeet
 * 
 */
public class WebURL {

	/**
	 * 1-
	 */
	final static String RISHUM_SEARCH = "http://ug.technion.ac.il/rishum/search.php?CNM=%E0&PNT=&CNO=&FAC=&LFN=&LLN=&SEM=201301&RECALL=Y&D6=on&D5=on&D4=on&D3=on&D2=on&D1=on&TTM=&FTM=&SIL=&OPTCAT=on&OPTSEM=on&Search.x=10&Search.y=10";

	/**
	 * 1-course number 2-semester year and part of the year (like 201301)
	 */
	final static String RISHUM_COURSE_PAGE = "2|http://ug.technion.ac.il/rishum/mikdet.php?MK=~~&SEM=~~";

	public static void buildURL(String URL, List<String> parameters) {
		URL.substring(URL.indexOf("http"));

	}

}
