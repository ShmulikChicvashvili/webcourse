package com.technion.coolie.teletech;

import java.util.List;

import com.technion.coolie.teletech.api.ITeletech;

public class TeletechManager {

	public static List<ContactInformation> getContacts() {
		final ITeletech teletechAPI = new Teletech();

		return teletechAPI.getAllContacts();
	}

}
