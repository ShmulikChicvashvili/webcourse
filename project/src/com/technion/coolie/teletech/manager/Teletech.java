package com.technion.coolie.teletech.manager;

import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.teletech.ContactInformation;
import com.technion.coolie.teletech.api.Communicator;
import com.technion.coolie.teletech.api.ITeletech;

/**
 * Created on 6.12.2013
 * 
 * @author DANIEL
 * 
 */
public class Teletech implements ITeletech {

  private static final String servletName = "Teletech";
  private static final String FUNCTION = "function";
  Communicator communicator = new Communicator();
  Gson gson = new Gson();

  @Override
  public List<ContactInformation> getAllContacts() {
	  Log.v("got here!!","tag");
    String serverResult = communicator.execute(servletName, FUNCTION,
        TeletechFunctions.GET_ALL_CONTACTS.value());
    Log.v("tag", serverResult);
    return gson.fromJson(serverResult,
        new TypeToken<List<ContactInformation>>() {/*
                                                    * The type target for Gson
                                                    */
        }.getType());
  }

}
