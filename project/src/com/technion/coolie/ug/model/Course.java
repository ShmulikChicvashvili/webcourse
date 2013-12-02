package com.technion.coolie.ug.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.technion.coolie.ug.Enums.Faculty;

//Notes : faculty can be decided by the first two numbers of the
// course id.

public class Course implements Serializable {

	// The key\id fields: courseNumber + semester

	/**
	 * 
	 */
	private static final long serialVersionUID = 1920752343710817619L;

	private Semester semester; // can be a string in the db
	private String courseNumber;

	private String name;

	private String points;
	private String description;

	private Faculty faculty;// can be a string in the db.

	private Date moedA;
	private Date moedB;

	private List<String> prerequisites;// kda-mim
	private List<RegistrationGroup> registrationGroups;

	public Course(String courseNumber, String name, String points,
			String description, Semester semester, Faculty faculty, Date moedA,
			Date moedB, List<String> prerequisites,
			List<RegistrationGroup> registrationGroups) {
		super();
		this.courseNumber = courseNumber;
		this.name = name;
		this.points = points;
		this.description = description;
		this.semester = semester;
		this.faculty = faculty;
		this.moedA = moedA;
		this.moedB = moedB;
		this.prerequisites = prerequisites;
		this.registrationGroups = registrationGroups;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Date getMoedA() {
		return moedA;
	}

	public void setMoedA(Date moedA) {
		this.moedA = moedA;
	}

	public Date getMoedB() {
		return moedB;
	}

	public void setMoedB(Date moedB) {
		this.moedB = moedB;
	}

	public List<String> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(List<String> prerequisites) {
		this.prerequisites = prerequisites;
	}

	public List<RegistrationGroup> getRegistrationGroups() {
		return registrationGroups;
	}

	public void setRegistrationGroups(List<RegistrationGroup> registrationGroups) {
		this.registrationGroups = registrationGroups;
	}

}
