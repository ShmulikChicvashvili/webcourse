package com.technion.coolie.teletech;

import java.util.LinkedList;

public class FavoriteTest {

	public LinkedList<ContactInformation> favoriteList;

	public FavoriteTest() {
		super();
		ContactInformation contact01 = new ContactInformation("Ben", "Lev",
				Position.Student, "CS", new OfficeLocation(), "0524406323",
				"sbenlev@t2.technion.ac.il", null, null,
				"http://www.google.com");
		ContactInformation contact02 = new ContactInformation("Argaman",
				"Aloni", Position.Student, "CS", new OfficeLocation(),
				"0525856563", "sargaman@t2.technion.ac.il", null, null, null);
		ContactInformation contact03 = new ContactInformation("Ofer",
				"Guthmann", Position.Student, "CS", new OfficeLocation(),
				"0521234567", "soferg@t2.technion.ac.il", null, null, null);
		ContactInformation contact10 = new ContactInformation("Tal", "Gabay",
				Position.Student, "CS", new OfficeLocation(), "0522123523",
				"stalg@t2.technion.ac.il", null, null, null);
		ContactInformation contact14 = new ContactInformation("Eran", "Shapir",
				Position.Student, "CS", new OfficeLocation(), "0544928124",
				"shafir@t2.technion.ac.il", null, null, null);
		ContactInformation contact15 = new ContactInformation("Gilad",
				"Shelef", Position.Student, "CS", new OfficeLocation(),
				"0512385713", "shelef@t2.technion.ac.il", null, null, null);

		favoriteList = new LinkedList<ContactInformation>();
		favoriteList.add(contact01);
		favoriteList.add(contact02);
		favoriteList.add(contact03);
		favoriteList.add(contact10);
		favoriteList.add(contact14);
		favoriteList.add(contact15);
	}

}
