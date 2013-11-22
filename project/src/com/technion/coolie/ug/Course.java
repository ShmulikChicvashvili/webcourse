package com.technion.coolie.ug;

import java.util.Date;
import java.util.List;

import com.technion.coolie.ug.Enums.Faculty;

public class Course {
	private String courseNumber;
	private String name;
	private String points;
	private String description;

	private Semester semester;
	private Faculty faculty; // by the first two numbers of the course id.
	private Date moedA;
	private Date moedB;

	private List<String> prerequisites;
	private List<RegistrationGroup> registrationGroups;

}
