package com.technion.coolie.ug.Server;

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
	Long id;
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
	
	public ServerCourse()
	{
		
	}

	public ServerCourse(final String courseNumber, final String name,
			final float points, final String description,
			final Semester semester, final Faculty faculty, final long moedA,
			final long moedB, final List<GroupOfCourses> prerequisites,
			final List<GroupOfCourses> attachedCourses,
			final List<RegistrationGroup> registrationGroups) {
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

	public void setCourseNumber(final String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public float getPoints() {
		return points;
	}

	public void setPoints(final float points) {
		this.points = points;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(final Semester semester) {
		this.semester = semester;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(final Faculty faculty) {
		this.faculty = faculty;
	}

	public long getMoedA() {
		return moedA;
	}

	public void setMoedA(final long moedA) {
		this.moedA = moedA;
	}

	public long getMoedB() {
		return moedB;
	}

	public void setMoedB(final long moedB) {
		this.moedB = moedB;
	}

	public List<GroupOfCourses> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(final List<GroupOfCourses> prerequisites) {
		this.prerequisites = prerequisites;
	}

	public List<GroupOfCourses> getAttachedCourses() {
		return attachedCourses;
	}

	public void setAttachedCourses(final List<GroupOfCourses> attachedCourses) {
		this.attachedCourses = attachedCourses;
	}

	public List<RegistrationGroup> getRegistrationGroups() {
		return registrationGroups;
	}

	public void setRegistrationGroups(
			final List<RegistrationGroup> registrationGroups) {
		this.registrationGroups = registrationGroups;
	}

	public CourseKey getCourseKey() {
		return new CourseKey(courseNumber, semester);
	}

	public boolean hasFreePlaces() {
		int sum = 0;
		for (final RegistrationGroup group : registrationGroups)
			sum += Math.abs(group.getFreePlaces());
		return sum > 0;
	}

}
