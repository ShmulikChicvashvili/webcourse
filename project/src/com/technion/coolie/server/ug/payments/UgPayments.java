package com.technion.coolie.server.ug.payments;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.Communicator;
import com.technion.coolie.server.ug.api.IUgPayments;
import com.technion.coolie.ug.model.Payment;
import com.technion.coolie.ug.model.Student;

/**
 * Created on 8.12.2013
 * 
 * @author DANIEL
 * 
 */
public class UgPayments implements IUgPayments {
  private static final String servletName = "UGPayment";

  @Override
  public List<Payment> getStudentPayments(Student student) {
    String $ = Communicator.execute(servletName, "student", toJson(student));

    return convertJsonToList($);
  }

  /**
   * Convert json to list
   * 
   * @param json
   *          the json string
   * @return list of payments
   */
  private List<Payment> convertJsonToList(String json) {
    return new Gson().fromJson(json, new TypeToken<List<Payment>>() {
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
