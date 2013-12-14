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
		
		contact01.setID(Long.valueOf(1));
		
		ContactInformation contact02 = new ContactInformation("Argaman",
				"Aloni", Position.Student, "CS", new OfficeLocation(),
				"0525856563", "sargaman@t2.technion.ac.il", null, null, null);
		
		contact02.setID(Long.valueOf(2));
		
		ContactInformation contact03 = new ContactInformation("Ofer",
				"Guthmann", Position.Student, "CS", new OfficeLocation(),
				"0521234567", "soferg@t2.technion.ac.il", null, null, null);
		
		contact03.setID(Long.valueOf(3));
		
		ContactInformation contact10 = new ContactInformation("Tal", "Gabay",
				Position.Student, "CS", new OfficeLocation(), "0522123523",
				"stalg@t2.technion.ac.il", null, null, null);
		
		contact10.setID(Long.valueOf(10));
		
		ContactInformation contact14 = new ContactInformation("Eran", "Shapir",
				Position.Student, "CS", new OfficeLocation(), "0544928124",
				"shafir@t2.technion.ac.il", null, null, null);
		
		contact14.setID(Long.valueOf(14));
		
		ContactInformation contact15 = new ContactInformation("Gilad",
				"Shelef", Position.Student, "CS", new OfficeLocation(),
				"0512385713", "shelef@t2.technion.ac.il", null, null, null);
		
		
		contact15.setID(Long.valueOf(15));

		favoriteList = new LinkedList<ContactInformation>();
		favoriteList.add(contact01);
		favoriteList.add(contact02);
		favoriteList.add(contact03);
		favoriteList.add(contact10);
		favoriteList.add(contact14);
		favoriteList.add(contact15);
	}

}
