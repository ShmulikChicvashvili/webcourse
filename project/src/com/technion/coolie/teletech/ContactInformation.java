package com.technion.coolie.teletech;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

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
	private boolean isFavourite;

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

	public ContactInformation(String firstName, String lastName, Position contactPosition, String faculty,
			OfficeLocation office, String phoneNumber, String techMail, LinkedList<OfficeHour> officeHours,
			String additionalInformation, String website) {
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
		timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance()
				.getTime());
		setFavourite(false);
	}

	public Position contactPosition() {
		return contactPosition;
	}

	public void setContactPosition(Position contactPosition) {
		this.contactPosition = contactPosition;
	}

	public OfficeLocation office() {
		return office;
	}

	public void setOffice(OfficeLocation office) {
		this.office = office;
	}

	public String officeNumber() {
		return officeNumber;
	}

	public void setofficeNumber(String phoneNumber) {
		officeNumber = phoneNumber;
	}

	public String mobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String homeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String techMail() {
		return techMail;
	}

	public void setTechMail(String techMail) {
		this.techMail = techMail;
	}

	public LinkedList<OfficeHour> officeHours() {
		return officeHours;
	}

	public void setOfficeHours(LinkedList<OfficeHour> officeHours) {
		this.officeHours = officeHours;
	}

	public String additionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String website() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String firstName() {
		return firstName;
	}

	public String lastName() {
		return lastName;
	}

	public String faculty() {
		return faculty;
	}

	public String timeStamp() {
		return timeStamp;
	}

	public Long ID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public boolean isFromParse() {
		return isFromParse;
	}

	public void setFromParse(boolean isFromParse) {
		this.isFromParse = isFromParse;
	}

	public boolean isFavourite() {
		return isFavourite;
	}

	public void setFavourite(boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

}
