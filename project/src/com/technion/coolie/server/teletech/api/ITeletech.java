package com.technion.coolie.server.teletech.api;

import java.io.IOException;
import java.util.List;

import com.technion.coolie.teletech.ContactInformation;

/**
 * Created on 6.12.2013
 * 
 * @author DANIEL
 * 
 */
public interface ITeletech {
  /**
   * 
   * @return list of all the contacts in the DB
   * @throws IOException
   */
  public List<ContactInformation> getAllContacts() throws IOException;
}
