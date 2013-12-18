package com.technion.coolieserver.teletech.appfiles;

import java.util.LinkedList;
import java.util.List;

public class ParseContactsStub implements IParseContacts {
  @Override
  public List<ContactInformation> parse() {
    ContactInformation contact01 = new ContactInformation("Ben", "Lev",
        Position.Student, "CS", new OfficeLocation(), "0524406323",
        "sbenlev@t2.technion.ac.il", null, null, "http://www.google.com");
    ContactInformation contact02 = new ContactInformation("Argaman", "Aloni",
        Position.Student, "CS", new OfficeLocation(), "0525856563",
        "sargaman@t2.technion.ac.il", null, null, null);
    ContactInformation contact03 = new ContactInformation("Ofer", "Guthmann",
        Position.Student, "CS", new OfficeLocation(), "0521234567",
        "soferg@t2.technion.ac.il", null, null, null);
    ContactInformation contact04 = new ContactInformation("Yossi", "Gil",
        Position.Professor, "CS", new OfficeLocation(), "0521337123",
        "syossig@cs.technion.ac.il", null, null, null);
    ContactInformation contact05 = new ContactInformation("Matan", "Hamilis",
        Position.Assistant, "CS", new OfficeLocation(), "0544444444",
        "smatanham@cs.technion.ac.il", null, null, null);
    ContactInformation contact06 = new ContactInformation("Hava", "Shamir",
        Position.Staff, "CS", new OfficeLocation(), "0523232323",
        "semek@cs.technion.ac.il", null, null, null);
    ContactInformation contact07 = new ContactInformation("Dorit", "Asa",
        Position.Staff, "CS", new OfficeLocation(), "0531313131",
        "sasadorit@cs.technion.ac.il", null, null, null);
    ContactInformation contact08 = new ContactInformation("Nader", "Bshouty",
        Position.Professor, "CS", new OfficeLocation(), "0529911991",
        "sbshouty@cs.technion.ac.il", null, null, null);
    ContactInformation contact09 = new ContactInformation("Shahar", "Dag",
        Position.Staff, "CS", new OfficeLocation(), "0555521235",
        "shahardag@cs.technion.ac.il", null, null, null);
    ContactInformation contact10 = new ContactInformation("Tal", "Gabay",
        Position.Student, "CS", new OfficeLocation(), "0522123523",
        "stalg@t2.technion.ac.il", null, null, null);
    ContactInformation contact11 = new ContactInformation("Ron", "Rubinstein",
        Position.Professor, "CS", new OfficeLocation(), "0526762327",
        "sronr@tx.technion.ac.il", null, null, null);
    ContactInformation contact12 = new ContactInformation("Jonathan", "Yaniv",
        Position.Assistant, "CS", new OfficeLocation(), "0544123551",
        "srabak@t2.technion.ac.il", null, null, null);
    ContactInformation contact13 = new ContactInformation("Mika", "Shapira",
        Position.Staff, "CS", new OfficeLocation(), "0555555555",
        "smika@t2.technion.ac.il", null, null, null);
    ContactInformation contact14 = new ContactInformation("Eran", "Shapir",
        Position.Student, "CS", new OfficeLocation(), "0544928124",
        "shafir@t2.technion.ac.il", null, null, null);
    ContactInformation contact15 = new ContactInformation("Gilad", "Shelef",
        Position.Student, "CS", new OfficeLocation(), "0512385713",
        "shelef@t2.technion.ac.il", null, null, null);

    List<ContactInformation> contactList = new LinkedList<ContactInformation>();
    contactList.add(contact01);
    contactList.add(contact02);
    contactList.add(contact03);
    contactList.add(contact04);
    contactList.add(contact05);
    contactList.add(contact06);
    contactList.add(contact07);
    contactList.add(contact08);
    contactList.add(contact09);
    contactList.add(contact10);
    contactList.add(contact11);
    contactList.add(contact12);
    contactList.add(contact13);
    contactList.add(contact14);
    contactList.add(contact15);

    return contactList;
  }

}
