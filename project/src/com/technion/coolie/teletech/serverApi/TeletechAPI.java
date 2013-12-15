package com.technion.coolie.teletech.serverApi;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.teletech.ContactInformation;
import com.technion.coolie.teletech.api.Communicator;

public class TeletechAPI implements ITeletechAPI {

	private static final String servletName = "Teletech";
	private static final String FUNCTION = "function";
	Communicator communicator = new Communicator();
	Gson gson = new Gson();

	@Override
	public List<ContactInformation> getAllContacts() {
		final String serverResult = communicator.execute(servletName, FUNCTION,
				TeletechFunctions.GET_ALL_CONTACTS.value());
		return gson.fromJson(serverResult,
				new TypeToken<List<ContactInformation>>() {/*
				 * The type target for Gson
				 */
		}.getType());
	}

}
