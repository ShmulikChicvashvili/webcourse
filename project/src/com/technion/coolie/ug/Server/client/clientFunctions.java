package com.technion.coolie.ug.Server.client;

import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.model.Semester;

/**
 * 
 * wrapper functions of the (UGFactory)server functions. all a-sync tasks should
 * be defined here.
 * 
 */
public class clientFunctions {

	/**
	 * sets the semester array with the current arrays
	 * 
	 * @param semesters
	 * @return
	 */
	public static SemesterSeason findCurrentSemesters(Semester[] semesters) {

		return SemesterSeason.WINTER;
	}

	// getAllInfo

	// getcourses

	// getTrackingCourses

	// getAcademic

}
