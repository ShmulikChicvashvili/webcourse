package com.technion.coolie.letmein.model;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.technion.coolie.letmein.CarManufacturer;
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
	private Date date;

	@DatabaseField
	private Status status;

	@DatabaseField
	private Long contactId;

	@DatabaseField
	private String contactName;

	@DatabaseField
	private String contactPhoneNumber;

	@DatabaseField
	private String carNumber;

	@DatabaseField
	private CarManufacturer carManufacturer;

	@DatabaseField
	private String carColor;

	public enum Status {
		CREATED, SENT
	}

	public Invitation() {
	}

	public long getId() {
		return id;
	}

	public Long getContactId() {
		return contactId;
	}

	private void setContactId(final Long contactId) {
		this.contactId = contactId;
	}

	public Date getDate() {
		return date;
	}

	private void setDate(final Date date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	private void setStatus(final Status status) {
		this.status = status;
	}

	public String getContactName() {
		return contactName;
	}

	private void setContactName(final String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	private void setContactPhoneNumber(final String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	public String getCarNumber() {
		return carNumber;
	}

	private void setCarNumber(final String carNumber) {
		this.carNumber = carNumber;
	}

	public CarManufacturer getCarManufacturer() {
		return carManufacturer;
	}

	private void setCarManufacturer(final CarManufacturer carManufacturer) {
		this.carManufacturer = carManufacturer;
	}

	public String getCarColor() {
		return carColor;
	}

	private void setCarColor(final String carColor) {
		this.carColor = carColor;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builder(final Invitation i) {
		return new Builder(i);
	}

	public static class Builder {
		private final Invitation invitation;

		private Builder() {
			invitation = new Invitation();
		}

		private Builder(final Invitation i) {
			if (i == null)
				throw new IllegalArgumentException("Passed null invitation to builder");

			invitation = i;
		}

		public Builder contactId(final Long contactId) {
			invitation.setContactId(contactId);
			return this;
		}

		public Builder date(final Date date) {
			invitation.setDate(date);
			return this;
		}

		public Builder status(final Status status) {
			invitation.setStatus(status);
			return this;
		}

		public Builder contactName(final String contactName) {
			invitation.setContactName(contactName);
			return this;
		}

		public Builder contactPhoneNumber(final String contactPhoneNumber) {
			invitation.setContactPhoneNumber(contactPhoneNumber);
			return this;
		}

		public Builder carNumber(final String carNumber) {
			invitation.setCarNumber(carNumber);
			return this;
		}

		public Builder carManufacturer(final CarManufacturer carManufacturer) {
			invitation.setCarManufacturer(carManufacturer);
			return this;
		}

		public Builder carColor(final String carColor) {
			invitation.setCarColor(carColor);
			return this;
		}

		public Invitation build() {
			return invitation;
		}
	}

}
