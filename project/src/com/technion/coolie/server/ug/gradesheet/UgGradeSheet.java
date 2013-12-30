package com.technion.coolie.server.ug.gradesheet;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.Communicator;
import com.technion.coolie.server.ug.api.IUgGradeSheet;
import com.technion.coolie.ug.gradessheet.GradesFooterItem;
import com.technion.coolie.ug.gradessheet.GradesSectionItem;
import com.technion.coolie.ug.gradessheet.SectionedListItem;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.UGLoginObject;

/**
 * Created on 8.12.2013
 * 
 * @author DANIEL
 * 
 */
public class UgGradeSheet implements IUgGradeSheet {
	private static final String servletName = "UGGradeSheet";

	@Override
	public List<SectionedListItem> getMyGradesSheet(UGLoginObject student) {
		String $ = Communicator
				.execute(servletName, "student", toJson(student));
		List<SectionedListItem> res = new ArrayList<SectionedListItem>();
		List<Object> list = convertJsonToList($);
		GradesSectionItem g = new GradesSectionItem("title");
		Log.v("tg", new Gson().toJson(g));

		// Log.v("tg", list.get(0));

		for (Object object : list) {
			if (object.toString().contains("title")) {
				res.add(new Gson().fromJson("[" + object.toString() + "]",
						GradesSectionItem.class));
			}
			if (object.toString().contains("courseNumber")) {
				res.add(new Gson().fromJson("[" + object.toString() + "]",
						AccomplishedCourse.class));
			}
			if (object.toString().contains("semesterAvg")) {
				res.add(new Gson().fromJson("[" + object.toString() + "]",
						GradesFooterItem.class));
			}
		}
		for (SectionedListItem s : res) {
			if (s.isFooter()) {
				Log.v("footer", ((GradesFooterItem) s).getSemesterAvg());
			} else if (s.isSection()) {
				Log.v("isSection", ((GradesSectionItem) s).getTitle());
			} else {
				Log.v("AccomplishedCourse",
						((AccomplishedCourse) s).getCourseNumber());

			}
		}
		return res;
	}

	private List<Object> convertJsonToList(String json) {
		return new Gson().fromJson(json, new TypeToken<List<Object>>() {
		}.getType());
	}

	/**
	 * 
	 * @param jsonElement
	 *            an object
	 * @return json of the object
	 */
	private String toJson(Object JsonElement) {
		return new Gson().toJson(JsonElement);
	}
}