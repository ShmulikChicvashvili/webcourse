package com.technion.coolie.server.ug.api;

import java.util.List;

import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.UGLoginObject;

/**
 * Created on 7.12.2013
 * 
 * @author DANIEL
 * 
 */
public interface IUgGradeSheet {
	/**
	 * 
	 * @param student
	 *            the student
	 * @return list of student's accomplished courses
	 */
	public List<AccomplishedCourse> getMyGradesSheet(UGLoginObject student);
}
