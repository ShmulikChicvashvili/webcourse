package com.technion.coolie.letmein.model;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "invitations")
public class Invitation {
	@DatabaseField(generatedId = true)
	private long id;

	@DatabaseField
	private String contactId;

	@DatabaseField
	private Date date;

	@DatabaseField
	private Status status;

	public enum Status {
		CREATED, SENT
	}

	public Invitation() {
	}

	public long getId() {
		return id;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(final String contactId) {
		this.contactId = contactId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}
}
