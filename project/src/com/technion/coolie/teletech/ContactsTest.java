package com.technion.coolie.teletech;

import java.util.LinkedList;

public class ContactsTest {

	public LinkedList<ContactInformation> contactList;

	public ContactsTest() {
		ContactInformation contact01 = new ContactInformation("Ben", "Lev", Position.Student, "CS",
				new OfficeLocation(), "0524406323", "sbenlev@t2.technion.ac.il", null, null, "http://www.google.com");

		contact01.setID(Long.valueOf(1));

		ContactInformation contact02 = new ContactInformation("Argaman", "Aloni", Position.Student, "CS",
				new OfficeLocation(), "0525856563", "sargaman@t2.technion.ac.il", null, null, null);

		contact02.setID(Long.valueOf(2));

		ContactInformation contact03 = new ContactInformation("Ofer", "Guthmann", Position.Student, "CS",
				new OfficeLocation(), "0521234567", "soferg@t2.technion.ac.il", null, null, null);

		contact03.setID(Long.valueOf(3));

		ContactInformation contact04 = new ContactInformation("Yossi", "Gil", Position.Professor, "CS",
				new OfficeLocation(), "0521337123", "syossig@cs.technion.ac.il", null, null, null);

		contact04.setID(Long.valueOf(4));

		ContactInformation contact05 = new ContactInformation("Matan", "Hamilis", Position.Assistant, "CS",
				new OfficeLocation(), "0544444444", "smatanham@cs.technion.ac.il", null, null, null);

		contact05.setID(Long.valueOf(5));

		ContactInformation contact06 = new ContactInformation("Hava", "Shamir", Position.Staff, "CS",
				new OfficeLocation(), "0523232323", "semek@cs.technion.ac.il", null, null, null);

		contact06.setID(Long.valueOf(6));

		ContactInformation contact07 = new ContactInformation("Dorit", "Asa", Position.Staff, "CS",
				new OfficeLocation(), "0531313131", "sasadorit@cs.technion.ac.il", null, null, null);

		contact07.setID(Long.valueOf(7));

		ContactInformation contact08 = new ContactInformation("Nader", "Bshouty", Position.Professor, "CS",
				new OfficeLocation(), "0529911991", "sbshouty@cs.technion.ac.il", null, null, null);

		contact08.setID(Long.valueOf(8));

		ContactInformation contact09 = new ContactInformation("Shahar", "Dag", Position.Staff, "CS",
				new OfficeLocation(), "0555521235", "shahardag@cs.technion.ac.il", null, null, null);

		contact09.setID(Long.valueOf(9));

		ContactInformation contact10 = new ContactInformation("Tal", "Gabay", Position.Student, "CS",
				new OfficeLocation(), "0522123523", "stalg@t2.technion.ac.il", null, null, null);

		contact10.setID(Long.valueOf(10));

		ContactInformation contact11 = new ContactInformation("Ron", "Rubinstein", Position.Professor, "CS",
				new OfficeLocation(), "0526762327", "sronr@tx.technion.ac.il", null, null, null);

		contact11.setID(Long.valueOf(11));

		ContactInformation contact12 = new ContactInformation("Jonathan", "Yaniv", Position.Assistant, "CS",
				new OfficeLocation(), "0544123551", "srabak@t2.technion.ac.il", null, null, null);

		contact12.setID(Long.valueOf(12));

		ContactInformation contact13 = new ContactInformation("Mika", "Shapira", Position.Staff, "CS",
				new OfficeLocation(), "0555555555", "smika@t2.technion.ac.il", null, null, null);

		contact13.setID(Long.valueOf(13));

		ContactInformation contact14 = new ContactInformation("Eran", "Shapir", Position.Student, "CS",
				new OfficeLocation(), "0544928124", "shafir@t2.technion.ac.il", null, null, null);

		contact14.setID(Long.valueOf(14));

		ContactInformation contact15 = new ContactInformation("Gilad", "Shelef", Position.Student, "CS",
				new OfficeLocation(), "0512385713", "shelef@t2.technion.ac.il", null, null, null);

		contact15.setID(Long.valueOf(15));

		contactList = new LinkedList<ContactInformation>();
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
	}
}