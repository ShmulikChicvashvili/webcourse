package com.tecnion.coolie.ug.utils;

import android.support.v4.app.Fragment;

import com.technion.coolie.ug.calendar.AcademicCalendarFragment;
import com.technion.coolie.ug.coursesAndExams.CoursesAndExamsFragment;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchFragment;

public class FragmentsFactory {
	private static Fragment _gradesSheetLargeFragment = null;
	private static Fragment _coursesAndExamsLargeFragment = null;
	private static Fragment _getAcademicCalendarLargeFragment = null;
	private static Fragment _getCoursesSearchLargeFragment = null;

	public static Fragment getGradesSheetLargeFragment() {
		if (_gradesSheetLargeFragment == null) {
			_gradesSheetLargeFragment = new GradesSheetFragment();
		}
		return _gradesSheetLargeFragment;
	}

	public static Fragment getCoursesAndExamsLargeFragment() {
		if (_coursesAndExamsLargeFragment == null) {
			_coursesAndExamsLargeFragment = new CoursesAndExamsFragment();
		}
		return _coursesAndExamsLargeFragment;
	}

	public static Fragment getAcademicCalendarLargeFragment() {
		if (_getAcademicCalendarLargeFragment == null) {
			_getAcademicCalendarLargeFragment = new AcademicCalendarFragment();
		}
		return _getAcademicCalendarLargeFragment;
	}

	public static Fragment getSearchCorsesLargeFragment() { // TODO causes
															// problems for now.
		// if (_getCoursesSearchLargeFragment==null)
		// {
		_getCoursesSearchLargeFragment = new SearchFragment();
		// }
		return _getCoursesSearchLargeFragment;
	}
}
