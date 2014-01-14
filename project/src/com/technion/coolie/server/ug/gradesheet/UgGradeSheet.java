package com.technion.coolie.server.ug.gradesheet;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.Communicator;
import com.technion.coolie.server.ug.api.IUgGradeSheet;
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
	public List<AccomplishedCourse> getMyGradesSheet(UGLoginObject student) {
		String $ = Communicator
				.execute(servletName, "student", toJson(student));
		return convertJsonToList($);
	}

	private List<AccomplishedCourse> convertJsonToList(String json) {
		return new Gson().fromJson(json,
				new TypeToken<List<AccomplishedCourse>>() {
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
