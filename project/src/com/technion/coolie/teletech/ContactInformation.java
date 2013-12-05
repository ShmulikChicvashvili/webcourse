/**
 * 
 */
package com.technion.coolie.teletech;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

/**
 * @author Argaman
 * 
 */
public class ContactInformation {

	/**
	 * The ID that the server needs to set
	 */
	private Long ID;
	private final String firstName;
	private final String lastName;
	private Position contactPosition;
	private final String faculty;
	private OfficeLocation office;

	/**
	 * These fields describe three means of communication with the contact that
	 * is to be displayed.
	 */
	private String officeNumber;
	private String mobileNumber;
	private String homeNumber;
	private String techMail;
	private LinkedList<OfficeHour> officeHours;
	private String additionalInformation;
	private String website;
	private String timeStamp;
	/**
	 * This field is set to be true if the contact info was set from the parser
	 * and false if the information was set manually from the application
	 */
	private boolean isFromParse = true;

	public ContactInformation() {
		// adding the assignments to that the constructor would compile.
		firstName = null;
		lastName = null;
		faculty = null;
	}

	public ContactInformation(String firstName, String lastName,
			Position contactPosition, String faculty, OfficeLocation office,
			String phoneNumber, String techMail,
			LinkedList<OfficeHour> officeHours, String additionalInformation,
			String website) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactPosition = contactPosition; // according to the mail
		this.faculty = faculty;
		this.office = office;
		officeNumber = phoneNumber;
		this.techMail = techMail;
		this.officeHours = officeHours;
		this.additionalInformation = additionalInformation;
		this.website = website;
		timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",
				Locale.getDefault()).format(Calendar.getInstance().getTime());
	}

	/**
	 * @return the contactPosition
	 */
	public Position contactPosition() {
		return contactPosition;
	}

	/**
	 * @param contactPosition
	 *            the contactPosition to set
	 */
	public void setContactPosition(Position contactPosition) {
		this.contactPosition = contactPosition;
	}

	/**
	 * @return the office
	 */
	public OfficeLocation office() {
		return office;
	}

	/**
	 * @param office
	 *            the office to set
	 */
	public void setOffice(OfficeLocation office) {
		this.office = office;
	}

	/**
	 * @return the phoneNumber
	 */
	public String officeNumber() {
		return officeNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setofficeNumber(String phoneNumber) {
		officeNumber = phoneNumber;
	}

	/**
	 * @return the mobileNumber
	 */
	public String mobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber
	 *            the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the homeNumber
	 */
	public String homeNumber() {
		return homeNumber;
	}

	/**
	 * @param homeNumber
	 *            the homeNumber to set
	 */
	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	/**
	 * @return the techMail
	 */
	public String techMail() {
		return techMail;
	}

	/**
	 * @param techMail
	 *            the techMail to set
	 */
	public void setTechMail(String techMail) {
		this.techMail = techMail;
	}

	/**
	 * @return the officeHours
	 */
	public LinkedList<OfficeHour> officeHours() {
		return officeHours;
	}

	/**
	 * @param officeHours
	 *            the officeHours to set
	 */
	public void setOfficeHours(LinkedList<OfficeHour> officeHours) {
		this.officeHours = officeHours;
	}

	/**
	 * @return the additionalInformation
	 */
	public String additionalInformation() {
		return additionalInformation;
	}

	/**
	 * @param additionalInformation
	 *            the additionalInformation to set
	 */
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	/**
	 * @return the website
	 */
	public String website() {
		return website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return the firstName
	 */
	public String firstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String lastName() {
		return lastName;
	}

	/**
	 * @return the faculty
	 */
	public String faculty() {
		return faculty;
	}

	/**
	 * @return the timeStamp
	 */
	public String timeStamp() {
		return timeStamp;
	}

	/**
	 * @return the iD
	 */
	public Long ID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(Long iD) {
		ID = iD;
	}

	/**
	 * @return the isFromParse
	 */
	public boolean isFromParse() {
		return isFromParse;
	}

	/**
	 * @param isFromParse
	 *            the isFromParse to set
	 */
	public void setFromParse(boolean isFromParse) {
		this.isFromParse = isFromParse;
	}

}
