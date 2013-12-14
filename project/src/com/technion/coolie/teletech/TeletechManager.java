/**
 * 
 */
package com.technion.coolie.teletech;

import java.util.List;

import com.technion.coolie.teletech.serverApi.ITeletechAPI;
import com.technion.coolie.teletech.serverApi.TeletechAPI;

/**
 * @author Argaman
 * 
 */
public class TeletechManager {

	public static List<ContactInformation> getContacts() {
		final ITeletechAPI teletechAPI = new TeletechAPI();

		return teletechAPI.getAllContacts();
	}

}
