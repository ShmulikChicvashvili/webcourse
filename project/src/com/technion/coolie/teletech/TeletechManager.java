/**
 * 
 */
package com.technion.coolie.teletech;

import java.util.List;

/**
 * @author Argaman
 * 
 */
public class TeletechManager {

	public static List<ContactInformation> getContacts() {
		return new ContactsTest().contactList;
	}

}
