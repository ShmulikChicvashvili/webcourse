package com.technion.coolie.ug.web;

import java.util.List;

import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Exam;
import com.technion.coolie.ug.model.Payment;
import com.technion.coolie.ug.model.Semester;

public interface UgInterface {

	// get academic average
	public double getAverage();

	// get num of academic points completed so far
	public double getPoints();

	// get name of student
	public String getName();

	// get date (and hour) of opening registration to UG
	public String getRegistrationDate();

	// retrieves courses taken by user in current semester
	public List<CourseKey> getCurrentCourses();

	// retrieves list of courses the student completed so far
	public List<AccomplishedCourse> getAccomplishedCourses();

	// Returns a list of all my exams (îáçðéí) in current semester
	public List<Exam> getAllMyExams();

	// Returns a list of all my tests (áçðéí) in current semester
	public List<Exam> getAllMyTests();

	// Returns all payments of the student
	public List<Payment> getAllPayments();

	// Retrieves course’s details (input can be either course name or course
	// number)
	public List<Course> getAllCourses(Semester semester);

	// Returns payment details of a given semester
	public List<Payment> getPaymentDetails(String semester);

	// Returns the three open courses for viewing in the UG
	public List<Semester> getAvailableSemesters();

}
