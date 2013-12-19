package com.technion.coolie.teletech;

public class OfficeLocation {
	private String faculty;
	private String officeRoom;

	public OfficeLocation() {
	}

	public OfficeLocation(String faculty, String officeRoom) {
		this.faculty = faculty;
		this.officeRoom = officeRoom;
	}

	public String faculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String officeRoom() {
		return officeRoom;
	}

	public void setOfficeRoom(String officeRoom) {
		this.officeRoom = officeRoom;
	}

	@Override
	public String toString() {
		String officeLocation;

		if (faculty == null && officeRoom == null || "NA".equals(faculty)
				&& "NA".equals(officeRoom))
			officeLocation = "No room specified";
		else if (faculty == null)
			officeLocation = officeRoom;
		else if (officeRoom == null)
			officeLocation = faculty;
		else
			officeLocation = faculty + " " + officeRoom;

		return officeLocation;
	}

}
