package com.technion.coolie.ug.model;

public class Payment {
	private String description;
	private String amount;

	public Payment(final String description, final String amount) {
		this.description = description;
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(final String amount) {
		this.amount = amount;
	}

}