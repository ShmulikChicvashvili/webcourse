package com.technion.coolie.letmein.model;

import java.util.Date;

import com.technion.coolie.letmein.model.Invitation.Status;

public class InvitationBuilder {

	private Invitation invitation;
	
	public InvitationBuilder() {
		invitation = new Invitation();
	}

	public InvitationBuilder setContactId(String contactId) {
		invitation.setContactId(contactId);
		return this;
	}

	public InvitationBuilder setDate(Date date) {
		invitation.setDate(date);
		return this;
	}

	public InvitationBuilder setStatus(Status status) {
		invitation.setStatus(status);
		return this;
	}

	public InvitationBuilder setFriendName(String friendName) {
		invitation.setFriendName(friendName);
		return this;
	}

	public InvitationBuilder setFriendCellphone(String friendCellphone) {
		invitation.setFriendCellphone(friendCellphone);
		return this;
	}

	public InvitationBuilder setCarNumber(String carNumber) {
		invitation.setCarNumber(carNumber);
		return this;
	}

	public InvitationBuilder setCarCompany(String carCompany) {
		invitation.setCarCompany(carCompany);
		return this;
	}

	public InvitationBuilder setCarColor(String carColor) {
		invitation.setCarColor(carColor);
		return this;
	}

	public Invitation build() {
		return invitation;
	}
}
