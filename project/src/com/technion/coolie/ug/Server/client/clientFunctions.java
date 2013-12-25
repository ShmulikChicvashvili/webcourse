package com.technion.coolie.ug.Server.client;

import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.model.Semester;

/**
 * 
 * functions that are needed from the server.
 *
 */
public class clientFunctions {
 
	/**
	 * sets the semester array with the current arrays
	 * @param semesters
	 * @return
	 */
	public static SemesterSeason findCurrentSemesters(Semester [] semesters){
		
		return SemesterSeason.WINTER;
	}
	
	
	
}
