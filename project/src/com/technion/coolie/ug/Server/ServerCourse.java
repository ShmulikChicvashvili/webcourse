package com.technion.coolie.ug.Server;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.technion.coolie.ug.Enums.Faculty;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.GroupOfCourses;
import com.technion.coolie.ug.model.RegistrationGroup;
import com.technion.coolie.ug.model.Semester;

//Notes : faculty can be decided by the first two numbers of the
// course id.

public class ServerCourse {

	// The key\id fields: courseNumber + semester

	/**
	 * 
	 */
	int id;
	private Semester semester; 
	private String courseNumber;

	private String name;

	private float points;
	private String description;

	private Faculty faculty;

	private long moedA;
	private long moedB;

	private List<GroupOfCourses> prerequisites; // ���
	private List<GroupOfCourses> attachedCourses; // ����
	private List<RegistrationGroup> registrationGroups;

	public ServerCourse(String courseNumber, String name, float points,
			String description, Semester semester, Faculty faculty, long moedA,
			long moedB, List<GroupOfCourses> prerequisites,List<GroupOfCourses> attachedCourses,
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
		this.attachedCourses = attachedCourses;
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

	public float getPoints() {
		return points;
	}

	public void setPoints(float points) {
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

	public long getMoedA() {
		return moedA;
	}

	public void setMoedA(long moedA) {
		this.moedA = moedA;
	}

	public long getMoedB() {
		return moedB;
	}

	public void setMoedB(long moedB) {
		this.moedB = moedB;
	}

	public List<GroupOfCourses> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(List<GroupOfCourses> prerequisites) {
		this.prerequisites = prerequisites;
	}
	
	public List<GroupOfCourses> getAttachedCourses() {
		return attachedCourses;
	}

	public void setAttachedCourses(List<GroupOfCourses> attachedCourses) {
		this.attachedCourses = attachedCourses;
	}

	public List<RegistrationGroup> getRegistrationGroups() {
		return registrationGroups;
	}

	public void setRegistrationGroups(List<RegistrationGroup> registrationGroups) {
		this.registrationGroups = registrationGroups;
	}

	public CourseKey getCourseKey() {
		return new CourseKey(courseNumber, semester);
	}

	public boolean hasFreePlaces() {
		int sum = 0;
		for (RegistrationGroup group : registrationGroups) {
			sum += Math.abs(group.getFreePlaces());
		}
		return sum > 0;
	}

}
