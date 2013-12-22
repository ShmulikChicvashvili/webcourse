package com.technion.coolieserver.teletech;

import static com.technion.coolieserver.framework.OfyService.ofy;

import java.util.List;

import com.technion.coolieserver.teletech.appfiles.ContactInformation;

public class TeletechManager {

  public static ReturnCodeTeletech addContacts(List<ContactInformation> contacts) {
    if (contacts == null)
      return ReturnCodeTeletech.ILLEGAL_ARGUMENT;
    for (ContactInformation c : contacts)
      ofy().save().entity(c).now();
    return ReturnCodeTeletech.SUCCESS;
  }

  public static List<ContactInformation> getAllContacts() {
    List<ContactInformation> $ = ofy().load().type(ContactInformation.class)
        .list();
    return $;
  }

  // GET CONTACT //

  // private ContactInformation getContactFtomDatastroe(ContactInformation c) {
  // return ofy().load().type(ContactInformation.class)
  // .filter("techMail", c.techMail()).first().now();
  // }

  // REMOVE CONTACT //

  // public ReturnCodeTeletech removeContact(ContactInformation c) {
  // ContactInformation contactToRemove = getContactFtomDatastroe(c);
  // if (contactToRemove == null)
  // return ReturnCodeTeletech.CONTACT_DOES_NOT_EXIST;
  // ofy().delete().entity(contactToRemove).now();
  // return ReturnCodeTeletech.SUCCESS;
  // }

}
