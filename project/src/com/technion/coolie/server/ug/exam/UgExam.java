package com.technion.coolie.server.ug.exam;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.Communicator;
import com.technion.coolie.server.ug.api.IUgExam;
import com.technion.coolie.ug.UGLoginObject;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.Semester;

/**
 * Created on 8.12.2013
 * 
 * @author DANIEL
 * 
 */
public class UgExam implements IUgExam {
  private static final String servletName = "UGExam";

  @Override
  public List<CourseItem> getStudentExams(UGLoginObject student,
      Semester semester) throws IOException {
    String $ = Communicator.execute(servletName, "student", toJson(student),
        "semester", toJson(semester));
    return convertJsonToList($);
  }

  /**
   * Convert json to list
   * 
   * @param json
   *          the json string
   * @return list of courses
   */
  private List<CourseItem> convertJsonToList(String json) {
    return new Gson().fromJson(json, new TypeToken<List<CourseItem>>() {
    }.getType());
  }

  /**
   * 
   * @param jsonElement
   *          an object
   * @return json of the object
   */
  private String toJson(Object JsonElement) {
    return new Gson().toJson(JsonElement);
  }
}
