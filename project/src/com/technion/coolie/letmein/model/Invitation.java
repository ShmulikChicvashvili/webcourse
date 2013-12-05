package com.technion.coolie.letmein.model;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation.DefaultContentMimeTypeVnd;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation.DefaultContentUri;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation.DefaultSortOrder;

@DatabaseTable(tableName = Contract.Invitation.TABLENAME)
@DefaultContentUri(authority = Contract.AUTHORITY, path = Contract.Invitation.CONTENT_URI_PATH)
@DefaultContentMimeTypeVnd(name = Contract.Invitation.MIMETYPE_NAME, type = Contract.Invitation.MIMETYPE_TYPE)
public class Invitation {

	@DatabaseField(columnName = Contract.Invitation._ID, generatedId = true)
	@DefaultSortOrder
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
