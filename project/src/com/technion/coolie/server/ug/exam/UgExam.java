package com.technion.coolie.server.ug.exam;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.Communicator;
import com.technion.coolie.server.ug.api.IUgExam;
import com.technion.coolie.server.ug.framework.Exam;
import com.technion.coolie.server.ug.framework.Student;

/**
 * Created on 8.12.2013
 * 
 * @author DANIEL
 * 
 */
public class UgExam implements IUgExam {
  private static final String servletName = "UGExam";
  Communicator c = new Communicator();

  @Override
  public List<Exam> getStudentExams(Student student) {
    String $ = c.execute(servletName, "student", toJson(student));
    return new Gson().fromJson($, new TypeToken<List<Exam>>() {
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
